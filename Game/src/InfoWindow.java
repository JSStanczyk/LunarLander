import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * klasa odpowiedzialna za rysowanie okna informacyjnego
 */
public class InfoWindow extends Window implements ActionListener {
    /***
     * windowName - nazwa okienka
     * windowID - jest wykorzystywane przy wczytywaniu parametrów przez LocalConfigurationLoader
     */
    private String windowName;
    private final String windowID="Info";
    /***
     * tablica rozmiarów okienka
     */
    private int[] windowSizeXY;
    /***
     * background opowiada za skalowanie tła okna
     */
    protected BackgroundMaker background;
    /**
     * przyciki
     * pola takstowe
     * layouty
     */
    private JButton okButton;
    private JLabel message;
    private JPanel buttonAndLabelLayout, generalPackLayout;

    /**
     * konstuktor
     */
    InfoWindow(){
        if (MenuWindow.serverIsOn) {
            ServerFileComunicationCenter.loadServerWindowConfig(this, windowID);
        }
        else {
            LocalFileComunicationCenter.loadLocalWindowConfig(this, windowID);
        }
/**
 * inicjalizacja GUI
 */
        okButton = new JButton("OK");
        message = new JLabel();
        buttonAndLabelLayout = new JPanel(new GridLayout(4,1));
        generalPackLayout = new JPanel(new GridBagLayout());

        background = new BackgroundMaker(this, "Game/images/RestWindowsBackground.png",false);

        okButton.addActionListener(this);

        okButton.setBackground(Color.LIGHT_GRAY);
        message.setForeground(Color.WHITE);

        buttonAndLabelLayout.add(Box.createVerticalStrut(1));
        buttonAndLabelLayout.add(message);
        buttonAndLabelLayout.add(Box.createVerticalStrut(1));
        buttonAndLabelLayout.add(okButton);
        buttonAndLabelLayout.setOpaque(false);

        generalPackLayout.add(buttonAndLabelLayout);
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

    /**
     * ustawia wiadomosc braku Nicku i wyswietla okno
     */
    public void noNickNameLayout(){
        message.setText("NickName is required");
        background.startScaling();
        setVisible(true);
    }
    /**
     * ustawia wiadomosc zbyt długiego Nicku i wyswietla okno
     */
    public void tooLongNickNameLauout(){
        message.setText("Your NickName is too long");
        background.startScaling();
        setVisible(true);
    }
    /**
     * ustawia wiadomosc złego formatu IP i wyswietla okno
     */
    public void wrongIPFormatLayout(){
        message.setText("IP format is wrong ex. 127.0.0.1");
        background.startScaling();
        setVisible(true);
    }
    /**
     * ustawia wiadomosc liter w adresie IP i wyswietla okno
     */
    public void lettersInIPInputLayout(){
        message.setText("IPv4 address must include only numbers");
        background.startScaling();
        setVisible(true);
    }
    /**
     * ustawia wiadomosc przekroczenia zakresu adresów IPv4 i wyswietla okno
     */
    public void IPNumberOutOfRange(){
        message.setText("Each part of IPv4 address have to be from range 0-255 ");
        background.startScaling();
        setVisible(true);
    }
    /**
     * ustawia wiadomosc liter w numerze Portu i wyswietla okno
     */
    public void lettersInPortInput(){
        message.setText("Port field must include only numbers");
        background.startScaling();
        setVisible(true);
    }
    /**
     * ustawia wiadomosc numeru portu poza zakresem i wyswietla okno
     */
    public void PortNumberOutOfRange(){
        message.setText("Port number must be from range 0 - 65535");
        background.startScaling();
        setVisible(true);
    }

    /**
     * metoda odpowiedzialna za logikę przycisku
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals(okButton.getText())){
            background.stopScaling();
            setVisible(false);
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
