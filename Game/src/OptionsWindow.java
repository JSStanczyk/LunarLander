import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * klasa odpowiedzialna za wyświetlanie opcji
 */
public class OptionsWindow extends Window implements ActionListener {
    /***
     * tablica rozmiarów okienka
     */
    private int[] windowSizeXY;
    /***
     * windowName - nazwa okienka
     * windowID - jest wykorzystywane przy wczytywaniu parametrów przez LocalConfigurationLoader
     */
    private String windowName;
    private final String windowID = "Options";
    /***
     * okna odpowiedzialne za poszczególne opcje możliwe do zmiany
     */
    private MovingOptionsWindow movingOptionsWindow;
    private DifficultyOptionsWindow difficultyOptionsWindow;
    private ChooseOfflineOnlineModeWindow chooseOfflineOnlineModeWindow;
    /***
     * opowiada za skalowanie tła okna
     */
    public  BackgroundMaker background;
    /**
     * przyciski
     */
    private JButton movingOptionsWindowButton, difficultyOptionsWindowButton, chooseOfflineOnlineWindowButton, exitButton;
    /***
     *latouty do pozycjonowania przycisku, tabel oraz checkboxów
     */
    private JPanel buttonLayout, packLayout, bottomLayout;
    /***
     * inicjalizacja GUI
     */
    OptionsWindow(ChooseOfflineOnlineModeWindow chooseOfflineOnlineModeWindow) {
        if (MenuWindow.serverIsOn) {
            ServerFileComunicationCenter.loadServerWindowConfig(this, windowID);
        } else {
            LocalFileComunicationCenter.loadLocalWindowConfig(this, windowID);
        }

        buttonLayout = new JPanel(new GridLayout(8, 1));
        packLayout = new JPanel(new GridBagLayout());
        bottomLayout = new JPanel(new GridLayout(2, 1));

        movingOptionsWindow = new MovingOptionsWindow();
        difficultyOptionsWindow = new DifficultyOptionsWindow();
        this.chooseOfflineOnlineModeWindow = chooseOfflineOnlineModeWindow;
        background = new BackgroundMaker(this, "Game/images/RestWindowsBackground.png", false);

        movingOptionsWindowButton = new JButton("Moving Options");
        difficultyOptionsWindowButton = new JButton("Difficulty Level");
        chooseOfflineOnlineWindowButton = new JButton("Choose Offline/Online Mode");
        exitButton = new JButton("Exit Options");

        movingOptionsWindowButton.addActionListener(this);
        difficultyOptionsWindowButton.addActionListener(this);
        chooseOfflineOnlineWindowButton.addActionListener(this);
        exitButton.addActionListener(this);

        movingOptionsWindowButton.setBackground(Color.LIGHT_GRAY);
        difficultyOptionsWindowButton.setBackground(Color.LIGHT_GRAY);
        chooseOfflineOnlineWindowButton.setBackground(Color.LIGHT_GRAY);
        exitButton.setBackground(Color.LIGHT_GRAY);

        buttonLayout.add(Box.createVerticalStrut(1));
        buttonLayout.add(movingOptionsWindowButton);
        buttonLayout.add(Box.createVerticalStrut(1));
        buttonLayout.add(difficultyOptionsWindowButton);
        buttonLayout.add(Box.createVerticalStrut(1));
        buttonLayout.add(chooseOfflineOnlineWindowButton);
        buttonLayout.add(Box.createVerticalStrut(1));
        buttonLayout.add(exitButton);
        buttonLayout.setOpaque(false);

        packLayout.add(buttonLayout);
        packLayout.setOpaque(false);

//        bottomLayout.add(Box.createVerticalStrut(1));
//        bottomLayout.add(packLayout);
//        bottomLayout.setOpaque(false);

        background.add(packLayout);

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
        setVisible(false);
    }

    /***
     * metoda modyfukująca opcje możliwe do zmiany w zależności od tego czy trwa rozgrywka
     */
    public void switchInGameOutGameLayout(){
        if(difficultyOptionsWindowButton.isEnabled()){
            difficultyOptionsWindowButton.setEnabled(false);
            chooseOfflineOnlineWindowButton.setEnabled(false);
        }
        else{
            difficultyOptionsWindowButton.setEnabled(true);
            chooseOfflineOnlineWindowButton.setEnabled(true);
        }
    }
    /***
     * metoda odpowiedzialna za logikę przycisków oraz checkboxów
     */
    @Override
    public void actionPerformed(ActionEvent pressed) {
        if (pressed.getActionCommand().equals(movingOptionsWindowButton.getText())){
            background.stopScaling();
            setVisible(false);
            movingOptionsWindow.background.startScaling();
            movingOptionsWindow.setVisible(true);
        }
        else if (pressed.getActionCommand().equals(difficultyOptionsWindowButton.getText())){
            background.stopScaling();
            setVisible(false);
            difficultyOptionsWindow.background.startScaling();
            difficultyOptionsWindow.setVisible(true);
        }
        else if (pressed.getActionCommand().equals(chooseOfflineOnlineWindowButton.getText())){
            background.stopScaling();
            setVisible(false);
            chooseOfflineOnlineModeWindow.background.startScaling();
            chooseOfflineOnlineModeWindow.setVisible(true);
        }
        else if (pressed.getActionCommand().equals(exitButton.getText())){
            background.stopScaling();
            setVisible(false);
        }
    }

    /***
     * setter wymarów okienka
     * @param windowSizeXY tablica wymiarów okna
     */
    @Override
    public void setWindowSizeXY(int[] windowSizeXY) {
        this.windowSizeXY = windowSizeXY;
    }

    /***
     * setter nazwy okienka
     * @param windowName nazwa okna
     */
    @Override
    public void setWindowName(String windowName) {
        this.windowName = windowName;
    }

    /***
     * getter szerokości okienka
     * @return szerokość okienka
     */
    @Override
    public int getWindowSizeX() {
        return windowSizeXY[0];
    }

    /***
     * getter wyskokości okienka
     * @return wyskokość okienka
     */
    @Override
    public int getWindowSizeY() {
        return windowSizeXY[1];
    }

    /**
     * zwraca informacje o statusie checkboxa od sterowanie WSADem
     * @return
     */
    public boolean getWsadCheckBoxStatus(){
        return movingOptionsWindow.getWsadCheckBoxStatus();
    }
    /**
     * zwraca informacje o statusie checkboxa od sterowanie strzałkami
     * @return
     */
    public boolean getArrowsCheckBoxStatus(){
        return movingOptionsWindow.getArrowsCheckBoxStatus();
    }

    /***
     * zwraza informację o poziomie trudności
     * @return
     */
    public double getInterpretedDifficultyLevel(){
        return difficultyOptionsWindow.interpretDifficultyLevel();
    }
}