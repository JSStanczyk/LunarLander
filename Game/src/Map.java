import java.awt.*;
import java.awt.geom.Area;
import java.io.IOException;
import java.util.ArrayList;

/***
 * Klasa odpowiedzialna za przechowywanie parametrów oraz tworzenie map.
 */

public class Map {
    /***
     * gravity przewchowuje stałą grawitacji
     * mapNumber przechowuje numer danej mapy
     */
    private int mapNumber;
    private double gravity, difficultyLevelMultiplicator = 1;
    /***
     * landingPlatformSizeX, landingPlatformSizeY są tablicami przechowującymi współrzędne odpowiednio X i Y punktów na bazie których będzie tworzona platforma do lądowania
     * mapParametersX, mapParametersY są tablicami przechowującymi współrzędne odpowiednio X i Y punktów na bazie których będzie tworzona mapa
     * planet, platform1, platform2 są polygonami stworzonymi na bazie punktów sczytanych z landingPlatformSizeX, landingPlatformSizeY,mapParametersX, mapParametersY
     * planetColor, platform1Color, platform2Color są kolorami planety i lądowisk zapisanych jako 3 wartości RGB
     * planetCollision Area stworzona na bazie współrzędnych planety służy do wykrywania kolizji z lądownikiem
     * platforms ArrayList przechowujący Area stworzone na bazie współrzędnych lądowisk, służy do wykrywania kolizji lądownika z lądowiskiem
     */
    private int[] landingPlatform1Position, modifiedLandingPlatform1Size, landingPlatform1Size, landingPlatform2Position, modifiedLandingPlatform2Size, landingPlatform2Size, mapParametersX, mapParametersY,
            planetColorArray, platform1ColorArray, platform2ColorArray, platformesScore;
    private Polygon planet;
    private Rectangle  platform1, platform2;
    private Color planetColor, platform1Color, platform2Color;
    private Area planetCollision;
    private ArrayList<Area> platforms;

    /***
     * Konstruktor wykorzytujący klasę LocalConfigurationLoader jako łącznika z plikami konfiguracyjnymi
     * @param mapNumber numer mapy
     */
    Map(int mapNumber){
        this.mapNumber=mapNumber;
        try {
            if (MenuWindow.serverIsOn) {
                ServerFileComunicationCenter.loadServerMapConfig(this, this.mapNumber);
                modifiedLandingPlatform1Size = new int[2];
                modifiedLandingPlatform2Size = new int[2];
                set_reset();
            }
            else {
                LocalFileComunicationCenter.loadLocalMapConfig(this, this.mapNumber);
                modifiedLandingPlatform1Size = new int[2];
                modifiedLandingPlatform2Size = new int[2];
                set_reset();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (mapNotFoundException m){
            System.err.println(m);
        }
    }

    /***
     * Setter stałej grawitacji
     * @param gravity stała grawitacji
     */
    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    /***
     * Setter współrzędnych X punktów tworzących platformy do lądowania
     * @param landingPlatformSize współrzędne X puntów tworzących platformy do lądowania
     */
    public void setLandingPlatform1Size(int[] landingPlatformSize) {
        this.landingPlatform1Size = landingPlatformSize;
    }

    public void setLandingPlatform2Size(int[] landingPlatform2Size) {
        this.landingPlatform2Size = landingPlatform2Size;
    }

    /***
     * Setter współrzędnych Y punktów tworzących platformy do lądowania
     * @param landingPlatformPosition współrzędne Y punktów tworzących platformy do lądowania
     */
    public void setLandingPlatform1Position(int[] landingPlatformPosition) {
        this.landingPlatform1Position = landingPlatformPosition;
    }

    public void setLandingPlatform2Position(int[] landingPlatformPosition) {
        this.landingPlatform2Position = landingPlatformPosition;
    }

    /***
     * Setter numeru mapy
     * @param mapNumber numer mapy
     */
    public void setMapNumber(int mapNumber) {
        this.mapNumber = mapNumber;
    }

    /***
     * Setter współrzędnych X punktów tworzących mapę
     * @param mapParametersX współrzędne X punktów tworzących mapę
     */
    public void setMapParametersX(int[] mapParametersX) {
        this.mapParametersX = mapParametersX;
    }

    /***
     * Setter współrzędnych Y punktów tworzących mapę
     * @param mapParametersY współrzędne Y punktów tworzących mapę
     */
    public void setMapParametersY(int[] mapParametersY) {
        this.mapParametersY = mapParametersY;
    }

    /***
     * Setter pola definiującego kolor planety
     * @param planetColorArray kolor planety zapisany jako RGB
     */

    public void setPlanetColorArray(int[] planetColorArray){
        this.planetColorArray = planetColorArray;
    }

    /***
     * Setter pola definiującego kolor pierwszego lądowiska
     * @param platformColorArray kolor pierwszego lądowiska zapisany jako RGB
     */
    public void setPlatform1ColorArray(int[] platformColorArray) {
        this.platform1ColorArray = platformColorArray;
    }

    /***
     * Setter pola definiującego kolor drugiego lądowiska
     * @param platform2ColorArray kolor drugiego lądowiska zapisany jako RGB
     */
    public void setPlatform2ColorArray(int[] platform2ColorArray) {
        this.platform2ColorArray = platform2ColorArray;
    }

    /***
     * Setter punktacji za kolejne lądowiska
     * @param platformesScore punkty przypisane kolejnym lądowiskom
     */
    public void setPlatformesScore(int[] platformesScore) {
        this.platformesScore = platformesScore;
    }

    /***
     * Getter stałej grawitacji
     * @return zwraca stałą grawitacji
     */
    public double getGravity() {
        return gravity;
    }

//    /***
//     * Getter współrzędnych Y punktów tworzących mapę
//     * @return zwraca współrzędne Y punktów tworzących mapę
//     */
//    public int[] getMapParametersY() {
//        return mapParametersY;
//    }

    /***
     * Getter numeru mapy
     * @return zwraca numer mapy
     */
    public int getMapNumber() {
        return mapNumber;
    }


//    /***
//     * Getter współrzędnych X punktów tworzących platformy do lądowania
//     * @return zwraca współrzędne X punktów tworzących platformy do lądowania
//     */
//    public int[] getLandingPlatformSizeX() {
//        return landingPlatformSizeX;
//    }
//
//    /***
//     * Getter współrzędnych Y punktów tworzących platformy do lądowania
//     * @return współrzędne Y punktów tworzących platformy do lądowania
//     */
//    public int[] getLandingPlatformSizeY() {
//        return landingPlatformSizeY;
//    }
//
//    /***
//     * Getter współrzędnych X punktów tworzących mapę
//     * @return zwraca współrzędne X punktów tworzących mapę
//     */
//    public int[] getMapParametersX() {
//        return mapParametersX;
//    }

    /***
     * getter punktacji za lądowiska
     * @return punktacje przypisaną kolejnym lądowiskom
     */
    public int[] getPlatformesScore() {
        return platformesScore;
    }

    /***
     * Getter współrzędnych planety
     * @return współrzędne planety jako Polygon
     */
    public Polygon getPlanet() {
        return planet;
    }

    /***
     * Getter współrzędnych pierwszego lądowiska
     * @return współrzędne pierwszego lądowiska jako Polygon
     */
    public Rectangle getPlatform1(){return platform1;}
    /***
     * Getter współrzędnych drugiego lądowiska
     * @return współrzędne drugiego lądowiska jako Polygon
     */
    public Rectangle getPlatform2() {
        return platform2;
    }

    /***
     * Getter koloru planety
     * @return kolor planety zapisany jako RGB
     */
    public Color getPlanetColor() {
        return planetColor;
    }

    /***
     * Getter koloru pierwszego lądowiska
     * @return kolor pierwszego lądowiska zapisany jako RGB
     */
    public Color getPlatform1Color() {
        return platform1Color;
    }
    /***
     * Getter koloru drugiego lądowiska
     * @return kolor drugiego lądowiska zapisany jako RGB
     */
    public Color getPlatform2Color() {
        return platform2Color;
    }

    /***
     *Getter "wykrywacza kolizji" z planetą
     * @return zwraca obiekt odpowiedzialny za wykrywanie kolizji z planetą
     */
    public Area getPlanetCollision() {
        return planetCollision;
    }

    /***
     * Getter kontenera przechowującego współrzędne lądowisk
     * @return zwraca kontener przechowujący współrzędne lądowisk
     */
    public ArrayList<Area> getPlatforms() {
        return platforms;
    }

    /***
     * set_reset odpowiedzialny za tworzenie pól obiektu map
     */
    public void set_reset(){
        planet = new Polygon(mapParametersX, mapParametersY, mapParametersX.length);
        modfiyMapParametersByDifficultyLevel();
        platform1= new Rectangle(landingPlatform1Position[0], landingPlatform1Position[1],modifiedLandingPlatform1Size[0],modifiedLandingPlatform1Size[1]);
        platform2 = new Rectangle(landingPlatform2Position[0], landingPlatform2Position[1],modifiedLandingPlatform2Size[0],modifiedLandingPlatform2Size[1]);
        planetColor = new Color(planetColorArray[0], planetColorArray[1], planetColorArray[2]);
        platform1Color = new Color(platform1ColorArray[0], platform1ColorArray[1], platform1ColorArray[2]);
        platform2Color = new Color(platform2ColorArray[0], platform2ColorArray[1], platform2ColorArray[2]);
        planetCollision = new Area(planet);
        platforms = new ArrayList<>();
        platforms.add(new Area(platform1));
        platforms.add(new Area(platform2));
    }

    /***
     * seter pola służącego do modyfikowania gry w zależności od poziomu trudności
     * @param difficultyLevelMultiplicator
     */
    public void setDifficultyLevelMultiplicator(double difficultyLevelMultiplicator){
        this.difficultyLevelMultiplicator = difficultyLevelMultiplicator;
    }
    /***
     * metoda odpowiedzialna za modyfikowanie mapy w zależności od poziomu trudności
     */
    public void modfiyMapParametersByDifficultyLevel(){
        modifiedLandingPlatform1Size[0]=(int)(landingPlatform1Size[0]*(0.5+1/(2*difficultyLevelMultiplicator)));
        modifiedLandingPlatform1Size[1]=(int)(landingPlatform1Size[1]);
        modifiedLandingPlatform2Size[0]=(int)(landingPlatform2Size[0]*(0.5+1/(2*difficultyLevelMultiplicator)));
        modifiedLandingPlatform2Size[1]=(int)(landingPlatform2Size[1]);

//        for(int i = 0; i<4; i++){
//            if(i==1 || i==2){
//                modifiedLandingPlatform1SizeX[i]=(int)(landingPlatform1SizeX[i]*(1/difficultyLevelMultiplicator));
//                modifiedLandingPlatform2SizeX[i]=(int)(landingPlatform2SizeX[i]*(1/difficultyLevelMultiplicator));
//            }
//            else{
//                modifiedLandingPlatform1SizeX[i]=landingPlatform1SizeX[i];
//                modifiedLandingPlatform2SizeX[i]=landingPlatform2SizeX[i];
//            }
//        }
    }
}