import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Klasa odpowiedzialna za tworzenie wszystkich niezbędnych obiektów oraz obsługę Menu
 */
public class MenuWindow extends Window implements ActionListener {
    public static boolean serverIsOn;
    /***
     * tablica rozmiarów okienka
     */
    private int[] windowSizeXY;
    /***
     * windowName - nazwa okienka
     * windowID - jest wykorzystywane przy wczytywaniu parametrów przez LocalConfigurationLoader
     */
    private String windowName;
    private final String windowID="Menu";
    /***
     * przyciski do obsługi Menu
     */
    private final JButton StartButton, CreditsButton, LeaderBoardButton, ExitButton, OptionsButton;
    /***
     * latouty do pozycjonowania przycisków
     */
    private final JPanel packLayout, buttonBox, centerLayout;
    /***
     * background opowiada za skalowanie i animację tła w Menu
     */
    public static BackgroundMaker background;
    /***
     * okno autorów
     */
    private final CreditsWindow Credits;
    /***
     * okno opcji
     */
    public static OptionsWindow Options;

    private LeaderBoardWindow LeaderBoard;
    /***
     * okno potwierdzenie wyjścia
     */
    private final ExitCheckWindow ExitCheck;
    /***
     * okno do pobierania nazwy gracza
     */
    private final GetNickNameWindow GetNickName;
    /***
     * okno gry
     */
    public static GameBoardWindow gameBoardWindow;

    /***
     * konstruktor
     */
    MenuWindow(ChooseOfflineOnlineModeWindow chooseOfflineOnlineModeWindow){
        /***
         * wczytanie parametrów z pliku
         */

        if (this.serverIsOn){
            ServerFileComunicationCenter.loadServerWindowConfig(this, windowID);
        }
        else {
            LocalFileComunicationCenter.loadLocalWindowConfig(this, windowID);
        }

        /***
         * inicjalizacja GUI i okien
         */
        buttonBox = new JPanel(new GridLayout(10,1));
        packLayout = new JPanel(new GridBagLayout());
        centerLayout = new JPanel(new GridLayout(3,1));
        background = new BackgroundMaker(this, "Game/images/MainMenuBackground.png", "Game/images/Title.png", "Game/images/RotatedUpLanderIcon.png", "Game/images/onlineIcon.png","Game/images/offlineIcon.png",true);
        background.startScaling();
        background.startAnimation();

        GetNickName = new GetNickNameWindow();
        Credits = new CreditsWindow();
        Options = new OptionsWindow(chooseOfflineOnlineModeWindow);
        LeaderBoard = new LeaderBoardWindow();
        ExitCheck = new ExitCheckWindow();
        gameBoardWindow = new GameBoardWindow(Options, LeaderBoard.getLeaderBoard());

        StartButton = new JButton("Start");
        CreditsButton = new JButton("Credits");
        OptionsButton = new JButton("Options");
        LeaderBoardButton = new JButton("Leader Board");
        ExitButton = new JButton("Exit");

        StartButton.addActionListener(this);
        CreditsButton.addActionListener(this);
        OptionsButton.addActionListener(this);
        LeaderBoardButton.addActionListener(this);
        ExitButton.addActionListener(this);

        StartButton.setBackground(Color.LIGHT_GRAY);
        CreditsButton.setBackground(Color.LIGHT_GRAY);
        OptionsButton.setBackground(Color.LIGHT_GRAY);
        LeaderBoardButton.setBackground(Color.LIGHT_GRAY);
        ExitButton.setBackground(Color.LIGHT_GRAY);

        buttonBox.add(StartButton);
        buttonBox.add(Box.createVerticalStrut(1));
        buttonBox.add(CreditsButton);
        buttonBox.add(Box.createVerticalStrut(1));
        buttonBox.add(OptionsButton);
        buttonBox.add(Box.createVerticalStrut(1));
        buttonBox.add(LeaderBoardButton);
        buttonBox.add(Box.createVerticalStrut(1));
        buttonBox.add(Box.createVerticalStrut(1));
        buttonBox.add(ExitButton);
        buttonBox.setOpaque(false);

        packLayout.add(buttonBox);
        packLayout.setOpaque(false);

        centerLayout.add(Box.createVerticalStrut(1));
        centerLayout.add(packLayout);
        centerLayout.add(Box.createVerticalStrut(1));
        centerLayout.setOpaque(false);

        background.add(centerLayout);

        add(background);

        setTitle(windowName);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ExitCheck.background.startScaling();
                ExitCheck.setVisible(true);
            }
        });
        setSize(windowSizeXY[0], windowSizeXY[1]);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /***
     * metoda odpowiedzialna za logikę Menu
     * @param pressed
     */
    @Override
    public void actionPerformed(ActionEvent pressed){
        if(pressed.getActionCommand().equals(StartButton.getText())){
            try{
                GetNickName.background.startScaling();
                GetNickName.setVisible(true);
            }
            catch (NullPointerException e){}
        }
        else if(pressed.getActionCommand().equals(CreditsButton.getText())){
            Credits.background.startScaling();
            Credits.setVisible(true);
        }
        else if (pressed.getActionCommand().equals(OptionsButton.getText())){
            Options.background.startScaling();
            Options.setVisible(true);
        }
        else if(pressed.getActionCommand().equals(LeaderBoardButton.getText())){
            LeaderBoard.updateLeaderBoard();
            LeaderBoard.background.startScaling();
            LeaderBoard.setVisible(true);
        }
        else if(pressed.getActionCommand().equals(ExitButton.getText())){
            ExitCheck.background.startScaling();
            ExitCheck.setVisible(true);
        }
    }

    /***
     * setter wymarów okienka
     * @param windowSizeXY tablica wymiarów okna
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