import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Properties;

/***
 * Klasa odpowiedzialna za pobieranie i parsowanie danych z serwera
 */
public class ServerFileComunicationCenter {
    /***
     * config, leaderBoard-obiekty służące do pobierania danych z plików konfiguracyjnych
     * socket-socket
     * commandSender-odpowiedzialny za wysyłanie wiadomości do serwera
     * input-strumień wejściowy
     * output-strumień wyjściowy
     * textInput- odpowiedzialny za odbieranie wiadomości od serwera
     * portNumber- numer portu serwera
     * IP-IP serwera
     */
    private static Properties config, leaderBoard;
    private static Socket socket;
    private static PrintWriter commandSender;
    private static ObjectInputStream input;
    private static ObjectOutputStream output;
    private static InputStreamReader textInput;
    private static BufferedReader bufferedTextInput;
    private static Throwable mapNotFound;
    private static int portNumber = 2800;
    private static String IP = "localhost";

    /***
     * metoda odpowiedzialna za nawiązanie łączności z serwerem
     * @param command
     * @throws IOException
     * @throws ClassNotFoundException
     */
    static void initServerLoadingSesion(String command) throws IOException, ClassNotFoundException{
        socket = new Socket(IP, portNumber);
        commandSender = new PrintWriter(socket.getOutputStream());
        commandSender.println(command);
        commandSender.flush();
//        input = new ObjectInputStream(socket.getInputStream());
//        config = (Properties)input.readObject();
    }

    /***
     * metoda odpowiedzialna za wczytywanie i parsowanie pliku konfiguracyjnego obiektu Lander
     * @param lander-lądownik
     */
    static void loadServerLunarConfig(Lander lander){
        try {
            initServerLoadingSesion("getLunarConfig\n");
            input = new ObjectInputStream(socket.getInputStream());
            config = (Properties)input.readObject();
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
        }
        catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /***
     * metoda odpowiedzialna za wczytywanie i parsowanie pliku konfiguracyjnego okna
     * @param window
     * @param ID
     */
    static void loadServerWindowConfig(Window window, String ID){
        try {
            initServerLoadingSesion("getWindowConfig\n");
            input = new ObjectInputStream(socket.getInputStream());
            config = (Properties)input.readObject();
            window.setWindowName(config.getProperty(ID+"WindowName"));
            window.setWindowSizeXY(Arrays.stream(config.getProperty(ID+"WindowSizesXY").split(" ")).mapToInt(Integer::parseInt).toArray());
        }
        catch (Exception e) {
            //e.printStackTrace();
        }
    }

    /***
     * metoda odpowiedzialna za wczytywanie i parsowanie pliku konfiguracyjnego mapy
     * @param map
     * @param mapNumber
     * @throws mapNotFoundException
     */
    static void loadServerMapConfig(Map map, int mapNumber) throws mapNotFoundException{
        try {
            initServerLoadingSesion("getMapConfig" + " " + mapNumber + "\n");
            textInput = new InputStreamReader(socket.getInputStream());
            bufferedTextInput = new BufferedReader(textInput);
            String answer = bufferedTextInput.readLine();
            if(answer.equals("mapFound")){
                commandSender = new PrintWriter(socket.getOutputStream());
                commandSender.println("Ready\n");
                commandSender.flush();
                input = new ObjectInputStream(socket.getInputStream());
                config = (Properties)input.readObject();
                map.setGravity(Double.parseDouble(config.getProperty("gravity")) / 5000);
                map.setLandingPlatform1Size(Arrays.stream(config.getProperty("landingPlatform1Size").split(" ")).mapToInt(Integer::parseInt).toArray());
                map.setLandingPlatform1Position(Arrays.stream(config.getProperty("landingPlatform1Position").split(" ")).mapToInt(Integer::parseInt).toArray());
                map.setLandingPlatform2Size(Arrays.stream(config.getProperty("landingPlatform2Size").split(" ")).mapToInt(Integer::parseInt).toArray());
                map.setLandingPlatform2Position(Arrays.stream(config.getProperty("landingPlatform2Position").split(" ")).mapToInt(Integer::parseInt).toArray());
                map.setMapParametersX(Arrays.stream(config.getProperty("mapParametersX").split(" ")).mapToInt(Integer::parseInt).toArray());
                map.setMapParametersY(Arrays.stream(config.getProperty("mapParametersY").split(" ")).mapToInt(Integer::parseInt).toArray());
                map.setPlanetColorArray(Arrays.stream(config.getProperty("planetColor").split(" ")).mapToInt(Integer::parseInt).toArray());
                map.setPlatform1ColorArray(Arrays.stream(config.getProperty("platform1Color").split(" ")).mapToInt(Integer::parseInt).toArray());
                map.setPlatform2ColorArray(Arrays.stream(config.getProperty("platform2Color").split(" ")).mapToInt(Integer::parseInt).toArray());
                map.setPlatformesScore(Arrays.stream(config.getProperty("platformesScore").split(" ")).mapToInt(Integer::parseInt).toArray());
            }
            else if(answer.equals("mapNotFound")){
                throw new mapNotFoundException("MapConfigFileNotFound");
            }
        }
        catch (IOException e){}
        catch (ClassNotFoundException c){}
    }

    /***
     * metoda odpowiedzialna za pobieranie listy najlepszych wyników z serwera
     * @param leaderBoard
     */
    static void loadServerLeaderBoard(LeaderBoard leaderBoard){
        try{
            initServerLoadingSesion("getLeaderBoard\n");
            input = new ObjectInputStream(socket.getInputStream());
            //config = (Properties)input.readObject();
            leaderBoard.setLeaderBoard((Properties)input.readObject());
        }
        catch (Exception e){
            //e.printStackTrace();
        }
    }

    /***
     * metoda wysyłająca obiekt gracz do serwera
     * @param p
     */
    static void saveServerLeaderBoard(Player p){
        try {
            while (true){
                initServerLoadingSesion("setLeaderBoard\n");
                textInput = new InputStreamReader(socket.getInputStream());
                bufferedTextInput = new BufferedReader(textInput);
                if(bufferedTextInput.readLine().equals("ACK")){
                    output = new ObjectOutputStream(socket.getOutputStream());
                    output.writeObject(p);
                    output.flush();
                    break;
                }
            }
        }
        catch (Exception e){}
    }

    /***
     * metoda sprawdzająca połączenie z serwerem
     * @return
     */
    static boolean checkIfServerIsOnline(){
        try{
            initServerLoadingSesion("OnlineQuestion");
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /***
     * seter IP
     * @param IP-IP serwera
     */
    public static void setIP(String IP) {
        ServerFileComunicationCenter.IP = IP;
    }

    /***
     * seter numeru portu
     * @param portNumber-numer portu
     */
    public static void setPortNumber(int portNumber) {
        ServerFileComunicationCenter.portNumber = portNumber;
    }
}
