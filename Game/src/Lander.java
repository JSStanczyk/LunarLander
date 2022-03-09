/***
 * Klasa odpowiedzialna z obsluge Landera
 */

public class Lander {
    /***
     * lives, maxLives, fuel, maxFuel to odpowiednio: ilosc zyc, maksymalna ilosc zyc, paliwo, obecne paliwo
     * landerHitBox to rozmiar ladownika
     * enginePower to moc silnika wykorzystywana przy obliczaniu pozycji ladownika
     * properVelocityXY, maxVelocityXY, currentVelocityXY, startVelocityXY, startPositionXY ,currentPositionXY to odpowiednio: wektor maksymalnej predkości przy ktorej ladowanie jest uznane za udane, maksymalna predkosc ladownika, obecna predkosc ladownika, startowa predkosc ladownika, startowa pozycja ladownika, obecna pozycja ladownika
     */
    private int lives, maxLives, modifiedLives, modifiedMaxLives, fuel, maxFuel;
    private int[] landerHitBox;
    private double enginePower, difficultyLevelMultiplicator;
    private double[] properVelocityXY, maxVelocityXY, currentVelocityXY, startVelocityXY, startPositionXY ,currentPositionXY;

    /***
     * Konstruktor wykorzytujacy klase LocalConfigurationLoader jako lacznika z plikami konfiguracyjnymi
     */
    Lander(){
        if (MenuWindow.serverIsOn) {
            ServerFileComunicationCenter.loadServerLunarConfig(this);
        }
        else {
            LocalFileComunicationCenter.loadLocalLunarConfig(this);
        }
    }

    /***
     * Setter ilosci paliwa w Landerze
     * @param fuel ilosc paliwa
     */
    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    /**
     * setter maksymalnej liczby paliwa
     * @param maxFuel
     */
    public void setMaxFuel(int maxFuel){
        this.maxFuel = maxFuel;
    }

    /***
     * Setter HitBoxów Landera
     * @param landerHitBox HitBoxy Landera
     */
    public void setLanderHitBoxXY(int[] landerHitBox) {
        this.landerHitBox = landerHitBox;
    }

    /***
     * Setter ilosci zyc
     * @param lives ilosc zyc
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * setter maksymalnej liczby zyc
     * @param maxLives
     */
    public void setMaxLives(int maxLives){
        this.maxLives = maxLives;
    }

    /**
     * setter zmodyfikowanej przez poziom trudnosci liczby zyc
     * @param modifiedLives
     */
    public void setModifiedLives(int modifiedLives){
        this.modifiedLives = modifiedLives;
    }

//    public void setModifiedMaxLives(int modifiedMaxLives) {
//        this.modifiedMaxLives = modifiedMaxLives;
//    }

    /***
     * Setter maksymalnej predkosci
     * @param maxVelocity maksymalnie osiagalna predkosc
     */
    public void setMaxVelocityXY(double[] maxVelocity) {
        this.maxVelocityXY = maxVelocity;
    }

    /***
     * Setter predkości odpowiedniej do poprawdnego ladowania
     * @param properVelocity predkosc, przy ktorej mozna skutecznie wyladowac
     */
    public void setProperVelocityXY(double[] properVelocity) {
        this.properVelocityXY = properVelocity;
    }

    /***
     * Setter poczatkowej predkości Landera
     * @param startVelocity predkosc poczatkowa
     */
    public void setStartVelocityXY(double[] startVelocity) {
        this.currentVelocityXY = startVelocity.clone();
        this.startVelocityXY = startVelocity.clone();
    }

    /***
     * Setter startowej pozycji ladownika
     * @param startPositionXY startowa pozycja ladownika
     */
    public void setStartPositionXY(double[] startPositionXY){
        this.currentPositionXY = startPositionXY.clone();
        this.startPositionXY = startPositionXY.clone();
    }

//    public void setCurrentPositionX(double currentPositionX) {
//        this.currentPositionXY[0] = currentPositionX;
//    }
//
//    public void setCurrentPositionY(double currentPositionY) {
//        this.currentPositionXY[1] = currentPositionY;
//    }

    /***
     * Setter mocy silnika
     * @param enginePower moc silnika
     */
    public void setEnginePower(double enginePower){
        this.enginePower = enginePower;
    }

    /***
     * Setter obecnej wspolrzednej X
     * @param positionX obecna wspolrzedna X
     */
    public void setPositionX(double positionX) {
        this.currentPositionXY[0] = positionX;
    }

    /***
     * Setter obecnej wspolrzednej Y
     * @param positionY obecna wspolrzedna Y
     */
    public void setPositionY(double positionY) {
        this.currentPositionXY[1] = positionY;
    }

    /***
     * Setter obecnej predkosci w kierunku X
     * @param curretVelocityX wartosc x skladowej predkosci
     */
    public void setCurrentVelocityX(double curretVelocityX) {
        this.currentVelocityXY[0] = curretVelocityX;
    }

    /***
     * Setter obecnej predkości w kierunku Y
     * @param curretVelocityY wartosc Y skladowej predkosci
     */
    public void setCurrentVelocityY(double curretVelocityY) {
        this.currentVelocityXY[1] = curretVelocityY;
    }

    /***
     * Getter ilosci paliwa
     * @return zwraca ilosc paliwa
     */
    public int getFuel() {
        return fuel;
    }

    /**
     * getter maksymalnej ilosci paliwa
     * @return
     */
    public int getMaxFuel() {
        return maxFuel;
    }

    /***
     * Getter HitBoxow Landera
     * @return zwraca HitBoxy Landera
     */
    public int getLanderHitBoxX() {
        return landerHitBox[0];
    }

    /**
     * getter HitBoxow Landera
     * @return
     */
    public int getLanderHitBoxY() {
        return landerHitBox[1];
    }

    /***
     * Getter ilosci zyc
     * @return zwraca ilosc zyc
     */
    public int getModifiedLives() {
        return modifiedLives;
    }

    /**
     * getter maksymalnej ilości żyć zmodyfikowanej przez poziom trudnosci ilosci zyc
     * @return
     */
    public int getModifiedMaxLives() {
        return modifiedMaxLives;
    }

    /***
     * Getter maksymalnej predkosci Landera
     * @return zwraca maksymalna predkosc Landera
     */
    public double[] getMaxVelocity() {
        return maxVelocityXY;
    }

    /***
     * Getter predkosci odpowiedniej do poprawdnego ladowania
     * @return zwraca predkosc odpowiednią do poprawnego ladowania
     */
    public double[] getProperVelocity() {
        return properVelocityXY;
    }

    /***
     * Getter predkości poczatkowej Landera
     * @return zwraca predkość poczatkową Landera
     */
//    public double[] getStartVelocity() {
//        return startVelocityXY;
//    }

    /***
     *Getter mocy silnika
     * @return moc silnika
     */
    public double getEnginePower(){
        return enginePower;
    }

//    public double getStartPositionX() {
//        return startPositionXY[0];
//    }
//
//    public double getStartPositionY() {
//        return startPositionXY[1];
//    }

    /***
     * Getter obecnej pozycji
     * @return obecna pozycja ladownika
     */
    public double getCurrentPositionX() {
        return currentPositionXY[0];
    }

    /**
     * getter obecnej wpolrzednej Y landera
     * @return
     */

    public double getCurrentPositionY() {
        return currentPositionXY[1];
    }

    /***
     * Getter obecnej predkosci ladownika
     * @return predkość ladownika
     */
    public double getCurretVelocityX() {
        return currentVelocityXY[0];
    }

    /**
     * getter obecnej predkosci landera w kieruku Y
     * @return
     */

    public double getCurretVelocityY() {
        return currentVelocityXY[1];
    }

    /***
     * move to metoda odpowiedzialna za obliczanie obecnej pozycji ladownika
     */
    public void move() {
        currentPositionXY[0] += currentVelocityXY[0];
        currentPositionXY[1] += currentVelocityXY[1];
    }

    /***
     * reset to metoda odpowiedzialna za resetowanie stanu paliwa i pozycji ladownika
     */
    public void reset(){
        currentPositionXY = startPositionXY.clone();
        fuel = maxFuel;
        currentVelocityXY = startVelocityXY.clone();
    }

    /***
     * restoreLives to metoda odpowiedzialna za przywracanie maxsymalnej liczby zyc
     */
    public void restoreLives(){
        modifiedLives = modifiedMaxLives;
    }

    /**
     * metoda ustawiająca mnożnik wynikający z poziomu trudności ustawiony
     * @param difficultyLevelMultiplicator
     */

    public void setDifficultyLevelMultiplicator(double difficultyLevelMultiplicator){
        this.difficultyLevelMultiplicator = difficultyLevelMultiplicator;
    }

    /**
     * metoda modyfikujaca liczbe zyc
     */
    public void modfiyLivesByDifficultyLevel(){
        modifiedLives = (int)(lives*(1/difficultyLevelMultiplicator));
        modifiedMaxLives = (int)(maxLives*(1/difficultyLevelMultiplicator));
    }
}