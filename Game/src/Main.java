import java.awt.*;
import java.io.IOException;

/***
 * Gówna klasa programu
 */

public class Main {
    /***
     * obiekt klasy MenuWindow
     */
    public static MenuWindow Menu;
    private static ChooseOfflineOnlineModeWindow chooseOfflineOnlineMode;
    /***
     * metoda startująca program
     * @param args
     */
    static public void main(String[] args){
        chooseOfflineOnlineMode = new ChooseOfflineOnlineModeWindow();
        chooseOfflineOnlineMode.background.startScaling();
    }

    /***
     * metoda służąca do włączenia gry
     */
    static public void startGame(){
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run() {
                Menu = new MenuWindow(chooseOfflineOnlineMode);
            }
        });
    }
}