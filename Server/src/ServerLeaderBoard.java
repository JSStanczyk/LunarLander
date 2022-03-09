import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

/***
 * metoda odpowiedzialna za zarządzanie rekordami
 */
public class ServerLeaderBoard {
    /**
     * pole wykorzystywane do zapisu i odczytu z pliku
     */
    private Properties leaderBoard;
    /***
     * ranking najlepszych graczy
     */
    private ArrayList<Player> list;

    /**
     * konstruktor
     */
    public ServerLeaderBoard(){
        list = new ArrayList<>();
        loadList();
    }
    /**
     * metoda wykorzystywana do wczytania listy najlepszych wyników i ich posortowanie
     */
    public void loadList(){
        list.clear();
        try {
           FileInputStream fileIn = new FileInputStream( "Server/configFiles/leaderBoard.properties");
            leaderBoard = new Properties();
            leaderBoard.load(fileIn);
            fileIn.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        for(int i=1; i<=10; i++){
            if(leaderBoard.containsKey("place"+i)) {
                String[] tempPlayer = leaderBoard.getProperty("place" + i, "brak wyniku").split(" ");
                list.add(new Player(tempPlayer[0]));
                list.get(list.size() - 1).addToScore(Integer.parseInt(tempPlayer[1]));
//                System.out.println(list.get(list.size()-1).getNickname());
//                System.out.println(list.get(list.size()-1).getScore());
            }
            else{
                list.add(new Player());
            }
        }
        //sortowanie listy po wyniku
        Collections.sort(list);
    }
    /***
     * metoda służąca do dodawania wyniku gracza do listy(jeśli jest wystarczająco wysoki)
     * @param p
     */
    public void addScore(Player p){
        int i=0;
        for(Player sc : list){
            if(p.getScore()> sc.getScore()){
                list.add(i,p);
                list.remove(list.size()-1);
                break;
            }
            i++;
        }
        }
    /***
     * metoda odpowiedzialna za zapis rekorów do pliku
     */
    public void saveScore(){
        for (int i = 1; i <= 10; i++) {
            leaderBoard.setProperty("place" + i, list.get(i - 1).getNickname() + " " + list.get(i - 1).getScore());
        }
        try {
            FileOutputStream fileOut = new FileOutputStream("Server/configFiles/leaderBoard.properties");
            leaderBoard.store(fileOut, "komentarz");
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
