import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/***
 * okno z najlepszymi wynikami
 */
public class LeaderBoardWindow extends Window implements ActionListener {
    /***
     * tablica rozmiarów okienka
     */
    private int[] windowSizeXY;
    /***
     * windowName - nazwa okienka
     * windowID - jest wykorzystywane przy wczytywaniu parametrów przez LocalConfigurationLoader
     */
    private String windowName;
    private final String windowID = "LeaderBoard";
    /***
     * opowiada za skalowanie tła okna
     */
    public BackgroundMaker background;
    /***
     * obiekt odpowiedzialny za przechowywanie i modyfikacje listy najlepszych wyników
     */
    public LeaderBoard leaderBoard;
    /**
     * przyciski
     */
    private final JButton okButton;
    /***
     * poszczególne wyniki
     */
    private JLabel firstPlaceLabel, secondPlaceLabel, thirdPlaceLabel, fourthPlaceLabel, fivethPlaceLabel,
                   sixthPlaceLabel, seventhPlaceLabel, eigthPlaceLabel, ninethPlaceLabel, tenthPlaceLabel, top10Label;
    /***
     *latouty do pozycjonowania przycisku, tabel oraz checkboxów
     */
    private final JPanel labelsAndButtonLayout, labelsAndButtonPackLayout,// firstPlaceLabelCenterLayout, secondPlaceLabelCenterLayout,
//                         thirdPlaceLabelCenterLayout, fourthPlaceLabelCenterLayout, fivethPlaceLabelCenterLayout, sixthPlaceLabelCenterLayout,
//                         seventhPlaceLabelCenterLayout, eigthPlaceLabelCenterLayout, ninethPlaceLabelCenterLayout, tenthPlaceLabelCenterLayout,
                         top10LabelCenterLayout;
    /***
     * konstruktor
     */
    LeaderBoardWindow(){
        if (MenuWindow.serverIsOn) {
            ServerFileComunicationCenter.loadServerWindowConfig(this, windowID);
        } else {
            LocalFileComunicationCenter.loadLocalWindowConfig(this, windowID);
        }
/***
 * inicjalizacja GUI
 */
        labelsAndButtonLayout = new JPanel(new GridLayout(14,1));
        labelsAndButtonPackLayout = new JPanel(new GridBagLayout());
//        firstPlaceLabelCenterLayout = new JPanel(new GridLayout(1,3));
//        secondPlaceLabelCenterLayout = new JPanel(new GridLayout(1,3));
//        thirdPlaceLabelCenterLayout = new JPanel(new GridLayout(1,3));
//        fourthPlaceLabelCenterLayout = new JPanel(new GridLayout(1,3));
//        fivethPlaceLabelCenterLayout = new JPanel(new GridLayout(1,3));
//        sixthPlaceLabelCenterLayout = new JPanel(new GridLayout(1,3));
//        seventhPlaceLabelCenterLayout = new JPanel(new GridLayout(1,3));
//        eigthPlaceLabelCenterLayout = new JPanel(new GridLayout(1,3));
//        ninethPlaceLabelCenterLayout = new JPanel(new GridLayout(1,3));
//        tenthPlaceLabelCenterLayout = new JPanel(new GridLayout(1,3));
        top10LabelCenterLayout = new JPanel(new GridLayout(1,3));

        background = new BackgroundMaker(this, "Game/images/RestWindowsBackground.png", false);
        leaderBoard = new LeaderBoard();

        okButton = new JButton("OK");
        top10Label = new JLabel("TOP 10:");

        //leaderBoard.loadList();

        firstPlaceLabel = new JLabel();
        secondPlaceLabel = new JLabel();
        thirdPlaceLabel = new JLabel();
        fourthPlaceLabel = new JLabel();
        fivethPlaceLabel = new JLabel();
        sixthPlaceLabel = new JLabel();
        seventhPlaceLabel = new JLabel();
        eigthPlaceLabel = new JLabel();
        ninethPlaceLabel = new JLabel();
        tenthPlaceLabel = new JLabel();

        okButton.addActionListener(this);

        okButton.setBackground(Color.LIGHT_GRAY);
        top10Label.setForeground(Color.WHITE);
        firstPlaceLabel.setForeground(Color.WHITE);
        secondPlaceLabel.setForeground(Color.WHITE);
        thirdPlaceLabel.setForeground(Color.WHITE);
        fourthPlaceLabel.setForeground(Color.WHITE);
        fivethPlaceLabel.setForeground(Color.WHITE);
        sixthPlaceLabel.setForeground(Color.WHITE);
        seventhPlaceLabel.setForeground(Color.WHITE);
        eigthPlaceLabel.setForeground(Color.WHITE);
        ninethPlaceLabel.setForeground(Color.WHITE);
        tenthPlaceLabel.setForeground(Color.WHITE);

        top10LabelCenterLayout.add(Box.createVerticalStrut(1));
        top10LabelCenterLayout.add(top10Label);
        top10LabelCenterLayout.add(Box.createVerticalStrut(1));
        top10LabelCenterLayout.setOpaque(false);

        labelsAndButtonLayout.add(Box.createVerticalStrut(1));
        labelsAndButtonLayout.add(top10LabelCenterLayout);
        labelsAndButtonLayout.add(firstPlaceLabel);
        labelsAndButtonLayout.add(secondPlaceLabel);
        labelsAndButtonLayout.add(thirdPlaceLabel);
        labelsAndButtonLayout.add(fourthPlaceLabel);
        labelsAndButtonLayout.add(fivethPlaceLabel);
        labelsAndButtonLayout.add(sixthPlaceLabel);
        labelsAndButtonLayout.add(seventhPlaceLabel);
        labelsAndButtonLayout.add(eigthPlaceLabel);
        labelsAndButtonLayout.add(ninethPlaceLabel);
        labelsAndButtonLayout.add(tenthPlaceLabel);
        labelsAndButtonLayout.add(Box.createVerticalStrut(1));
        labelsAndButtonLayout.add(okButton);
        labelsAndButtonLayout.setOpaque(false);

        labelsAndButtonPackLayout.add(labelsAndButtonLayout);
        labelsAndButtonPackLayout.setOpaque(false);

        background.add(labelsAndButtonPackLayout);

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
        setVisible(false);
    }

    /***
     * metoda odpowiedzialna za wypisywanie wyników
     */
    public void updateLeaderBoard(){
        firstPlaceLabel.setText(leaderBoard.getPlayerName(0)+"  "+leaderBoard.getScore(0));
        secondPlaceLabel.setText(leaderBoard.getPlayerName(1)+"  "+leaderBoard.getScore(1));
        thirdPlaceLabel.setText(leaderBoard.getPlayerName(2)+"  "+leaderBoard.getScore(2));
        fourthPlaceLabel.setText(leaderBoard.getPlayerName(3)+"  "+leaderBoard.getScore(3));
        fivethPlaceLabel.setText(leaderBoard.getPlayerName(4)+"  "+leaderBoard.getScore(4));
        sixthPlaceLabel.setText(leaderBoard.getPlayerName(5)+"  "+leaderBoard.getScore(5));
        seventhPlaceLabel.setText(leaderBoard.getPlayerName(6)+"  "+leaderBoard.getScore(6));
        eigthPlaceLabel.setText(leaderBoard.getPlayerName(7)+"  "+leaderBoard.getScore(7));
        ninethPlaceLabel.setText(leaderBoard.getPlayerName(8)+"  "+leaderBoard.getScore(8));
        tenthPlaceLabel.setText(leaderBoard.getPlayerName(9)+"  "+leaderBoard.getScore(9));
    }

    /**
     * geter obiektu przechowującego wyniki
     * @return
     */
    public LeaderBoard getLeaderBoard(){
        return leaderBoard;
    }
    /***
     * metoda odpowiedzialna za logikę przycisków oraz checkboxów
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(okButton.getText())){
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

}
