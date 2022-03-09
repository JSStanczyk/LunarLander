import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * klasa wykorzystywana w GameBoardWindow
 * wykrywa wcisniecie i puszczenie przyciskow klawiatury
 */
public class KeyboardChecker extends KeyAdapter {
    private GameBoardEngine gameBoardEngine;

    /**
     * konstruktor
     * @param gameBoardEngine obiekt typu GameBoardEngine
     */
    public KeyboardChecker(GameBoardEngine gameBoardEngine){
        this.gameBoardEngine = gameBoardEngine;
    }

    /**
     * metoda wywolujaca metode keyPressed zdefiniowanej w klasie GameBoardEngine
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e){
        gameBoardEngine.keyPressed(e);
    }

    /**
     * metoda wywolujaca metode keyReleased zdefiniowanej w klasie GameBoardEngine
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e){
        gameBoardEngine.keyReleased(e);
    }
}
