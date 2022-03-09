import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/***
 * Klasa odpowiedzialna za wyświetlanie okna pobierania nazwy gracza
 */
public class GetIPAndPortWindow extends Window implements ActionListener, KeyListener {
    /***
     * nazwa gracza
     * windowName - nazwa okienka
     * windowID - jest wykorzystywane przy wczytywaniu parametrów przez LocalConfigurationLoader
     */
    private String windowName;
    private final String windowID="GetIPAndPort";
    /**
     * zmienna wykorzystywana przy sprawdzaniu poprawności wprowadzonego IP
     */
    private String[] tmp;
    /***
     * tablica rozmiarów okienka
     */
    private int[] windowSizeXY;
    /**
     * zmienna przechowująca numer portu
     */
    private int port;
    /**
     * zmienna sygnalizujaca podanie portu wykraczajacego poza dostepny przedzial
     */
    private boolean outOfRange;
    /***
     * background opowiada za skalowanie tła okna
     */
    protected BackgroundMaker background;
    /**
     * okno informacyjne
     */
    private InfoWindow infoWindow;
    /**
     * pola do wpisywania danych
     * pola tekstowe
     * przyciski
     * layouty
     */
    private JTextField ipInput, portInput;
    private JLabel ipLabel, portLabel, info1, info2;
    private JButton okButton, cancelButton;
    private JPanel buttonsLayout, buttonsPackLayout, infoLabelsLayout, ipLabelAndInputLayout, portLabelAndInputLayout,
                   buttonsAndTextFiledsLayout, generalPackLayout;

    /**
     * konstuktor
     */
    GetIPAndPortWindow(){
/**
 * wczytanie danych z pliku
 */
        if (MenuWindow.serverIsOn) {
            ServerFileComunicationCenter.loadServerWindowConfig(this, windowID);
        }
        else {
            LocalFileComunicationCenter.loadLocalWindowConfig(this, windowID);
        }

        /**
         * inicjalizacja GUI
         */
        ipInput = new JTextField();
        portInput = new JTextField();
        info1 = new JLabel("Previous server adress is invalid");
        info2 = new JLabel("Enter new server adress");
        ipLabel = new JLabel("IP: ");
        portLabel = new JLabel("Port: ");
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        buttonsLayout = new JPanel(new GridLayout(1,3));
        buttonsPackLayout = new JPanel(new GridBagLayout());
        infoLabelsLayout = new JPanel(new GridLayout(2,1));
        ipLabelAndInputLayout = new JPanel(new GridLayout(2,1));
        portLabelAndInputLayout = new JPanel(new GridLayout(2,1));
        buttonsAndTextFiledsLayout = new JPanel(new GridLayout(6,1));
        generalPackLayout = new JPanel(new GridBagLayout());

        background = new BackgroundMaker(this, "Game/images/RestWindowsBackground.png",false);
        infoWindow = new InfoWindow();

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);
        ipInput.addKeyListener(this);
        portInput.addKeyListener(this);

        okButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setBackground(Color.LIGHT_GRAY);
        info1.setForeground(Color.WHITE);
        info2.setForeground(Color.WHITE);
        ipLabel.setForeground(Color.WHITE);
        portLabel.setForeground(Color.WHITE);

        buttonsLayout.add(okButton);
        buttonsLayout.add(Box.createVerticalStrut(1));
        buttonsLayout.add(cancelButton);
        buttonsLayout.setOpaque(false);

        buttonsPackLayout.add(buttonsLayout);
        buttonsPackLayout.setOpaque(false);

        infoLabelsLayout.add(info1);
        infoLabelsLayout.add(info2);
        infoLabelsLayout.setOpaque(false);

        ipLabelAndInputLayout.add(ipLabel);
        ipLabelAndInputLayout.add(ipInput);
        ipLabelAndInputLayout.setOpaque(false);

        portLabelAndInputLayout.add(portLabel);
        portLabelAndInputLayout.add(portInput);
        portLabelAndInputLayout.setOpaque(false);

        buttonsAndTextFiledsLayout.add(Box.createVerticalStrut(1));
        buttonsAndTextFiledsLayout.add(infoLabelsLayout);
        buttonsAndTextFiledsLayout.add(ipLabelAndInputLayout);
        buttonsAndTextFiledsLayout.add(portLabelAndInputLayout);
        buttonsAndTextFiledsLayout.add(Box.createVerticalStrut(1));
        buttonsAndTextFiledsLayout.add(buttonsPackLayout);
        buttonsAndTextFiledsLayout.setOpaque(false);

        generalPackLayout.add(buttonsAndTextFiledsLayout);
        generalPackLayout.setOpaque(false);

        background.add(generalPackLayout);

        add(background);

        setTitle(windowName);
        setSize(windowSizeXY[0], windowSizeXY[1]);
        /**
         * ustawienie reakcji na kliknięcie czerwonego X
         */
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                background.stopScaling();
            }
        });
        setLocationRelativeTo(null);
    }

    /***
     * metoda odpowiedzialna za logikę przycisku
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals(okButton.getText())){
            tmp = ipInput.getText().split("\\.");
            if(tmp.length == 4){
                try{
                    for(String elem : tmp) {
                        if(Integer.parseInt(elem)<0 || Integer.parseInt(elem)>255){
                            outOfRange=true;
                        }
                    }
                }
                catch (NumberFormatException n){
                    infoWindow.lettersInIPInputLayout();
                }
                if(!outOfRange) {
                    try {
                        port = Integer.parseInt(portInput.getText());
                        if (port < 0 || port > 65535) {
                            infoWindow.PortNumberOutOfRange();
                        } else {
                            ServerFileComunicationCenter.setIP(ipInput.getText());
                            ServerFileComunicationCenter.setPortNumber(port);
                            background.stopScaling();
                            setVisible(false);
                            ipInput.setText("");
                            portInput.setText("");
                        }
                    } catch (NumberFormatException N) {
                        infoWindow.lettersInPortInput();
                    }
                }
                else {
                    infoWindow.IPNumberOutOfRange();
                }
            }
            else{
                infoWindow.wrongIPFormatLayout();
            }
        }
        else if(e.getActionCommand().equals(cancelButton.getText())){
            background.stopScaling();
            setVisible(false);
            ipInput.setText("");
            portInput.setText("");
        }
    }

    /***
     * metoda odpowiedzialna za logikę klawiatury
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            tmp = ipInput.getText().split("\\.");
            if(tmp.length == 4){
                try{
                    for(String elem : tmp) {
                        Integer.parseInt(elem);
                    }
                    try{
                        port = Integer.parseInt(portInput.getText());
                        if(port<0 || port>65535){
                            infoWindow.PortNumberOutOfRange();
                        }
                        else{
                            ServerFileComunicationCenter.setIP(ipInput.getText());
                            ServerFileComunicationCenter.setPortNumber(port);
                            background.stopScaling();
                            setVisible(false);
                            ipInput.setText("");
                            portInput.setText("");
                        }
                    }
                    catch (NumberFormatException n){
                        infoWindow.lettersInPortInput();
                    }
                }
                catch (NumberFormatException b){
                    infoWindow.lettersInIPInputLayout();
                }
            }
            else{
                infoWindow.wrongIPFormatLayout();
            }
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

    @Override
    public void keyTyped(KeyEvent e){}

    @Override
    public void keyPressed(KeyEvent e){}
}
