import javax.swing.*;

/***
 * Klasa anstrakcyjna
 * Dziedziczą po niej prawie wszystkie okna
 */

public abstract class Window extends JFrame {
    /***
     * setter wymarów okienka
     * @param windowSizeXY tablica wymiary okienka
     */
    abstract public void setWindowSizeXY(int[] windowSizeXY);

    /***
     * setter nazwy okienka
     * @param windowName nazwa okna
     */
    abstract public void setWindowName(String windowName);

    /***
     * getter szerokości okienka
     * @return szerokość okienka
     */
    abstract public int getWindowSizeX();

    /***
     * getter wysokości okienka
     * @return wysokość okienka
     */
    abstract public int getWindowSizeY();
}


