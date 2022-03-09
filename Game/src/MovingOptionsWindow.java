import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/***
 * Klasa odpowiedzialna za wyświetlanie okna opcji
 */

public class MovingOptionsWindow extends Window implements ActionListener{
    /***
     * tablica rozmiarów okienka
     */
    private int[] windowSizeXY;
    /***
     * windowName - nazwa okienka
     * windowID - jest wykorzystywane przy wczytywaniu parametrów przez LocalConfigurationLoader
     */
    private String windowName;
    private final String windowID="MovingOptions";

    private boolean arrowsCheckBoxWasSelected, wsadCheckBoxWasSelected;
    /***
     *latouty do pozycjonowania przycisku, tabel oraz checkboxów
     */
    private final JPanel buttonBox, arrowsCheckBoxLayout, wsadCheckBoxLayout, connectingCheckBoxesLayout, generalLayout, packLayout,
            buttonBoxPackLayout;
    /**
     * przyciski
     */
    private final JButton okButton, cancelButton;
    /***
     * checkboxy
     */
    private final JCheckBox arrowsCheckBox, wsadCheckBox;
    /***
     * tabele
     */
    private final JLabel arrowsUpMove, arrowsLeftMove, arrowsRightMove, wsadUpMove, wsadLeftMove, wsadRightMove;
    /***
     * opowiada za skalowanie tła okna
     */
    public BackgroundMaker background;

    /***
     * konstruktor
     */
    MovingOptionsWindow(){
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
        arrowsCheckBoxLayout = new JPanel();
        wsadCheckBoxLayout = new JPanel();
        connectingCheckBoxesLayout = new JPanel(new GridLayout(1,3));
        generalLayout = new JPanel(new GridLayout(3,1));
        buttonBoxPackLayout = new JPanel(new GridBagLayout());
        packLayout = new JPanel(new GridBagLayout());
        background = new BackgroundMaker(this, "Game/images/RestWindowsBackground.png",false);

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        arrowsCheckBox = new JCheckBox();
        wsadCheckBox = new JCheckBox();
        arrowsUpMove = new JLabel("^ - ruch do góry");
        arrowsLeftMove = new JLabel("<- - ruch w lewo");
        arrowsRightMove = new JLabel("-> - ruch w prawo");
        wsadUpMove = new JLabel("w - ruch w górę");
        wsadLeftMove = new JLabel("a - ruch w lewo");
        wsadRightMove = new JLabel("d - ruch w prawo");

        arrowsCheckBoxLayout.setLayout(new BoxLayout(arrowsCheckBoxLayout, BoxLayout.Y_AXIS));
        wsadCheckBoxLayout.setLayout(new BoxLayout(wsadCheckBoxLayout, BoxLayout.Y_AXIS));

        okButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setBackground(Color.LIGHT_GRAY);
        arrowsCheckBox.setSelected(true);
        arrowsCheckBoxWasSelected = arrowsCheckBox.isSelected();
        arrowsCheckBox.setOpaque(false);
        wsadCheckBox.setSelected(false);
        wsadCheckBoxWasSelected = wsadCheckBox.isSelected();
        wsadCheckBox.setOpaque(false);
        arrowsUpMove.setForeground(Color.WHITE);
        arrowsLeftMove.setForeground(Color.WHITE);
        arrowsRightMove.setForeground(Color.WHITE);
        wsadUpMove.setForeground(Color.WHITE);
        wsadLeftMove.setForeground(Color.WHITE);
        wsadRightMove.setForeground(Color.WHITE);

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);
        arrowsCheckBox.addActionListener(this);
        wsadCheckBox.addActionListener(this);


        buttonBox.add(okButton);
        buttonBox.add(Box.createVerticalStrut(1));
        buttonBox.add(cancelButton);
        buttonBox.setOpaque(false);

        buttonBoxPackLayout.add(buttonBox);
        buttonBoxPackLayout.setOpaque(false);

        arrowsCheckBoxLayout.add(arrowsCheckBox);
        arrowsCheckBoxLayout.add(arrowsUpMove);
        arrowsCheckBoxLayout.add(arrowsLeftMove);
        arrowsCheckBoxLayout.add(arrowsRightMove);
        arrowsCheckBoxLayout.setOpaque(false);

        wsadCheckBoxLayout.add(wsadCheckBox);
        wsadCheckBoxLayout.add(wsadUpMove);
        wsadCheckBoxLayout.add(wsadLeftMove);
        wsadCheckBoxLayout.add(wsadRightMove);
        wsadCheckBoxLayout.setOpaque(false);

        connectingCheckBoxesLayout.add(arrowsCheckBoxLayout);
        connectingCheckBoxesLayout.add(Box.createVerticalStrut(1));
        connectingCheckBoxesLayout.add(wsadCheckBoxLayout);
        connectingCheckBoxesLayout.setOpaque(false);

        generalLayout.add(Box.createVerticalStrut(1));
        generalLayout.add(connectingCheckBoxesLayout);
        generalLayout.add(buttonBoxPackLayout);
        generalLayout.setOpaque(false);

        packLayout.add(generalLayout);
        packLayout.setOpaque(false);

        background.add(packLayout);

        add(background);

        setTitle(windowName);
        //setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                arrowsCheckBox.setSelected(arrowsCheckBoxWasSelected);
                wsadCheckBox.setSelected(wsadCheckBoxWasSelected);
                background.stopScaling();
                //setVisible(false);
                MenuWindow.Options.background.startScaling();
                MenuWindow.Options.setVisible(true);
            }
        });
        setSize(windowSizeXY[0], windowSizeXY[1]);
        setLocationRelativeTo(null);
    }

    /***
     * metoda odpowiedzialna za logikę przycisków oraz checkboxów
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals(okButton.getText())){
            arrowsCheckBoxWasSelected = arrowsCheckBox.isSelected();
            wsadCheckBoxWasSelected = wsadCheckBox.isSelected();
            background.stopScaling();
            setVisible(false);
            MenuWindow.Options.background.startScaling();
            MenuWindow.Options.setVisible(true);
        }
        else if (e.getActionCommand().equals(cancelButton.getText())){
            arrowsCheckBox.setSelected(arrowsCheckBoxWasSelected);
            wsadCheckBox.setSelected(wsadCheckBoxWasSelected);
            background.stopScaling();
            setVisible(false);
            MenuWindow.Options.background.startScaling();
            MenuWindow.Options.setVisible(true);
        }
        else if (e.getSource().equals(arrowsCheckBox) && wsadCheckBox.isSelected() == true){
            arrowsCheckBox.setSelected(true);
            wsadCheckBox.setSelected(false);
        }
        else if (e.getSource().equals(wsadCheckBox) && arrowsCheckBox.isSelected() == true){
            wsadCheckBox.setSelected(true);
            arrowsCheckBox.setSelected(false);
        }
    }

    /***
     * getter stanu checkboxa sterowania strzałkami
     * @return stan checkboxa sterowania strzałkami
     */
    public boolean getArrowsCheckBoxStatus() {
        return arrowsCheckBox.isSelected();
    }

    /***
     * getter stanu checkboxa sterowania WSAD
     * @return stan checkboxa sterowania WSAD
     */
    public boolean getWsadCheckBoxStatus() {
        return wsadCheckBox.isSelected();
    }

    /***
     * setter wymarów okna
     * @param windowSizeXY tablica wymiary okienka
     */
    @Override
    public void setWindowSizeXY(int[] windowSizeXY){
        this.windowSizeXY=windowSizeXY;
    }

    /***
     * setter nazwy okna
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
