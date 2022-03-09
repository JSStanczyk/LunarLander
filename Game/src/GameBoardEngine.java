import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Timer;
import java.awt.event.*;
import java.util.TimerTask;

/***
 * klasa odpowiedzialna za logike gry
 */
public class GameBoardEngine extends JPanel implements ActionListener{
    /***
     * scaling, gamePlay to liczniki odpowiedzialne za skalowanie okna i animację gry
     * scalingTask, gamePlayTask to zadania przypisane licznikom
     * lander - obiekt typu Lander
     * map-obiekt typu map
     * player- obiekt typu player
     * options- obiekt typu OptionsWindow
     * gameOver- obiekt typu GameOverWindow
     */
    private final Timer scaling, gamePlay;
    private TimerTask scalingTask, gamePlayTask;
    private final Lander lander;
    private final Map map;
    private Player player;
    private final OptionsWindow options;
    private final GameOverWindow gameOver;
    private LeaderBoard leaderBoard;
    private ExitCheckWindow exitCheck;
    /***
     * elementy sluzace do budowy interfaceu
     */
    private final JButton resumeButton, exitButton, optionsButton, startButton;
    private final JPanel buttonBox, packLayout;
    private BufferedImage background, UpMoveLanderIcon, RightMoveLanderIcon, LeftMoveLanderIcon, UpAndLeftMoveLanderIcon, UpAndRightMoveLanderIcon,
            LeftAndRightLanderIcon, NothingLanderIcon, EverythingLanderIcon, fineLanderIcon, destroyedLanderIcon, onlineIcon, offlineIcon;
    /***
     * obrazy ladownika
     */
    private Image landerIcon;
    private final Image resizedUpMoveLanderIcon, resizedRightMoveLanderIcon, resizedLeftMoveLanderIcon, resizedUpAndLeftMoveLanderIcon, resizedUpAndRightMoveLanderIcon,
            resizedLeftAndRightLanderIcon, resizedNothingLanderIcon, resizedEverythingLanderIcon, resizedFineLanderIcon, resizedDestroyedLanderIcon, resizedOnlineIcon, resizedOfflineIcon;
    private final DecimalFormat numberFormat;
    /***
     * pola typu boolean sluzace do okreslania kierunku odrzutu
     */
    private boolean moveLeft, moveRight, moveUp;
    /**
     * pole słuzace do ustalania czy gra jest w stanie pauzy
     */
    protected boolean inGame = false;
    /***
     * pola uzywane do skalowania
     */
    private double scaleX = 1, scaleY = 1;
    private final int[] scalingWindowSizeXY;
    private final int period = 17;
    private int mapNumber = 1, backgroundX, backgroundY, distance;

    /***
     * konstruktor GameBoardEngine
     * @param options obiekt typu OptionsWindow
     * @param scalingWindowSizeXY bazowe wymiary GameBoardWindow potzrebne do skalowania
     */
    public GameBoardEngine(OptionsWindow options, LeaderBoard leaderBoard, ExitCheckWindow exitCheck, int[] scalingWindowSizeXY){
        lander=new Lander();
        map = new Map(mapNumber);
        numberFormat = new DecimalFormat("#.##");
        gameOver = new GameOverWindow();
        scaling =new Timer();
        gamePlay = new Timer();
        this.leaderBoard = leaderBoard;
        this.exitCheck = exitCheck;

        /***
         * wczytywanie zdjęec
         */
        try {
            background = ImageIO.read(new File("Game/images/GameBoardBackground.png"));
            UpMoveLanderIcon = ImageIO.read(new File("Game/images/UpLanderIcon.png"));
            RightMoveLanderIcon = ImageIO.read(new File("Game/images/RightLanderIcon.png"));
            LeftMoveLanderIcon = ImageIO.read(new File("Game/images/LeftLanderIcon.png"));
            UpAndLeftMoveLanderIcon = ImageIO.read(new File("Game/images/UpLeftLanderIcon.png"));
            UpAndRightMoveLanderIcon = ImageIO.read(new File("Game/images/UpRightLanderIcon.png"));
            LeftAndRightLanderIcon = ImageIO.read(new File("Game/images/LeftRightLanderIcon.png"));
            NothingLanderIcon = ImageIO.read(new File("Game/images/NothingLanderIcon.png"));
            EverythingLanderIcon = ImageIO.read(new File("Game/images/EverythingLanderIcon.png"));
            fineLanderIcon = ImageIO.read(new File("Game/images/NothingLanderIcon.png"));
            destroyedLanderIcon = ImageIO.read(new File("Game/images/DestroyedLanderIcon.png"));
            offlineIcon = ImageIO.read(new File("Game/images/offlineIcon.png"));
            onlineIcon = ImageIO.read(new File("Game/images/onlineIcon.png"));
        }
        catch (IOException e){
            e.printStackTrace();
        }
/***
 * skalowanie zdjec
 */
        resizedUpMoveLanderIcon = UpMoveLanderIcon.getScaledInstance(lander.getLanderHitBoxX(), lander.getLanderHitBoxY(), Image.SCALE_SMOOTH);
        resizedRightMoveLanderIcon = RightMoveLanderIcon.getScaledInstance(lander.getLanderHitBoxX(), lander.getLanderHitBoxY(), Image.SCALE_SMOOTH);
        resizedLeftMoveLanderIcon = LeftMoveLanderIcon.getScaledInstance(lander.getLanderHitBoxX(), lander.getLanderHitBoxY(), Image.SCALE_SMOOTH);
        resizedUpAndLeftMoveLanderIcon = UpAndLeftMoveLanderIcon.getScaledInstance(lander.getLanderHitBoxX(), lander.getLanderHitBoxY(), Image.SCALE_SMOOTH);
        resizedUpAndRightMoveLanderIcon = UpAndRightMoveLanderIcon.getScaledInstance(lander.getLanderHitBoxX(), lander.getLanderHitBoxY(), Image.SCALE_SMOOTH);
        resizedLeftAndRightLanderIcon = LeftAndRightLanderIcon.getScaledInstance(lander.getLanderHitBoxX(), lander.getLanderHitBoxY(), Image.SCALE_SMOOTH);
        resizedNothingLanderIcon = NothingLanderIcon.getScaledInstance(lander.getLanderHitBoxX(), lander.getLanderHitBoxY(), Image.SCALE_SMOOTH);
        resizedEverythingLanderIcon = EverythingLanderIcon.getScaledInstance(lander.getLanderHitBoxX(), lander.getLanderHitBoxY(), Image.SCALE_SMOOTH);
        resizedFineLanderIcon = fineLanderIcon.getScaledInstance(20,20,Image.SCALE_SMOOTH);
        resizedDestroyedLanderIcon = destroyedLanderIcon.getScaledInstance(20,20,Image.SCALE_SMOOTH);
        resizedOfflineIcon = offlineIcon.getScaledInstance(20,20,Image.SCALE_SMOOTH);
        resizedOnlineIcon = onlineIcon.getScaledInstance(20,20,Image.SCALE_SMOOTH);

        landerIcon = resizedNothingLanderIcon;
/***
 * projektowanie GUI
 */
        buttonBox = new JPanel(new GridLayout(9,1));
        packLayout = new JPanel(new GridBagLayout());

        resumeButton = new JButton("Resume");
        optionsButton = new JButton("Options");
        exitButton = new JButton("Exit to Menu");
        startButton = new JButton("Start");

        resumeButton.addActionListener(this);
        optionsButton.addActionListener(this);
        exitButton.addActionListener(this);
        startButton.addActionListener(this);

        resumeButton.setBackground(Color.LIGHT_GRAY);
        optionsButton.setBackground(Color.LIGHT_GRAY);
        exitButton.setBackground(Color.LIGHT_GRAY);
        startButton.setBackground(Color.LIGHT_GRAY);

        resumeButton.setFocusable(false);
        optionsButton.setFocusable(false);
        exitButton.setFocusable(false);
        startButton.setFocusable(false);

        resumeButton.setVisible(false);
        optionsButton.setVisible(false);
        exitButton.setVisible(false);
        startButton.setVisible(true);

        buttonBox.add(resumeButton);
        buttonBox.add(Box.createVerticalStrut(1));
        buttonBox.add(optionsButton);
        buttonBox.add(Box.createVerticalStrut(1));
        buttonBox.add(Box.createVerticalStrut(1));
        buttonBox.add(exitButton);
        buttonBox.add(Box.createVerticalStrut(1));
        buttonBox.add(Box.createVerticalStrut(1));
        buttonBox.add(startButton);
        buttonBox.setOpaque(false);

        packLayout.add(buttonBox);
        packLayout.setOpaque(false);

        add(packLayout);

        this.options = options;
        this.scalingWindowSizeXY = scalingWindowSizeXY;
/***
 * ustawianie pozycji tla i rozmiaru
 */
        moveBackground();

        setSize(scalingWindowSizeXY[0], scalingWindowSizeXY[1]);
    }

    /***
     * Setter pola typu player
     * @param player obiekt gracza
     */
    public void setPlayer(Player player){
        this.player = player;
    }

    /**
     * metoda pozawalająca zastosować poziom trudności do Landera
     */
    public void modifyLanderByDifficultyLevel(){
        lander.setDifficultyLevelMultiplicator(options.getInterpretedDifficultyLevel());
        lander.modfiyLivesByDifficultyLevel();
    }

    /**
     * metoda pozawalająca zastosować poziom trudności do Mapy
     */
    public void modifyMapByDifficultyLevel(){
        map.setDifficultyLevelMultiplicator(options.getInterpretedDifficultyLevel());
        map.set_reset();
    }

    /**
     * metoda wybor trybu gry wybranego przez gracza
     */
    public void applyOfflineOnlineMode(){
        try {
            if (MenuWindow.serverIsOn) {
                ServerFileComunicationCenter.loadServerMapConfig(map, mapNumber);
            } else {
                LocalFileComunicationCenter.loadLocalMapConfig(map, mapNumber);
            }
            map.set_reset();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (mapNotFoundException m){
            System.err.println(m);
        }
    }

    /**
     * metoda sprawdzająca polaczenie z serwerem
     */
    public void checkConnectivity(){
        if(MenuWindow.serverIsOn) {
            MenuWindow.serverIsOn = ServerFileComunicationCenter.checkIfServerIsOnline();
        }
    }

    /***
     * metoda sluzaca do rozpoczecia gry
     */
    private void startGamePlay(){
        pause();
    }

    /***
     * metoda odpowiedzialna za skalowanie okna
     */
    public void startScaling(){
        scalingTask = new TimerTask(){
            @Override
            public void run() {
                calculateScaleXY();
                repaint();
            }
        };
        scaling.schedule(scalingTask, 0, period);
    }

    /**
     * metoda resetujaca pola wskazujace na kierunek odrzutu
     */
    private void resetMovingButtons(){
        moveLeft = false;
        moveRight = false;
        moveUp = false;
    }

    /***
     * metoda sluzaca do resetowania poziomu
     */
    protected void restart(){
        checkConnectivity();
        try {
            if (!inGame) inGame=!inGame;
            resetMovingButtons();
            pause();
            /***
             * resetowanie stanu ladownika i chowanie przyciskow oraz wczytywanie parametrow odpowiedniej mapy i tworzenie jej pol
             */
            lander.reset();
            moveBackground();
            resumeButton.setVisible(false);
            optionsButton.setVisible(false);
            exitButton.setVisible(false);
            startButton.setVisible(true);
            if (MenuWindow.serverIsOn) {
                ServerFileComunicationCenter.loadServerMapConfig(map, mapNumber);
            }
            else {
                LocalFileComunicationCenter.loadLocalMapConfig(map, mapNumber);
            }
            map.set_reset();
        }
        catch (IOException e){
            mapNumber = 1;
            resetMovingButtons();
            moveBackground();
            try {
                if (MenuWindow.serverIsOn) {
                    ServerFileComunicationCenter.loadServerMapConfig(map, mapNumber);
                }
                else {
                    LocalFileComunicationCenter.loadLocalMapConfig(map, mapNumber);
                }
                //leaderBoard.updateScore(player);
                map.set_reset();
                //options.switchInGameOutGameLayout();
                gameOver.winLayout(player);
                gameOver.background.startScaling();
                gameOver.setVisible(true);
                //MenuWindow.gameBoardWindow.setVisible(false);
            }
            catch (IOException t){
                t.printStackTrace();
            }
            catch (mapNotFoundException m){
                System.err.println(m);
            }
        }
        catch (mapNotFoundException m){
            mapNumber = 1;
            resetMovingButtons();
            moveBackground();
            try {
                if (MenuWindow.serverIsOn) {
                    ServerFileComunicationCenter.loadServerMapConfig(map, mapNumber);
                }
                else {
                    LocalFileComunicationCenter.loadLocalMapConfig(map, mapNumber);
                }
                //leaderBoard.updateScore(player);
                map.set_reset();
                //options.switchInGameOutGameLayout();
                gameOver.winLayout(player);
                gameOver.background.startScaling();
                gameOver.setVisible(true);
                //MenuWindow.gameBoardWindow.setVisible(false);
            }
            catch (IOException t){
                t.printStackTrace();
            }
            catch (mapNotFoundException mm){
                System.err.println(mm);
            }
        }
    }

    /**
     * metoda zawierajaca wytyczne do rysowania zyc pozosalych graczowi
     * @param g2d
     */
    private void drawLanderHP(Graphics2D g2d){
        distance = (lander.getLanderHitBoxX()+5)*lander.getModifiedMaxLives();
        int numberOfLives = lander.getModifiedLives();
        g2d.drawString("Lives: ",scalingWindowSizeXY[0]-(distance+41),25);
        for(int i=1; i<=lander.getModifiedMaxLives(); i++){

            if(numberOfLives>0){
                g2d.drawImage(resizedFineLanderIcon, scalingWindowSizeXY[0]-distance, 10, this);
            }
            else{
                g2d.drawImage(resizedDestroyedLanderIcon, scalingWindowSizeXY[0]-distance, 10, this);
            }

            distance-=(lander.getLanderHitBoxX()+5);
            numberOfLives--;
        }
    }

    /**
     * metoda rysująca progresywny pasek paliwa
     * @param g2d
     */
    private void drawFuelBar(Graphics2D g2d){
        g2d.setColor(Color.YELLOW);
        g2d.fillRect(870,48,(int)(120*((double)lander.getFuel()/(double)lander.getMaxFuel())), 15);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Fuel:",840, 60);
    }

    /**
     * metoda rysujaca ikonę stanu polaczenia z serwerem
     * @param g2d
     */
    private void drawConnectivityIcon(Graphics2D g2d){
        if(MenuWindow.serverIsOn){
            g2d.drawImage(resizedOnlineIcon, 930,140, this);
        }
        else{
            g2d.drawImage(resizedOfflineIcon, 930,140,this);
        }
    }

    /***
     * metoda odpowiedzialna za rysowanie planszy
     * @param g obiekt klasy graphics
     */
    private void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        /***
         * ustawienie skalowania
         */
        g2d.scale(scaleX, scaleY);
/***
 * rysowanie elementow planszy
 */
        g2d.drawImage(background, backgroundX, backgroundY, this);

        g2d.drawImage(landerIcon, (int) lander.getCurrentPositionX(), (int) lander.getCurrentPositionY(), this);


        g2d.setColor(map.getPlatform1Color());
        g2d.fillRect((int)map.getPlatform1().getX(),(int)map.getPlatform1().getY(),(int)map.getPlatform1().getWidth(),(int)map.getPlatform1().getHeight());
        g2d.setColor(map.getPlatform2Color());
        g2d.fillRect((int)map.getPlatform2().getX(),(int)map.getPlatform2().getY(),(int)map.getPlatform2().getWidth(),(int)map.getPlatform2().getHeight());

        g2d.setColor(map.getPlanetColor());
        g2d.fillPolygon(map.getPlanet());

        /***
         * wypisywanie stanu ladownika
         */
        drawFuelBar(g2d);
        drawLanderHP(g2d);
        drawConnectivityIcon(g2d);
        g2d.drawString("Score: "+numberFormat.format(player.getScore()),910,120);
        g2d.drawString("Vx: "+numberFormat.format(lander.getCurretVelocityX()*10), 940, 90);
        g2d.drawString("Vy: "+numberFormat.format(lander.getCurretVelocityY()*10), 880, 90);

        g2d.dispose();
    }

    /***
     * przyciemnianie planszy gry w trakcie pauzy
     * @param g obiekt klasy graphics
     */
    private void drawPauseBackground(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(0,0,0,80));
        g2d.fillRect(this.getX(), this.getY(), this.getWidth()+20, this.getHeight()+20);

        g2d.dispose();
    }

    /***
     * przeciazenie metody paintComponent rysuje plansze gry
     * @param g obiekt klasy graphics
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
        /***
         * przyciemnianie gry w stanie wstrzymania
         */
        if (!inGame){
           drawPauseBackground(g);
        }
    }

    /***
     * poruszanie tlem w zaleznosci od pozycji ladownika
     */
    private void moveBackground(){
        backgroundX = -(int)(lander.getCurrentPositionX()/5+50);
        backgroundY = -(int)(lander.getCurrentPositionY()/5+20);
    }

    /***
     * sprawdzanie kolozji ladownika z planeta i zmniejsznie liczby zyc
     */
    private void checkCollision(){
        if(map.getPlanetCollision().intersects(lander.getCurrentPositionX(), lander.getCurrentPositionY(), lander.getLanderHitBoxX(), lander.getLanderHitBoxY())){
            lander.setModifiedLives(lander.getModifiedLives()-1);
            if (lander.getModifiedLives()>0){
                //landerIcon = resizedDestroyedLanderIcon;
                restart();
            }
            else{
                //landerIcon = resizedDestroyedLanderIcon;
                /***
                 * komunikat o przegranej
                 */
                //JOptionPane.showMessageDialog(this,"Rozbiles się");
                gameOver.loseLayout();
                gameOver.background.startScaling();
                gameOver.setVisible(true);
                //leaderBoard.updateScore(player);
                restart();
                lander.restoreLives();
                //scalingTask.cancel();
                //options.switchInGameOutGameLayout();
                //MenuWindow.gameBoardWindow.exitToMenu();
            }
        }
    }

    /***
     * wykrywanie poprawnego ladowania, liczenie punktow i zwiekszanie numeru mapy
     */
    private void checkWin(){
        for (int i = 0; i<2; i++) {
            if (map.getPlatforms().get(i).contains(lander.getCurrentPositionX(), lander.getCurrentPositionY() + lander.getLanderHitBoxY()) && map.getPlatforms().get(i).contains(lander.getCurrentPositionX() + lander.getLanderHitBoxX(), lander.getCurrentPositionY() + lander.getLanderHitBoxY())) {
                if (lander.getCurretVelocityX()<=(lander.getProperVelocity()[0]*(1/options.getInterpretedDifficultyLevel()))/10 && lander.getCurretVelocityY()<=lander.getProperVelocity()[1]*(1/options.getInterpretedDifficultyLevel())/10){
                    mapNumber++;
                    player.addToScore((int)(map.getPlatformesScore()[i]*options.getInterpretedDifficultyLevel()*(lander.getFuel()/((1/options.getInterpretedDifficultyLevel())*200))));
                    restart();
                }
                else{
                    lander.setModifiedLives(lander.getModifiedLives()-1);
                    if (lander.getModifiedLives()>0){
                        //landerIcon = resizedDestroyedLanderIcon;
                        restart();
                    }
                    else{
                        //landerIcon = resizedDestroyedLanderIcon;
                        /***
                         * komunikat o przegranej
                         */
                        //JOptionPane.showMessageDialog(this,"Rozbiles się");
                        gameOver.loseLayout();
                        gameOver.background.startScaling();
                        gameOver.setVisible(true);
                        //leaderBoard.updateScore(player);
                        restart();
                        lander.restoreLives();
                        //scalingTask.cancel();
                        //options.switchInGameOutGameLayout();
                        //MenuWindow.gameBoardWindow.exitToMenu();
                    }
                }
            }
        }
    }

    /***
     * skalowanie planszy
     */
    private void calculateScaleXY(){
        scaleX = this.getWidth()/(double)scalingWindowSizeXY[0];
        scaleY = this.getHeight()/(double)scalingWindowSizeXY[1];
    }

    public void exitToMenu(){
        mapNumber = 1;
        //restart();
        leaderBoard.updateScore(player);
        scalingTask.cancel();
        options.switchInGameOutGameLayout();
    }

    /***
     * obsluga przyciskow
     * @param e obiekt klasy ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals(resumeButton.getText())){
            pause();
        }
        else if (e.getActionCommand().equals(optionsButton.getText())){
            options.background.startScaling();
            options.setVisible(true);
        }
        else if (e.getActionCommand().equals(exitButton.getText())){
            exitCheck.background.startScaling();
            exitCheck.setVisible(true);
        }
        else if (e.getActionCommand().equals(startButton.getText())){
            startGamePlay();
        }
    }

    /***
     * wykrycie wcisnietego przycisku klawiatury
     * sterowanie ladownikiem w dwuch wersjach sterowania(WSAD i strzalki)
     * @param e obiekt klasy ActionEvent
     */
    public void keyPressed(KeyEvent e){
        if (options.getWsadCheckBoxStatus()){
            if(e.getKeyChar() == 'a')
            {
                moveLeft = true;
            }
            if(e.getKeyChar() == 'd')
            {
                moveRight = true;
            }
            if(e.getKeyChar() == 'w')
            {
                moveUp = true;
            }
        }
        else if (options.getArrowsCheckBoxStatus()){
            if(e.getKeyCode() == KeyEvent.VK_LEFT)
            {
                moveLeft = true;
            }
            if(e.getKeyCode() == KeyEvent.VK_RIGHT)
            {
                moveRight = true;
            }
            if(e.getKeyCode() == KeyEvent.VK_UP)
            {
                moveUp = true;
            }
        }
    }

    /***
     *wykrycie zwolnienia przycisku klawiatury
     * @param e obiekt klasy ActionEvent
     */
    public void keyReleased(KeyEvent e){
        if (options.getWsadCheckBoxStatus()) {
            if (e.getKeyChar() == 'a') {
                moveLeft = false;
            }
            if (e.getKeyChar() == 'd') {
                moveRight = false;
            }
            if (e.getKeyChar() == 'w') {
                moveUp = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                pause();
            }
        }
        else if (options.getArrowsCheckBoxStatus()){
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                moveLeft = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                moveRight = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                moveUp = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                pause();
            }
        }
//        if(e.getKeyCode()==KeyEvent.VK_ENTER){
//            startGamePlay();
//        }
    }

    /***
     * modyfikowanie predkosci ladownika i zmniejszanie ilosci paliwa oraz zmiana ikony ladownika w zaleznosci od wcisnietych przyciskow klawiatury
     */
    private void makeMove(){
        if (lander.getFuel()>0) {
            if (!moveLeft && !moveRight && !moveUp){
                landerIcon = resizedNothingLanderIcon;
            }
            if (moveLeft && moveRight) {
                landerIcon = resizedLeftAndRightLanderIcon;
                lander.setFuel((int)(lander.getFuel() - (0.75*options.getInterpretedDifficultyLevel())));
            }
            if (moveUp && !moveRight && !moveLeft) {
                landerIcon = resizedUpMoveLanderIcon;
                lander.setCurrentVelocityY(lander.getCurretVelocityY() - lander.getEnginePower());
                lander.setFuel((int)(lander.getFuel() - (0.5*options.getInterpretedDifficultyLevel())));
            }
            if (moveRight && !moveLeft) {
                landerIcon = resizedRightMoveLanderIcon;
                lander.setCurrentVelocityX(lander.getCurretVelocityX() + lander.getEnginePower());
                lander.setFuel((int)(lander.getFuel() - (0.5*options.getInterpretedDifficultyLevel())));
            }
            if (moveLeft && !moveRight) {
                landerIcon = resizedLeftMoveLanderIcon;
                lander.setCurrentVelocityX(lander.getCurretVelocityX() - lander.getEnginePower());
                lander.setFuel((int)(lander.getFuel() - (0.5*options.getInterpretedDifficultyLevel())));
            }
            if (moveUp && moveRight && !moveLeft) {
                landerIcon = resizedUpAndRightMoveLanderIcon;
                lander.setCurrentVelocityY(lander.getCurretVelocityY() - lander.getEnginePower());
                lander.setCurrentVelocityX(lander.getCurretVelocityX() + lander.getEnginePower());
                lander.setFuel((int)(lander.getFuel() - (0.75*options.getInterpretedDifficultyLevel())));
            }
            if (moveUp && moveLeft && !moveRight) {
                landerIcon = resizedUpAndLeftMoveLanderIcon;
                lander.setCurrentVelocityY(lander.getCurretVelocityY() - lander.getEnginePower());
                lander.setCurrentVelocityX(lander.getCurretVelocityX() - lander.getEnginePower());
                lander.setFuel((int)(lander.getFuel() - (0.75*options.getInterpretedDifficultyLevel())));
            }
            if (moveUp && moveRight && moveLeft) {
                landerIcon = resizedEverythingLanderIcon;
                lander.setFuel((int)(lander.getFuel() - (1*options.getInterpretedDifficultyLevel())));
            }
        }
        else{
            landerIcon = resizedNothingLanderIcon;
        }
    }

    /***
     * wlaczanie/wylaczanie pauzy
     */
    protected void pause(){
        /**
         * wlaczenie pauzy
         * zatrzymanie gry i wyswietlenie przyciskow
         */
        if(inGame){
            inGame=!inGame;

            gamePlayTask.cancel();

            resumeButton.setVisible(true);
            optionsButton.setVisible(true);
            exitButton.setVisible(true);
        }
        /**
         * wznawianie gry i ukrycie gozikow
         */
        else if(!inGame){
            inGame=!inGame;

            resumeButton.setVisible(false);
            optionsButton.setVisible(false);
            exitButton.setVisible(false);
            startButton.setVisible(false);
/**
 * sekwecja gry
 */
            gamePlayTask = new TimerTask() {
                @Override
                public void run() {
                    lander.setCurrentVelocityY(lander.getCurretVelocityY()+map.getGravity());
                    lander.move();
                    moveBackground();
                    makeMove();
                    checkCollision();
                    checkWin();
                }
            };
           gamePlay.schedule(gamePlayTask,0, period);
        }
    }
}