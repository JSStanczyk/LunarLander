import java.util.*;

/***
 * obiekt odpowiedzialny za przechowywanie i zarządzanie listą najlepszych wyników
 */
public class LeaderBoard {
    private  Properties leaderBoard;
    private  ArrayList<Player> list;

    /**
     * konstruktor
     */
    public LeaderBoard(){
        list = new ArrayList<>();
        loadList();
    }

    /**
     * metoda wykorzystywana do wczytania listy najlepszych wyników i ich posortowanie
     */
     public void loadList(){
        if(MenuWindow.serverIsOn){
            ServerFileComunicationCenter.loadServerLeaderBoard(this);
        }
        else{
            LocalFileComunicationCenter.loadLocalLeaderBoard(this);
        }
        list.clear();
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
     * @param p
     */
    public void saveScore(Player p){
        if(MenuWindow.serverIsOn){
            for (int i = 1; i <= 10; i++) {
                leaderBoard.setProperty("place" + i, list.get(i - 1).getNickname() + " " + list.get(i - 1).getScore());
            }
            ServerFileComunicationCenter.saveServerLeaderBoard(p);
        }
        else {
            for (int i = 1; i <= 10; i++) {
                leaderBoard.setProperty("place" + i, list.get(i - 1).getNickname() + " " + list.get(i - 1).getScore());
            }
            LocalFileComunicationCenter.saveLocalLeaderBoard(leaderBoard);
        }
    }
//    public void saveScore(){
//        if(MenuWindow.serverIsOn){
//            for (int i = 1; i <= 10; i++) {
//                leaderBoard.setProperty("place" + i, list.get(i - 1).getNickname() + " " + list.get(i - 1).getScore());
//            }
//            ServerFileComunicationCenter.saveServerLeaderBoard(leaderBoard);
//        }
//        else {
//            for (int i = 1; i <= 10; i++) {
//                leaderBoard.setProperty("place" + i, list.get(i - 1).getNickname() + " " + list.get(i - 1).getScore());
//            }
//            LocalFileComunicationCenter.saveLocalLeaderBoard(leaderBoard);
//        }
//    }

    /**
     * metoda aktualizująca listę najlepszych wyników
     * @param player
     */
    public void updateScore(Player player){
         addScore(player);
         saveScore(player);
    }

    /***
     * metoda zwracające wynik gracza o konkretnej pozycji w rankingu
     * @param placeNumber-pozycja w rankingu
     * @return
     */
    public int getScore(int placeNumber) {
        return list.get(placeNumber).getScore();
    }

    /**
     * metoda zwracająca nazwę gracza zajmującego konkretne miejsce w rankingu
     * @param placeNumber-miejsce w rankingu
     * @return
     */
    public String getPlayerName(int placeNumber){
        return list.get(placeNumber).getNickname();
    }

    /**
     * seter pola leaderBoard
     * @param leaderBoard
     */
    public void setLeaderBoard(Properties leaderBoard) {
        //this.leaderBoard = new Properties();
        this.leaderBoard = leaderBoard;
    }
}

