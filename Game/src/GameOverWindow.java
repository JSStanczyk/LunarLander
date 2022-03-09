import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/***
 * Klasa odpowiedzialna na wyświetlanie okna końca gry
 */
public class GameOverWindow extends Window implements ActionListener {
    /***
     * tablica rozmiarów okienka
     */
    private int[] windowSizeXY;
    /***
     * windowName - nazwa okienka
     * windowID - jest wykorzystywane przy wczytywaniu parametrów przez LocalConfigurationLoader
     */
    private String windowName;
    private final String windowID="GameOver";
    /***
     * przycisk
     */
    private JButton okButton;
    /***
     * latouty do pozycjonowania przycisku i tabel
     */
    private JPanel layout, packLayout;
    /***
     * tabele
     */
    private JLabel playerName, playerScore;
    /***
     * obiekt gracza
     */
    private Player player;
    /***
     * background opowiada za skalowanie tła okna
     */
    public BackgroundMaker background;

    /***
     * konstruktor
     */
    GameOverWindow(){
        if (MenuWindow.serverIsOn) {
            ServerFileComunicationCenter.loadServerWindowConfig(this, windowID);
        }
        else {
            LocalFileComunicationCenter.loadLocalWindowConfig(this, windowID);
        }
        /***
         * inicjalizacja GUI
         */
        okButton = new JButton("OK");
        playerName = new JLabel();
        playerScore = new JLabel();
        layout = new JPanel(new GridLayout(6,1));
        packLayout = new JPanel(new GridBagLayout());
        background = new BackgroundMaker(this, "Game/images/RestWindowsBackground.png",false);

        okButton.addActionListener(this);

        okButton.setBackground(Color.LIGHT_GRAY);
        playerName.setForeground(Color.WHITE);
        playerScore.setForeground(Color.WHITE);

        layout.add(Box.createVerticalStrut(1));
        layout.add(Box.createVerticalStrut(1));
        layout.add(playerName);
        layout.add(playerScore);
        layout.add(Box.createVerticalStrut(1));
        layout.add(okButton);
        layout.setOpaque(false);

        packLayout.add(layout);
        packLayout.setOpaque(false);

        background.add(packLayout);

        add(background);

        setTitle(windowName);
        setSize(windowSizeXY[0], windowSizeXY[1]);
        /**
         * ustawienie reakcji na kliknięcie czerwonego X
         */
        //setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                background.stopScaling();
                MenuWindow.background.startScaling();
                MenuWindow.background.startAnimation();
                Main.Menu.setVisible(true);
            }
        });
        setLocationRelativeTo(null);
    }

//    /***
//     * setter obiektu gracza + inicjalizator textu tabel
//     * @param player
//     */
//    public void setPlayer(Player player) {
//
//    }

    /**
     * metoda ustawiająca layout wygranej i wyświetlająca okno
     * @param player
     */
    public void winLayout(Player player){
        this.player = player;
        playerName.setText("Nick: "+this.player.getNickname());
        playerScore.setText("Score: "+this.player.getScore());
        MenuWindow.gameBoardWindow.setVisible(false);
    }

    /**
     * metoda ustawiająca layout przegranej i wyświetlająca okno
     */
    public void loseLayout(){
        playerName.setText("GAME OVER");
        playerScore.setText("");
        MenuWindow.gameBoardWindow.setVisible(false);
    }

    /***
     * metoda odpowiedzialna za logikę przycisku
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals(okButton.getText())){
            background.stopScaling();
            setVisible(false);
            MenuWindow.gameBoardWindow.exitToMenu();
        }
    }

    /***
     * setter wymarów okienka
     * @param windowSizeXY tablica wymiarów onka
     */
    @Override
    public void setWindowSizeXY(int[] windowSizeXY){
        this.windowSizeXY=windowSizeXY;
    }

    /***
     * setter nazwy okienka
     * @param windowName nazwa okna
     */
    @Override
    public void setWindowName(String windowName){
        this.windowName=windowName;
    }

    /***
     * getter szerokości okienka
     * @return szerokość okienka
     */
    @Override
    public int getWindowSizeX(){
        return windowSizeXY[0];
    }

    /***
     * getter wyskokości okienka
     * @return wyskokość okienka
     */
    @Override
    public int getWindowSizeY(){
        return windowSizeXY[1];
    }
}
