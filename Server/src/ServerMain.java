import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    /**
     * socket serwera
     */
    protected static ServerSocket serverSocket;

    /**
     * inicjalizowanie serwera i nawiązywanie łączności z klientem
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ServerInicializer serverInicializer = new ServerInicializer();
        (new Thread(new ConsoleInputListener())).start();

        while(true) {
            Socket socket = serverSocket.accept();
            (new Thread(new Connect(socket))).start();
        }
    }
}