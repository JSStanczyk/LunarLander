import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;

/**
 * rozpoczynająca działanie serwera
 */
public class ServerInicializer {
    /**
     * dane wejściowe z konsoli
     */
    private String inputMessage;
    /**
     * strumień wejściowy
     */
    private InputStreamReader input;
    /**
     * pole służące do odczytywania danych z konsoli
     */
    private BufferedReader bufferedReader;
    /**
     * port serwera
     */
    private int port;

    /**
     * konstruktor przypisujące numer portu serwerowi
     * @throws IOException
     */
    ServerInicializer() throws IOException{
        while(true){
            System.out.println("Enter port number or type -> default <- to use default port number");
            input = new InputStreamReader(System.in);
            bufferedReader = new BufferedReader(input);
            inputMessage = bufferedReader.readLine();

            switch (inputMessage){
                case "default":
                    port = 2800;
                    break;

                default:
                    try{
                        port = Integer.parseInt(inputMessage);
                    }
                    catch (NumberFormatException e){
                        System.out.println("Wrong command");
                    }
                    continue;
                    //break;
            }

            try{
                ServerMain.serverSocket = new ServerSocket(port);
                break;
            }
            catch (IOException e){
                System.out.println("This port number is already in use.");
            }
            catch (SecurityException s){
                System.out.println("This port number is already in use.");
            }
            catch (IllegalArgumentException i){
                System.out.println("Entered port number is out of range.\nAvailable range is 0 - 65535\n");
            }
        }
    }

}
