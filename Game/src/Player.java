import java.io.Serializable;

/**
 * klasa przechowujaca informacje o graczu
 */
public class Player implements Comparable<Player>, Serializable {
    /**
     * nazwa gracza
     */

    private String nickname = "brak";
    /**
     * wynik gracza
     */
    private int score = 0;

    /**
     * konstruktor bezparametrowy
     */
    Player(){}

    /**
     * konstruktor
     * @param nickname nazwa gracza
     */
    Player(String nickname){
        this.nickname = nickname;
        //score = 0;
    }

    /**
     * modyfikacja wyniku gracza
     * @param scoreComponent punkty dodane do aktualnego wyniku
     */
    public void addToScore(int scoreComponent){
        score += scoreComponent;
    }

    /**
     * Getter wyniku gracza
     * @return zwraca wynik
     */
    public int getScore(){
        return score;
    }

    /**
     * Getter nazwy gracza
     * @return zwraca nazwe gracza
     */
    public String getNickname(){
        return nickname;
    }

//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }

    /**
     * metoda ustawiajaca parametry domyslne graczowi
     */
    public void restoreDefaults(){
        nickname = "brak";
        score = 0;
    }
    // overriding the compareTo method of Comparable class
    /***
     * przeciążona metoda pozwalająca na porównanie obiektów typu Gracz
     * @param comparestu
     * @return
     */
    @Override public int compareTo(Player comparestu)
    {
        double compareage = ((Player)comparestu).getScore();

         return (int)(compareage-this.score);
    }
}
