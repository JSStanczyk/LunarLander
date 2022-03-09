import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

/***
 * Klasa odpowiedzialna za wyświetlanie okna wyjścia
 */
public class ExitCheckWindow extends Window implements ActionListener {
    /***
     * tablica rozmiarów okienka
     */
    private int[] windowSizeXY;
    /***
     * windowName - nazwa okienka
     * windowID - jest wykorzystywane przy wczytywaniu parametrów przez LocalConfigurationLoader
     */
    private String windowName;
    private final String windowID="ExitCheck";
    /**
     *zmienna pozwalająca ustalić kiedy jest wysietlane okno
     */
    private boolean itsInGameExit;
    /***
     * przyciski
     */
    private JButton noButton, yesButton;
    /***
     * latouty do pozycjonowania przycisków
     */
    private JPanel layout, buttonBox, innerLayout;
    /***
     * question tabela
     */
    private JLabel question;
    /***
     * background opowiada za skalowanie tła okna
     */
    public BackgroundMaker background;

    /***
     * konstruktor
     */
    ExitCheckWindow(){
        question = new JLabel("Do you really want to exit?");
        initWindow();
        this.itsInGameExit = false;
    }

    /***
     * konstruktor
     */
    ExitCheckWindow(boolean itsInGameExit){
        question = new JLabel("Do you really want to exit to Menu?");
        initWindow();
        this.itsInGameExit = itsInGameExit;
        //this.itsInGameExit = true;
    }

    /**
     * inicjalizator okna
     */
    private void initWindow(){
        /**
         * wczytanie danych z pliku
         */
        if (MenuWindow.serverIsOn) {
            ServerFileComunicationCenter.loadServerWindowConfig(this, windowID);
        }
        else {
            LocalFileComunicationCenter.loadLocalWindowConfig(this, windowID);
        }
        /***
         * inicjalizacja GUI
         */
        buttonBox = new JPanel(new GridLayout(1,3));
        innerLayout = new JPanel(new GridLayout(4,1));
        layout = new JPanel(new GridBagLayout());
        noButton = new JButton("NO");
        yesButton = new JButton("YES");
        //question = new JLabel("Czy napewno chcesz wyjść?");
        background = new BackgroundMaker(this, "Game/images/RestWindowsBackground.png", false);

        noButton.addActionListener(this);
        yesButton.addActionListener(this);

        yesButton.setBackground(Color.LIGHT_GRAY);
        noButton.setBackground(Color.LIGHT_GRAY);
        question.setForeground(Color.WHITE);

        buttonBox.add(yesButton);
        buttonBox.add(Box.createVerticalStrut(1));
        buttonBox.add(noButton);
        buttonBox.setOpaque(false);

        innerLayout.add(Box.createVerticalStrut(1));
        innerLayout.add(question);
        innerLayout.add(Box.createVerticalStrut(1));
        innerLayout.add(buttonBox);
        innerLayout.setOpaque(false);

        layout.add(innerLayout);
        layout.setOpaque(false);

        background.add(layout);

        add(background);

        setTitle(windowName);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                background.stopScaling();
            }
        });
        setSize(windowSizeXY[0], windowSizeXY[1]);
        setLocationRelativeTo(null);
    }

    /***
     * metoda odpowiedzialna za logikę przycisków
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals(yesButton.getText())){
            if(itsInGameExit){
                background.stopScaling();
                setVisible(false);
                MenuWindow.gameBoardWindow.exitToMenu();
                MenuWindow.gameBoardWindow.gameBoardEngine.restart();
            }
            else{
                System.exit(0);
            }
        }
        else if(e.getActionCommand().equals(noButton.getText())){
            background.stopScaling();
            setVisible(false);
        }
    }

    /***
     * setter wymarów okienka
     * @param windowSizeXY tablica wymarów okna
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
