import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * klasa odpowiedzilana za wyswietlanie okna ustawien poziomu trudnosci i obsluge logiki tych ustawien
 */
public class DifficultyOptionsWindow extends Window implements ActionListener {
    /***
     * tablica wymairów okna
     */
    private int[] windowSizeXY;
    /**
     * nazwa okna
     */
    private String windowName;
    /**
     * windowID - jest wykorzystywane przy wczytywaniu parametrów przez LocalConfigurationLoader
     */
    private final String windowID = "DifficultyOptions";
    /**
     * zmienne przechowywyjące dane o tym który chechbox był uprzednio włączony
     */
    private boolean easyModeCheckBoxWasSelected, normalModeCheckBoxWasSelected, hardModeCheckBoxWasSelected;
    /**
     * background opowiada za skalowanie i animację tła w Menu
     */
    public BackgroundMaker background;
    /**
     * checkboxy
     * pola tekstowe
     * przyciki
     * layouty
     */
    private final JCheckBox easyModeCheckBox, normalModeCheckBox, hardModeCheckBox;
    private final JButton okButton, cancelButton;
    private final JLabel easyModeLabel, normalModeLabel, hardModeLabel, leveLabel;
    private final JPanel buttonLayout, buttonPackLayout,easyModeCheckBoxAndLabelLayout, normalModeCheckBoxAndLabelLayout, hardModeCheckBoxAndLabelLayout,
                   easyModeCheckBoxAndLabelPackLayout, normalModeCheckBoxAndLabelPackLayout, hardModeCheckBoxAndLabelPackLayout,
                   generalCheckBoxesAndButtonsLayout, generalCheckBoxesAndButtonsPackLayout;// bottomLayout, generalPackLayout;

    /**
     * konstuktor
     */
    DifficultyOptionsWindow(){
        /**
         * wczytanie danych z pliku
         */
        if (MenuWindow.serverIsOn) {
            ServerFileComunicationCenter.loadServerWindowConfig(this, windowID);
        } else {
            LocalFileComunicationCenter.loadLocalWindowConfig(this, windowID);
        }

        /**
         * inicjalizacja GUI
         */
        buttonLayout = new JPanel(new GridLayout(1,3));
        buttonPackLayout = new JPanel(new GridBagLayout());
        easyModeCheckBoxAndLabelLayout = new JPanel(new GridLayout(1,2));
        normalModeCheckBoxAndLabelLayout = new JPanel(new GridLayout(1,2));
        hardModeCheckBoxAndLabelLayout = new JPanel(new GridLayout(1,2));
        easyModeCheckBoxAndLabelPackLayout = new JPanel(new GridBagLayout());
        normalModeCheckBoxAndLabelPackLayout = new JPanel(new GridBagLayout());
        hardModeCheckBoxAndLabelPackLayout = new JPanel(new GridBagLayout());
        generalCheckBoxesAndButtonsLayout = new JPanel(new GridLayout(7,1));
        generalCheckBoxesAndButtonsPackLayout = new JPanel(new GridBagLayout());
//        bottomLayout = new JPanel(new GridLayout(2,1));
//        generalPackLayout = new JPanel(new GridBagLayout());

        background = new BackgroundMaker(this, "Game/images/RestWindowsBackground.png", false);

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        easyModeCheckBox = new JCheckBox();
        normalModeCheckBox = new JCheckBox();
        hardModeCheckBox = new JCheckBox();
        leveLabel = new JLabel("Difficulty Level:");
        easyModeLabel = new JLabel("Easy");
        normalModeLabel = new JLabel("Normal");
        hardModeLabel = new JLabel("Hard");

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);
        easyModeCheckBox.addActionListener(this);
        normalModeCheckBox.addActionListener(this);
        hardModeCheckBox.addActionListener(this);

        easyModeCheckBox.setSelected(false);
        easyModeCheckBoxWasSelected = easyModeCheckBox.isSelected();
        easyModeCheckBox.setOpaque(false);
        normalModeCheckBox.setSelected(true);
        normalModeCheckBoxWasSelected = normalModeCheckBox.isSelected();
        normalModeCheckBox.setOpaque(false);
        hardModeCheckBox.setSelected(false);
        hardModeCheckBoxWasSelected = hardModeCheckBox.isSelected();
        hardModeCheckBox.setOpaque(false);
        okButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setBackground(Color.LIGHT_GRAY);
        leveLabel.setForeground(Color.WHITE);
        easyModeLabel.setForeground(Color.WHITE);
        normalModeLabel.setForeground(Color.WHITE);
        hardModeLabel.setForeground(Color.WHITE);

        buttonLayout.add(okButton);
        buttonLayout.add(Box.createVerticalStrut(1));
        buttonLayout.add(cancelButton);
        buttonLayout.setOpaque(false);

        buttonPackLayout.add(buttonLayout);
        buttonPackLayout.setOpaque(false);

        easyModeCheckBoxAndLabelLayout.add(easyModeLabel);
        easyModeCheckBoxAndLabelLayout.add(easyModeCheckBox);
        easyModeCheckBoxAndLabelLayout.setOpaque(false);

        easyModeCheckBoxAndLabelPackLayout.add(easyModeCheckBoxAndLabelLayout);
        easyModeCheckBoxAndLabelPackLayout.setOpaque(false);

        normalModeCheckBoxAndLabelLayout.add(normalModeLabel);
        normalModeCheckBoxAndLabelLayout.add(normalModeCheckBox);
        normalModeCheckBoxAndLabelLayout.setOpaque(false);

        normalModeCheckBoxAndLabelPackLayout.add(normalModeCheckBoxAndLabelLayout);
        normalModeCheckBoxAndLabelPackLayout.setOpaque(false);

        hardModeCheckBoxAndLabelLayout.add(hardModeLabel);
        hardModeCheckBoxAndLabelLayout.add(hardModeCheckBox);
        hardModeCheckBoxAndLabelLayout.setOpaque(false);

        hardModeCheckBoxAndLabelPackLayout.add(hardModeCheckBoxAndLabelLayout);
        hardModeCheckBoxAndLabelPackLayout.setOpaque(false);

        generalCheckBoxesAndButtonsLayout.add(Box.createVerticalStrut(1));
        generalCheckBoxesAndButtonsLayout.add(leveLabel);

        generalCheckBoxesAndButtonsLayout.add(easyModeCheckBoxAndLabelPackLayout);

        generalCheckBoxesAndButtonsLayout.add(normalModeCheckBoxAndLabelPackLayout);

        generalCheckBoxesAndButtonsLayout.add(hardModeCheckBoxAndLabelPackLayout);
        generalCheckBoxesAndButtonsLayout.add(Box.createVerticalStrut(1));
        generalCheckBoxesAndButtonsLayout.add(buttonPackLayout);
        generalCheckBoxesAndButtonsLayout.setOpaque(false);

        generalCheckBoxesAndButtonsPackLayout.add(generalCheckBoxesAndButtonsLayout);
        generalCheckBoxesAndButtonsPackLayout.setOpaque(false);

        background.add(generalCheckBoxesAndButtonsPackLayout);

        add(background);
/**
 * ustawienie reakcji na kliknięcie czerwonego X
 */
        setTitle(windowName);
        //setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                easyModeCheckBox.setSelected(easyModeCheckBoxWasSelected);
                normalModeCheckBox.setSelected(normalModeCheckBoxWasSelected);
                hardModeCheckBox.setSelected(hardModeCheckBoxWasSelected);
                background.stopScaling();
                //setVisible(false);
                MenuWindow.Options.background.startScaling();
                MenuWindow.Options.setVisible(true);
            }
        });
        setSize(windowSizeXY[0], windowSizeXY[1]);
        setLocationRelativeTo(null);
        setVisible(false);
    }

    /**
     * metoda interpretująca wybrany poziom trudności jako stałe liczbowe używane do skalowania punktacji, zużycia paliwa itd.
     * @return
     */
    public double interpretDifficultyLevel() {
        if (easyModeCheckBox.isSelected()==true && normalModeCheckBox.isSelected()==false && hardModeCheckBox.isSelected()==false) {
            return 0.5;
        }
        else if (normalModeCheckBox.isSelected()==true && easyModeCheckBox.isSelected()==false && hardModeCheckBox.isSelected()==false){
            return 1;
        }
        else if(hardModeCheckBox.isSelected()==true && easyModeCheckBox.isSelected()==false && normalModeCheckBox.isSelected()==false) {
            return 2;
        }
        else{
            return 0;
        }
    }

    /**
     * metoda odpowiedzialna za logikę okna
     */
    @Override
    public void actionPerformed(ActionEvent a) {
        if(a.getActionCommand().equals(okButton.getText())){
            easyModeCheckBoxWasSelected = easyModeCheckBox.isSelected();
            normalModeCheckBoxWasSelected = normalModeCheckBox.isSelected();
            hardModeCheckBoxWasSelected = hardModeCheckBox.isSelected();
            background.stopScaling();
            setVisible(false);
            MenuWindow.Options.background.startScaling();
            MenuWindow.Options.setVisible(true);
        }
        else if(a.getActionCommand().equals(cancelButton.getText())){
            easyModeCheckBox.setSelected(easyModeCheckBoxWasSelected);
            normalModeCheckBox.setSelected(normalModeCheckBoxWasSelected);
            hardModeCheckBox.setSelected(hardModeCheckBoxWasSelected);
            background.stopScaling();
            setVisible(false);
            MenuWindow.Options.background.startScaling();
            MenuWindow.Options.setVisible(true);
        }
        else if(a.getSource().equals(easyModeCheckBox) && normalModeCheckBox.isSelected()==true && hardModeCheckBox.isSelected()==false){
            easyModeCheckBox.setSelected(true);
            normalModeCheckBox.setSelected(false);
        }
        else if(a.getSource().equals(easyModeCheckBox) && normalModeCheckBox.isSelected()==false && hardModeCheckBox.isSelected()==true){
            easyModeCheckBox.setSelected(true);
            hardModeCheckBox.setSelected(false);
        }
        else if(a.getSource().equals(normalModeCheckBox) && easyModeCheckBox.isSelected()==true && hardModeCheckBox.isSelected()==false){
            normalModeCheckBox.setSelected(true);
            easyModeCheckBox.setSelected(false);
        }
        else if(a.getSource().equals(normalModeCheckBox) && easyModeCheckBox.isSelected()==false && hardModeCheckBox.isSelected()==true){
            normalModeCheckBox.setSelected(true);
            hardModeCheckBox.setSelected(false);
        }
        else if(a.getSource().equals(hardModeCheckBox) && easyModeCheckBox.isSelected()==true && normalModeCheckBox.isSelected()==false){
            hardModeCheckBox.setSelected(true);
            easyModeCheckBox.setSelected(false);
        }
        else  if(a.getSource().equals(hardModeCheckBox) && easyModeCheckBox.isSelected()==false && normalModeCheckBox.isSelected()==true){
            hardModeCheckBox.setSelected(true);
            normalModeCheckBox.setSelected(false);
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
