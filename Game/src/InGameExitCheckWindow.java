import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InGameExitCheckWindow extends Window implements ActionListener {

    private int[] windowSizeXY;

    private String windowName;
    private final String windowID="InGameExitCheck";

    private final JButton noButton, yesButton;

    private final JPanel layout, buttonBox, innerLayout;

    private final JLabel question;

    public BackgroundMaker background;


    InGameExitCheckWindow() {
        if (MenuWindow.serverIsOn) {
            ServerFileComunicationCenter.loadServerWindowConfig(this, windowID);
        } else {
            LocalFileComunicationCenter.loadLocalWindowConfig(this, windowID);
        }

        buttonBox = new JPanel(new GridLayout(1, 3));
        innerLayout = new JPanel(new GridLayout(4, 1));
        layout = new JPanel(new GridBagLayout());
        noButton = new JButton("Nie");
        yesButton = new JButton("Tak");
        question = new JLabel("Czy napewno chcesz wyjść do Menu?");
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
        //setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                background.stopScaling();
            }
        });
        setSize(windowSizeXY[0], windowSizeXY[1]);
        setLocationRelativeTo(null);
    }



    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals(yesButton.getText())){

        }
        else if(e.getActionCommand().equals(noButton.getText())){
            background.stopScaling();
            setVisible(false);
        }
    }


    @Override
    public void setWindowSizeXY(int[] windowSizeXY){
        this.windowSizeXY=windowSizeXY;
    }


    @Override
    public void setWindowName(String windowName){
        this.windowName=windowName;
    }


    @Override
    public int getWindowSizeX(){
        return windowSizeXY[0];
    }


    @Override
    public int getWindowSizeY(){
        return windowSizeXY[1];
    }
}
