import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

/***
 * Klasa odpowedzialna za wyświetlanie onka Autorów
 */

public class CreditsWindow extends Window implements ActionListener {
    /***
     * tablica rozmiarów okienka
     */
    private int[] windowSizeXY;
    /***
     * windowName - nazwa okienka
     * windowID - jest wykorzystywane przy wczytywaniu parametrów przez LocalConfigurationLoader
     */
    private String windowName;
    private final String windowID="Credits";
    /***
     * latouty do pozycjonowania przycisku i tabel
     */
    private final JPanel layout, innerLayout, labels, box;
    /***
     * przycisk
     */
    private final JButton okButton;
    /***
     * tabele
     */
    private final JLabel authors, firstAuthorName, secondAuthorName;
    /***
     * background opowiada za skalowanie tła okna
     */
    public BackgroundMaker background;

    /***
     * konstruktor
     */
    CreditsWindow(){
        if (MenuWindow.serverIsOn) {
            ServerFileComunicationCenter.loadServerWindowConfig(this, windowID);
        }
        else {
            LocalFileComunicationCenter.loadLocalWindowConfig(this, windowID);
        }


        /***
         * inicjalizacja GUI
         */
        labels = new JPanel(new GridLayout(2,1));
        innerLayout = new JPanel(new GridBagLayout());
        layout = new JPanel(new GridBagLayout());
        okButton = new JButton("OK");
        authors = new JLabel("Autorzy:");
        firstAuthorName = new JLabel("Wiktor Komorowski");
        secondAuthorName = new JLabel("Paweł Hojda");
        box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        background = new BackgroundMaker(this, "Game/images/RestWindowsBackground.png",false);

        okButton.addActionListener(this);
        okButton.setBackground(Color.LIGHT_GRAY);
        authors.setForeground(Color.WHITE);
        firstAuthorName.setForeground(Color.WHITE);
        secondAuthorName.setForeground(Color.WHITE);

        box.add(authors);
        box.add(Box.createVerticalStrut(5));
        box.add(firstAuthorName);
        box.add(secondAuthorName);
        box.setOpaque(false);

        innerLayout.add(okButton);
        innerLayout.setOpaque(false);

        labels.add(box);
        labels.add(innerLayout);
        labels.setOpaque(false);

        layout.add(labels);
        layout.setOpaque(false);

        background.add(layout);

        add(background);

        setTitle(windowName);
        //setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
     * metoda odpowiedzialna za logikę przycisku
     * @param pressed
     */
    @Override
    public void actionPerformed(ActionEvent pressed){
        if(pressed.getActionCommand().equals(okButton.getText())){
            background.stopScaling();
            setVisible(false);
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
