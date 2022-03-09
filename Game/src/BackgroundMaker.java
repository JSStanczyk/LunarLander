import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * klasa odpowiedzialna za tworzenie tla
 */
public class BackgroundMaker extends JPanel {
    /**
     * nazwy plikow
     */
    private String backgroundFileName, titleFileName, landerIconFileName;
    /**
     * obrazy wykorzystywane do tworzenia tla
     */
    private BufferedImage background, title, landerIcon, onlineIcon, offlineIcon;
    private Image resizedLanderIcon, resizedOnlineIcon, resizedOfflineIcon;
    private final Window frame;
    /**
     * liczniki animacji i skalowania i skorelowane z nimi akcje
     */
    private Timer animationTimer, scalingTimer;
    private TimerTask animationTimerTask, scalingTimerTask;
    /**
     * parametry skalowania
     */
    private double scaleX, scaleY;
    /**
     * wspolrzedne i predkosci elementow tla i okresy zegarow
     */
    private int backgroundY, titleX, titleY, scalingPeriod = 17, animationPeriod = 17,
                connectivityIconX, connectivityIconY, connectivityLabelX, connectivityLabelY;

    private double backgroundX, landerIconX, landerIconY, backgroundVelocityX, landerVelocityX, landerVelocityY;
    /**
     * pole informujace czy tlo ma byc animowane czy nie
     */
    private boolean isAnimated;

    /**
     * konstruktor
     * @param frame okno dla ktorego bedzie tworzone tlo
     * @param backgroundFileName plik z obrazem tla
     * @param titleFileName plik z tytulem
     * @param landerIconFileName plik z obrazem ladownika
     * @param isAnimated informacja czy tlo ma byc animowane
     */
    BackgroundMaker(Window frame, String backgroundFileName, String titleFileName, String landerIconFileName, String onlineIconFileName, String offlineIconFileName, boolean isAnimated) {
        this.backgroundFileName = backgroundFileName;
        this.titleFileName = titleFileName;
        this.isAnimated = isAnimated;
        this.frame = frame;
/**
 * pobieranie zdjec
 */
        try {
            background = ImageIO.read(new File(backgroundFileName));
            title = ImageIO.read(new File(titleFileName));
            offlineIcon = ImageIO.read(new File(offlineIconFileName));
            onlineIcon = ImageIO.read(new File(onlineIconFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * pozycjonowanie tla i tytulu
         */
        backgroundX = -57;
        backgroundY = -10;
        titleX = (int)(frame.getWindowSizeX()/2-title.getWidth()/2);
        titleY = (int)(frame.getWindowSizeY()/48);
        connectivityIconX = frame.getWindowSizeX()-90;
        connectivityIconY = 50;
        connectivityLabelX = frame.getWindowSizeX()-170;
        connectivityLabelY = 70;
/**
 * jesli animowany pobiera zdjecie ladownika i nadaje predkosc mu i tlu
 */
        if (this.isAnimated) {
            backgroundVelocityX = 0.5;
            landerVelocityX = 2;
            landerVelocityY = -2;
            this.landerIconFileName = landerIconFileName;
            try {
                landerIcon = ImageIO.read(new File(landerIconFileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
/**
 * skalowanie obrazu, pozycjonowanie ladownika i startowanie licznikow
 */
            resizedLanderIcon = landerIcon.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            resizedOfflineIcon = offlineIcon.getScaledInstance(30,30,Image.SCALE_SMOOTH);
            resizedOnlineIcon = onlineIcon.getScaledInstance(30,30,Image.SCALE_SMOOTH);

            landerIconX = 0;
            landerIconY = frame.getWindowSizeY();
/**
 * startowanie licznikow
 */
            animationTimer = new Timer();
        }
            scalingTimer = new Timer();
    }

    /**
     * konstruktor
     * @param frame okno dla którego będzie tworzone tlo
     * @param backgroundFileName nazwa pliku z obrazem tla
     * @param isAnimated informacja czy tlo ma byc animowane
     */
    BackgroundMaker(Window frame, String backgroundFileName, boolean isAnimated){
        this.backgroundFileName = backgroundFileName;
        this.isAnimated = isAnimated;
        this.frame = frame;
/**
 * pobranie zdjecia tla i jego pozycjonowanie
 */
        try {
            background = ImageIO.read(new File(backgroundFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        backgroundX = 0;
        backgroundY = 0;

        scalingTimer = new Timer();
    }

    /**
     * metoda odpowiedzialna za rysowanie animowanego tla
     * @param g
     */
    private void drawAnimation(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        /**
         * ustawianie parametrow skalowania
         */
        g2d.scale(scaleX, scaleY);
        /**
         * rysowanie elementow tla
         */
        g2d.drawImage(background, (int)backgroundX, backgroundY, frame);

        g2d.drawImage(resizedLanderIcon, (int)landerIconX, (int)landerIconY, frame);

        g2d.drawImage(title, titleX, titleY, frame);

        if(MenuWindow.serverIsOn){
            g2d.drawImage(resizedOnlineIcon, connectivityIconX, connectivityIconY, frame);
        }
        else{
            g2d.drawImage(resizedOfflineIcon, connectivityIconX, connectivityIconY, frame);
        }

        g2d.setColor(Color.WHITE);
        g2d.drawString("Connectivity:", connectivityLabelX, connectivityLabelY);

        g2d.dispose();
    }

    /**
     * metoda odpowiedzialna za rysowanie statycznego tla
     * @param g
     */
    private void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        /**
         * ustawianie parametrow skalowania
         */
        g2d.scale(scaleX, scaleY);
        /**
         * rysowanie elementow tla
         */
        g2d.drawImage(background, (int)backgroundX, backgroundY, frame);

        g2d.dispose();
    }

    /**
     * przeciazona metoda paintComponent wyswietlajaca w zaleznosci od wybranej opcji tlo statyczne lub animowane
     * @param g
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (isAnimated){
            drawAnimation(g);
        }
        else{
            draw(g);
        }
    }

    /**
     * ustawianie parametrow skalowania tla
     */
    private void calculateScaleXY(){
        scaleX = frame.getWidth()/(double)frame.getWindowSizeX();
        scaleY = frame.getHeight()/(double)frame.getWindowSizeY();
    }

    /**
     * zmiana wsporzednych elementow animowanego tla
     */
    private void animateBackground(){
        /**
         * animacja
         */
        backgroundX = backgroundX+backgroundVelocityX;
        landerIconX = landerIconX+landerVelocityX;
        landerIconY = landerIconY+landerVelocityY;
        if (backgroundX <= -110 || backgroundX >= 0){
            backgroundVelocityX=-backgroundVelocityX;
        }
        if (landerIconX>=frame.getWindowSizeX() || landerIconY<=-frame.getWindowSizeY()){
            landerIconX = 0;
            landerIconY = frame.getWindowSizeY();
        }
    }

    /**
     * metoda inicjujaca skalowanie
     */
    public void startScaling(){
        scalingTimerTask = new TimerTask() {
            @Override
            public void run() {
                calculateScaleXY();
                repaint();
            }
        };
        scalingTimer.schedule(scalingTimerTask, 0, scalingPeriod);
    }

    /**
     * inicjowanie animacji
     */
    public void startAnimation(){
        animationTimerTask = new TimerTask() {
            @Override
            public void run() {
                animateBackground();
            }
        };
        animationTimer.schedule(animationTimerTask, 0,animationPeriod);
    }

    /**
     * zatrzymanie skalowania
     */
    public void stopScaling(){
        scalingTimerTask.cancel();
    }

    /**
     * zatrzymanie animacji
     */
    public void stopAnimation(){
        animationTimerTask.cancel();
    }
}