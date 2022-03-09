import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

/***
 * Klasa udostępniająca metody do laczenie się oraz pobierania danych z plikow konfiguracyjnych gry
 */

    public class LocalFileComunicationCenter {
        /***config przechowuje tymczasowo dane zczytane z pliku*/
        private static Properties config;
        /***file strumień do pliku*/
        private static InputStream fileIn;
        private static FileOutputStream fileOut;

        /***
         * Metoda realizująca laczenie z plikiami
         * @param fileName nazwa pliku konfiguracyjnego
         */
        static void initLoadingSesion(String fileName) throws IOException{
                    fileIn = new FileInputStream(fileName);
                    config = new Properties();
                    config.load(fileIn);
            }

        /***
         * Metoda parsująca i przekazująca dane do obiektu typu Lander
         * @param lander obiekt typu Lander
         */
        static void loadLocalLunarConfig(Lander lander){
                try {
                    initLoadingSesion("Game/configFiles/lunarConfig.properties");
                    lander.setLives(Integer.parseInt(config.getProperty("lives")));
                    lander.setMaxLives(Integer.parseInt(config.getProperty("maxLives")));
                    lander.setFuel(Integer.parseInt(config.getProperty("fuel")));
                    lander.setMaxFuel(Integer.parseInt(config.getProperty("maxFuel")));
                    lander.setLanderHitBoxXY(Arrays.stream(config.getProperty("landerHitBox").split(" ")).mapToInt(Integer::parseInt).toArray());
                    lander.setStartVelocityXY(Arrays.stream(config.getProperty("startVelocityXY").split(" ")).mapToDouble(Double::parseDouble).toArray());
                    lander.setProperVelocityXY(Arrays.stream(config.getProperty("properVelocityXY").split(" ")).mapToDouble(Double::parseDouble).toArray());
                    lander.setMaxVelocityXY(Arrays.stream(config.getProperty("maxVelocityXY").split(" ")).mapToDouble(Double::parseDouble).toArray());
                    lander.setStartPositionXY(Arrays.stream(config.getProperty("startPositionXY").split(" ")).mapToDouble(Double::parseDouble).toArray());
                    lander.setEnginePower(Double.parseDouble(config.getProperty("enginePower"))/10000);
                    fileIn.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

    /***
     * Metoda parsujaca i przekazujaca dane do obiektu typu Window
     * @param window obiekt typu window
     * @param ID ID obiektu typu Window jako String wykorzystywany do wczytania odpowiedniego parametru
     */
        static void loadLocalWindowConfig(Window window, String ID){
            try {
                initLoadingSesion("Game/configFiles/windowConfig.properties");
                window.setWindowName(config.getProperty(ID+"WindowName"));
                window.setWindowSizeXY(Arrays.stream(config.getProperty(ID+"WindowSizesXY").split(" ")).mapToInt(Integer::parseInt).toArray());
                fileIn.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        /***
         * Metoda parsujaca i przekazujaca dane do obiektu typu Map
         * @param map obiekt typu Map
         * @param mapNumber numer mapy
         */
        static void loadLocalMapConfig(Map map, int mapNumber) throws IOException{
                initLoadingSesion("Game/configFiles/map" + mapNumber + "Config.properties");
                map.setGravity(Double.parseDouble(config.getProperty("gravity"))/5000);
                map.setLandingPlatform1Position(Arrays.stream(config.getProperty("landingPlatform1Position").split(" ")).mapToInt(Integer::parseInt).toArray());
                map.setLandingPlatform1Size(Arrays.stream(config.getProperty("landingPlatform1Size").split(" ")).mapToInt(Integer::parseInt).toArray());
                map.setLandingPlatform2Position(Arrays.stream(config.getProperty("landingPlatform2Position").split(" ")).mapToInt(Integer::parseInt).toArray());
                map.setLandingPlatform2Size(Arrays.stream(config.getProperty("landingPlatform2Size").split(" ")).mapToInt(Integer::parseInt).toArray());
                map.setMapParametersX(Arrays.stream(config.getProperty("mapParametersX").split(" ")).mapToInt(Integer::parseInt).toArray());
                map.setMapParametersY(Arrays.stream(config.getProperty("mapParametersY").split(" ")).mapToInt(Integer::parseInt).toArray());
                map.setPlanetColorArray(Arrays.stream(config.getProperty("planetColor").split(" ")).mapToInt(Integer::parseInt).toArray());
                map.setPlatform1ColorArray(Arrays.stream(config.getProperty("platform1Color").split(" ")).mapToInt(Integer::parseInt).toArray());
                map.setPlatform2ColorArray(Arrays.stream(config.getProperty("platform2Color").split(" ")).mapToInt(Integer::parseInt).toArray());
                map.setPlatformesScore(Arrays.stream(config.getProperty("platformesScore").split(" ")).mapToInt(Integer::parseInt).toArray());
                fileIn.close();
            }

    /***
     * metoda odpowiedzialna za wczytanie najlepszych wyników z lokalnych plików
     * @param leaderBoard
     */
        static void loadLocalLeaderBoard(LeaderBoard leaderBoard){
            try{
                initLoadingSesion("Game/configFiles/leaderBoard.properties");
                leaderBoard.setLeaderBoard(config);
                fileIn.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

    /**
     * metoda służąca do zapisu rekordów do pliku lokalnego
     * @param leaderBoard
     */
        static void saveLocalLeaderBoard(Properties leaderBoard){
            try {
                fileOut = new FileOutputStream("Game/configFiles/leaderBoard.properties");
                leaderBoard.store(fileOut, "komentarz");
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
