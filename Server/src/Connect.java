import java.io.*;
import java.net.Socket;
import java.util.Properties;

/**
 * klasa odpowiedzialna za obsluge zapytan przychodzacych do serwera
 */
public class Connect extends Thread {
    /**
     * zmienna przechowująca socket
     */
    private Socket socket;
    /**
     * zmienne odpowierdzialne na przechowywanie stumieni danych
     */
    private InputStreamReader textInput;
    private PrintWriter textOutput;
    private ObjectOutputStream objectOutput;
    private ObjectInputStream objectInput;
    private BufferedReader bufferedTextInput;
    private FileReader fileIn;
    private FileOutputStream fileOut;
    /**
     * zmienna przwująca comende
     */
    private String command;
    /**
     * zmienna przechowujaca wiadomosc ktora zostala odebrana przez serwer
     */
    private String[] incomingMessage;
    /**
     * numer mapy
     */
    private int mapNumber;
    /**
     * zmienna przechowujaca dane odczytane z pliku lub otrzymane od klienta
     */
    private Properties config, leaderBoard;
    /**
     * gracz
     */
    private Player player;
    /**
     * klasa odpowiedzialna za przegladanie wejsciowej tablicy wynikow i wybiaranie tylko tych nadajaceych sie do wpisania
     */
    private ServerLeaderBoard serverLeaderBoard;
    /**
     * zmienna przchowująca status
     */
    static boolean busy;

    /**
     * konstruktor
     * @param socket
     */
    public Connect(Socket socket) {
        this.socket = socket;
        serverLeaderBoard=new ServerLeaderBoard();
    }

    /**
     * nadpisana metoda run sluzaca do obslugi logiki serwera
     */
    @Override
    public void run() {
        try {
                busy = true;
                textInput = new InputStreamReader(socket.getInputStream());
                bufferedTextInput = new BufferedReader(textInput);
                incomingMessage = (bufferedTextInput.readLine()).split(" ");
                command = incomingMessage[0];
                if(incomingMessage.length == 2){
                    mapNumber = Integer.parseInt(incomingMessage[1]);
                }

                switch(command) {
                    case "getMapConfig":
                        try {
                            loadProperties("Server/configFiles/map" + mapNumber + "Config.properties");
                            sendMessage("mapFound\n");
                            textInput = new InputStreamReader(socket.getInputStream());
                            bufferedTextInput = new BufferedReader(textInput);
                            if(bufferedTextInput.readLine().equals("Ready")){
                                sendObject();
                            }
                        }
                        catch (Exception e){
                            sendMessage("mapNotFound\n");
                        }
                        break;

                    case "getLunarConfig":
                        loadProperties("Server/configFiles/lunarConfig.properties");
                        sendObject();
                        break;

                    case "getWindowConfig":
                        loadProperties("Server/configFiles/windowConfig.properties");
                        sendObject();
                        break;

                    case "getLeaderBoard":
                        loadProperties("Server/configFiles/leaderBoard.properties");
                        sendObject();
                        break;

                    case "setLeaderBoard":
                        //saveProperties("Server/configFiles/leaderBoard.properties");
                        updateBoard();
                        break;

                    default:
                        break;
                }
                //socket.close();
            busy = false;
        } catch (IOException e) {
            System.out.println("Connection lost");
        }
        catch (NullPointerException ex){}
    }

    /**
     * metoda ladujaca pliki
     * @param fileName
     * @throws IOException
     */
    private void loadProperties(String fileName) throws IOException {
        fileIn = new FileReader(fileName);
        config = new Properties();
        config.load(fileIn);
        fileIn.close();
    }

    /**
     * inicjalizujaca proces aktualizacji serwerowego leader boarda
     */
    private void updateBoard(){
        try {
                sendMessage("ACK");
                objectInput = new ObjectInputStream(socket.getInputStream());
                player = (Player)objectInput.readObject();
            }catch (IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        serverLeaderBoard.loadList();
        serverLeaderBoard.addScore(player);
        serverLeaderBoard.saveScore();
    }

    private void saveProperties(String filename) throws IOException{
        try{
            fileOut = new FileOutputStream(filename);
            sendMessage("ACK");
            objectInput = new ObjectInputStream(socket.getInputStream());
            leaderBoard = (Properties)objectInput.readObject();
            leaderBoard.store(fileOut, null);
            fileOut.close();
        }
        catch (IOException e){
            sendMessage("failure");
        }
        catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    /**
     * metoda wysylajaca leader boarda
     * @throws IOException
     */
    private void sendObject() throws IOException{
        objectOutput = new ObjectOutputStream(socket.getOutputStream());
        objectOutput.writeObject(config);
        objectOutput.flush();
    }

    /**
     * metoda wysylajaca wiadomosc tekstowa
     * @param message
     * @throws IOException
     */
    private  void sendMessage(String message) throws IOException{
        textOutput = new PrintWriter(socket.getOutputStream());
        textOutput.println(message);
        textOutput.flush();
    }
}
