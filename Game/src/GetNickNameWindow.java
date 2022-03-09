import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/***
 * Klasa odpowiedzialna za wyświetlanie okna pobierania nazwy gracza
 */
public class GetNickNameWindow extends Window implements ActionListener, KeyListener {
    /***
     * nazwa gracza
     * windowName - nazwa okienka
     * windowID - jest wykorzystywane przy wczytywaniu parametrów przez LocalConfigurationLoader
     */
    private String nickname, windowName;
    private final String windowID="GetNickName";
    /***
     * tablica rozmiarów okienka
     */
    private int[] windowSizeXY;
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
    private JTextField input;
    private JLabel info;
    private JButton okButton, cancelButton;
    private JPanel buttonsLayout, buttonsPackLayout, buttonsAndTextFiledLayout, generalPackLayout;

    /**
     * konstuktor
     */
    GetNickNameWindow(){
/**
 * pobranue danych z pliku
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
        input = new JTextField();
        info = new JLabel("Enter your nickname");
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        buttonsLayout = new JPanel(new GridLayout(1,3));
        buttonsPackLayout = new JPanel(new GridBagLayout());
        buttonsAndTextFiledLayout = new JPanel(new GridLayout(5,1));
        generalPackLayout = new JPanel(new GridBagLayout());

        background = new BackgroundMaker(this, "Game/images/RestWindowsBackground.png",false);
        infoWindow = new InfoWindow();

        okButton.addActionListener(this);
        cancelButton.addActionListener(this);
        input.addKeyListener(this);

        okButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setBackground(Color.LIGHT_GRAY);
        info.setForeground(Color.WHITE);

        buttonsLayout.add(okButton);
        buttonsLayout.add(Box.createVerticalStrut(1));
        buttonsLayout.add(cancelButton);
        buttonsLayout.setOpaque(false);

        buttonsPackLayout.add(buttonsLayout);
        buttonsPackLayout.setOpaque(false);

        buttonsAndTextFiledLayout.add(Box.createVerticalStrut(1));
        buttonsAndTextFiledLayout.add(info);
        buttonsAndTextFiledLayout.add(input);
        buttonsAndTextFiledLayout.add(Box.createVerticalStrut(1));
        buttonsAndTextFiledLayout.add(buttonsPackLayout);
        buttonsAndTextFiledLayout.setOpaque(false);

        generalPackLayout.add(buttonsAndTextFiledLayout);
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
     * metoda odpowiedzialna za logikę przycisku
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals(okButton.getText())){
                nickname = input.getText();
                if(nickname.length()>0 && nickname.length()<=20){
                    MenuWindow.gameBoardWindow.setPlayer(new Player(nickname));
                    MenuWindow.Options.switchInGameOutGameLayout();
                    MenuWindow.gameBoardWindow.applyOfflineOnlineMode();
                    MenuWindow.gameBoardWindow.applyDifficultyLevel();
                    MenuWindow.gameBoardWindow.gameBoardEngine.startScaling();
                    MenuWindow.gameBoardWindow.setVisible(true);
                    MenuWindow.background.stopScaling();
                    MenuWindow.background.stopAnimation();
                    Main.Menu.setVisible(false);
                    background.stopScaling();
                    setVisible(false);
                    input.setText("");
                }
                else if(nickname.length()==0){
                    input.setText("");
                    infoWindow.noNickNameLayout();
                }
                else{
                    input.setText("");
                    infoWindow.tooLongNickNameLauout();
                }
        }
        else if(e.getActionCommand().equals(cancelButton.getText())){
            background.stopScaling();
            setVisible(false);
            input.setText("");
        }
    }
    /**
     * metoda odpowiedzialna za logikę klawiatury
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            nickname = input.getText();
            if(nickname.length()>0 && nickname.length()<=20){
                MenuWindow.gameBoardWindow.setPlayer(new Player(nickname));
                MenuWindow.Options.switchInGameOutGameLayout();
                MenuWindow.gameBoardWindow.applyOfflineOnlineMode();
                MenuWindow.gameBoardWindow.applyDifficultyLevel();
                MenuWindow.gameBoardWindow.gameBoardEngine.startScaling();
                MenuWindow.gameBoardWindow.setVisible(true);
                MenuWindow.background.stopScaling();
                MenuWindow.background.stopAnimation();
                Main.Menu.setVisible(false);
                background.stopScaling();
                setVisible(false);
                input.setText("");
            }
            else if(nickname.length()==0){
                input.setText("");
                infoWindow.noNickNameLayout();
            }
            else{
                input.setText("");
                infoWindow.tooLongNickNameLauout();
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
