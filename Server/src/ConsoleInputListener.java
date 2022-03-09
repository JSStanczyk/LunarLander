import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * klasa odpowiedzialna za obsluge wejscia konsoli podczas dzialania serwera
 */
public class ConsoleInputListener extends Thread{
    private BufferedReader bufferedReader;
    private InputStreamReader input;
    private String command;

    /**
     * konstuktor
     */
    ConsoleInputListener(){
        System.out.println("\nCommands:\nclose - closes server\n");
    }

    /**
     * nadpisana metoda run dopoweidzialna za interpretacje wejscia konsoli
     */
    @Override
    public void run(){
        try {
            while (true) {
                input = new InputStreamReader(System.in);
                bufferedReader = new BufferedReader(input);
                command = bufferedReader.readLine();

                switch (command) {
                    case "close":
                        while (true){
                            if(!Connect.busy){
                                System.out.println("\nServer closed");
                                System.exit(0);
                            }
                        }
                    default:
                        System.out.println("unknown command");
                        break;
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
