import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/***
 * Klasa tworząca okno Gry
 */
public class GameBoardWindow extends Window{
    /**
     * tablica rozmiarów okienka
     */
    private int[] windowSizeXY;
    /***
     * windowName - nazwa okienka
     * windowID - jest wykorzystywane przy wczytywaniu parametrów przez LocalConfigurationLoader
     */
    private String windowName;
    private final String windowID="GameBoard";
    /***
     * obiekt gracza
     */
    private Player player;
//    /***
//     * okno opcji
//     */
    //private final OptionsWindow options;
    /***
     * obiekt klasy odpowiedzialnej za logikę gry
     */
    public GameBoardEngine gameBoardEngine;

    /**
     * okno do potwierdzenia 100% chęci wyjścia do menu
     */
    private ExitCheckWindow exitCheck;

    /***
     * konstruktor
     * @param options obiekt okna opcji
     */
    GameBoardWindow(OptionsWindow options, LeaderBoard leaderBoard){
        /***
         * inicjalizacja GUI
         */
        if (MenuWindow.serverIsOn) {
            ServerFileComunicationCenter.loadServerWindowConfig(this, windowID);
        }
        else {
            LocalFileComunicationCenter.loadLocalWindowConfig(this, windowID);
        }
        //this.options = options;
        exitCheck = new ExitCheckWindow(true);
        gameBoardEngine = new GameBoardEngine(options, leaderBoard, exitCheck, windowSizeXY);

        addKeyListener(new KeyboardChecker(gameBoardEngine));

        setTitle(windowName);
        setSize(windowSizeXY[0], windowSizeXY[1]);
        add(gameBoardEngine);
        /**
         * ustawienie reakcji na kliknięcie czerwonego X
         */
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(gameBoardEngine.inGame){
                    gameBoardEngine.pause();
                }
                exitCheck.background.startScaling();
                exitCheck.setVisible(true);
            }
        });
        setLocationRelativeTo(null);
    }

    /***
     * setter gracza
     * @param player obiekt gracza
     */
    public void setPlayer(Player player){
        this.player = player;
        gameBoardEngine.setPlayer(this.player);
    }

    /**
     * metoda pozwalająca wdrorzyć poziom trudności wybrany przez gracza
     */
    public void applyDifficultyLevel(){
        gameBoardEngine.modifyLanderByDifficultyLevel();
        gameBoardEngine.modifyMapByDifficultyLevel();
    }

    /**
     * metoda pozwalająca wdrorzyć tryb rozgrywki wybrany przez gracza
     */
    public void applyOfflineOnlineMode(){
        gameBoardEngine.checkConnectivity();
        gameBoardEngine.applyOfflineOnlineMode();
    }

    /***
     * metoda wychodzenia służąca do wychodzenia do Menu
     */
    public void exitToMenu(){
        setVisible(false);
        gameBoardEngine.exitToMenu();
        MenuWindow.background.startScaling();
        MenuWindow.background.startAnimation();
        Main.Menu.setVisible(true);
    }

    /***
     * setter rozmiarów okienka
     * @param windowSizeXY tablica wymiary okienka
     */
    @Override
    public void setWindowSizeXY(int[] windowSizeXY){
        this.windowSizeXY=windowSizeXY;
    }

    /***
     * setter nazwt okna
     * @param windowName nazwa okna
     */
    @Override
    public void setWindowName(String windowName){
        this.windowName=windowName;
    }

    /***
     * getter szerokości okna
     * @return szerokość okna
     */
    @Override
    public int getWindowSizeX(){
        return windowSizeXY[0];
    }

    /***
     * getter wysokości okna
     * @return wysokość okna
     */
    @Override
    public int getWindowSizeY(){
        return windowSizeXY[1];
    }
}