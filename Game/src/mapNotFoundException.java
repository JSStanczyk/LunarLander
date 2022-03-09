/***
 * wyjątek wykożystywany przy wczytywaniu mapy z serwera
 */
public class mapNotFoundException extends Throwable{
    mapNotFoundException(String message){
        super(message);
    }
}
