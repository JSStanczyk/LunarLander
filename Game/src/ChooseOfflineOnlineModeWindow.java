import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * klasa odpowiedzialna za wyswietlanie okna ustawien trybu gry oraz obslugi logiki tych ustawien
 */
public class ChooseOfflineOnlineModeWindow extends Window implements ActionListener{

    /***
     * tablica wymairów okna
     */
    private int[] windowSizeXY;
    /**
     * zmienna informująca w jakim momęcie wyświetlane jest okno
     */
    private boolean preGameChoose = true;
    /**
     * nazwa okna
     */
    private String windowName;
    /**
     * windowID - jest wykorzystywane przy wczytywaniu parametrów przez LocalConfigurationLoader
     */
    private final String windowID = "ChooseOfflineOnline";
    /**
     * zmienne przechowywyjące dane o tym który chechbox był uprzednio włączony
     */
    private boolean onlineModeCheckBoxWasSelected, offlineModeCheckBoxWasSelected;
    /**
     * background opowiada za skalowanie i animację tła w Menu
     */
    public BackgroundMaker background;
    /**
     * okno do wprowadzanie nowych danych servera
     */
    private GetIPAndPortWindow getIPAndPort;
    /**
     * checkboxy
     * pola tekstowe
     * przyciki
     * layouty
     */
    private final JCheckBox onlineModeCheckBox, offlineModeCheckBox;
    private final JLabel onlineModeLabel, offlineModeLabel, chooseModeLabel;
    private final JButton applyButton, closeButton;
    private final JPanel buttonLayout, buttonPackLayout, onlineModeCheckBoxAndLabelLayout, onlineModeCheckBoxAndLabelPackLayout,
                   offlineModeCheckBoxAndLabelLayout, offileModeCheckBoxAndLabelPackLaout, generalCheckBoxesAndButtonsLayout,
                   generalCheckBoxesAndButtonsPackLayout;

    /**
     * konstruktor
     */
    ChooseOfflineOnlineModeWindow() {
        /**
         * wczytanie danych z pliku
         */
//        if (MenuWindow.serverIsOn) {
//            ServerFileComunicationCenter.loadServerWindowConfig(this, windowID);
//        } else {
            LocalFileComunicationCenter.loadLocalWindowConfig(this, windowID);
        //}

        /**
         * inicjalizacja GUI
         */
        buttonLayout = new JPanel(new GridLayout(1,3));
        buttonPackLayout  =new JPanel(new GridBagLayout());
        onlineModeCheckBoxAndLabelLayout = new JPanel(new GridLayout(1,2));
        onlineModeCheckBoxAndLabelPackLayout = new JPanel(new GridBagLayout());
        offlineModeCheckBoxAndLabelLayout = new JPanel(new GridLayout(1,2));
        offileModeCheckBoxAndLabelPackLaout = new JPanel(new GridBagLayout());
        generalCheckBoxesAndButtonsLayout  =new JPanel(new GridLayout(6,1));
        generalCheckBoxesAndButtonsPackLayout = new JPanel(new GridBagLayout());

        background = new BackgroundMaker(this, "Game/images/RestWindowsBackground.png", false);
        getIPAndPort = new GetIPAndPortWindow();

        applyButton = new JButton("Apply");
        closeButton = new JButton("Close");
        chooseModeLabel = new JLabel("Choose Mode:");
        onlineModeLabel = new JLabel("Online");
        offlineModeLabel = new JLabel("Offline");
        onlineModeCheckBox = new JCheckBox();
        offlineModeCheckBox = new JCheckBox();

        applyButton.addActionListener(this);
        closeButton.addActionListener(this);
        onlineModeCheckBox.addActionListener(this);
        offlineModeCheckBox.addActionListener(this);

        applyButton.setBackground(Color.LIGHT_GRAY);
        closeButton.setBackground(Color.LIGHT_GRAY);
        chooseModeLabel.setForeground(Color.WHITE);
        onlineModeLabel.setForeground(Color.WHITE);
        offlineModeLabel.setForeground(Color.WHITE);
        onlineModeCheckBox.setOpaque(false);
        offlineModeCheckBox.setOpaque(false);
        if(MenuWindow.serverIsOn) {
            onlineModeCheckBox.setSelected(true);
            offlineModeCheckBox.setSelected(false);
        }
        else {
            onlineModeCheckBox.setSelected(false);
            offlineModeCheckBox.setSelected(true);
        }
        offlineModeCheckBoxWasSelected = offlineModeCheckBox.isSelected();
        onlineModeCheckBoxWasSelected = onlineModeCheckBox.isSelected();

        buttonLayout.add(applyButton);
        buttonLayout.add(Box.createVerticalStrut(1));
        buttonLayout.add(closeButton);
        buttonLayout.setOpaque(false);

        buttonPackLayout.add(buttonLayout);
        buttonPackLayout.setOpaque(false);

        onlineModeCheckBoxAndLabelLayout.add(onlineModeLabel);
        onlineModeCheckBoxAndLabelLayout.add(onlineModeCheckBox);
        onlineModeCheckBoxAndLabelLayout.setOpaque(false);

        onlineModeCheckBoxAndLabelPackLayout.add(onlineModeCheckBoxAndLabelLayout);
        onlineModeCheckBoxAndLabelPackLayout.setOpaque(false);

        offlineModeCheckBoxAndLabelLayout.add(offlineModeLabel);
        offlineModeCheckBoxAndLabelLayout.add(offlineModeCheckBox);
        offlineModeCheckBoxAndLabelLayout.setOpaque(false);

        offileModeCheckBoxAndLabelPackLaout.add(offlineModeCheckBoxAndLabelLayout);
        offileModeCheckBoxAndLabelPackLaout.setOpaque(false);

        generalCheckBoxesAndButtonsLayout.add(Box.createVerticalStrut(1));
        generalCheckBoxesAndButtonsLayout.add(chooseModeLabel);
        generalCheckBoxesAndButtonsLayout.add(onlineModeCheckBoxAndLabelPackLayout);
        generalCheckBoxesAndButtonsLayout.add(offileModeCheckBoxAndLabelPackLaout);
        generalCheckBoxesAndButtonsLayout.add(Box.createVerticalStrut(1));
        generalCheckBoxesAndButtonsLayout.add(buttonPackLayout);
        generalCheckBoxesAndButtonsLayout.setOpaque(false);

        generalCheckBoxesAndButtonsPackLayout.add(generalCheckBoxesAndButtonsLayout);
        generalCheckBoxesAndButtonsPackLayout.setOpaque(false);

        background.add(generalCheckBoxesAndButtonsPackLayout);

        add(background);

        //this.preGameChoose=preGameChoose;
/**
 * ustawienie reakcji na kliknięcie czerwonego X
 */
        setTitle(windowName);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onlineModeCheckBox.setSelected(onlineModeCheckBoxWasSelected);
                offlineModeCheckBox.setSelected(offlineModeCheckBoxWasSelected);
                if (preGameChoose) System.exit(0);
                background.stopScaling();
                MenuWindow.Options.background.startScaling();
                MenuWindow.Options.setVisible(true);
            }
        });
        setSize(windowSizeXY[0], windowSizeXY[1]);
        setLocationRelativeTo(null);
        setVisible(true);

        //Main.startGame(false);
    }

    /**
     * metoda odpowiedzialna za logikę okna
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(applyButton.getText())){
            if(offlineModeCheckBoxWasSelected && onlineModeCheckBox.isSelected()){
                if (preGameChoose){
                    MenuWindow.serverIsOn = true;
                    if(!ServerFileComunicationCenter.checkIfServerIsOnline()){
                        MenuWindow.serverIsOn=false;
                        offlineModeCheckBox.setSelected(true);
                        onlineModeCheckBox.setSelected(false);
                        getIPAndPort.background.startScaling();
                        getIPAndPort.setVisible(true);
                    }
                    else{
                        setVisible(false);
                        background.stopScaling();
                        preGameChoose = false;
                        Main.startGame();
                    }
                }
                else {
                    MenuWindow.serverIsOn=true;
                    if(!ServerFileComunicationCenter.checkIfServerIsOnline()){
                        MenuWindow.serverIsOn=false;
                        offlineModeCheckBox.setSelected(true);
                        onlineModeCheckBox.setSelected(false);
                        getIPAndPort.background.startScaling();
                        getIPAndPort.setVisible(true);
                    }
                }
            }
            else if(onlineModeCheckBoxWasSelected && offlineModeCheckBox.isSelected()){
                MenuWindow.serverIsOn=false;
            }
            else if(offlineModeCheckBoxWasSelected && offlineModeCheckBox.isSelected() && preGameChoose){
                setVisible(false);
                background.stopScaling();
                MenuWindow.serverIsOn=false;
                preGameChoose = false;
                Main.startGame();
            }
            onlineModeCheckBoxWasSelected = onlineModeCheckBox.isSelected();
            offlineModeCheckBoxWasSelected = offlineModeCheckBox.isSelected();
        }
        else if(e.getActionCommand().equals(closeButton.getText())){
            onlineModeCheckBox.setSelected(onlineModeCheckBoxWasSelected);
            offlineModeCheckBox.setSelected(offlineModeCheckBoxWasSelected);
            if(preGameChoose) System.exit(0);
            background.stopScaling();
            setVisible(false);
            MenuWindow.Options.background.startScaling();
            MenuWindow.Options.setVisible(true);
        }
        else if(e.getSource().equals(onlineModeCheckBox) && offlineModeCheckBox.isSelected() == true){
            offlineModeCheckBox.setSelected(false);
            onlineModeCheckBox.setSelected(true);
        }
        else if(e.getSource().equals(offlineModeCheckBox) && onlineModeCheckBox.isSelected() == true){
            offlineModeCheckBox.setSelected(true);
            onlineModeCheckBox.setSelected(false);
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

}
