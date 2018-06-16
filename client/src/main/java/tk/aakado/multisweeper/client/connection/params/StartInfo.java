package tk.aakado.multisweeper.client.connection.params;

/**
 * POJO for start action params. This is used to store information when starting a new game about the game.
 * @see tk.aakado.multisweeper.client.connection.Transmitter#start
 */
public class StartInfo {

    private double mineDensity;
    private int fieldWidth;
    private int fieldHeight;

    /**
     * Constructor
     * @param mineDensity mineDensity of game
     * @param fieldWidth fieldWidth of game
     * @param fieldHeight fieldHeight of game
     */
    public StartInfo(double mineDensity, int fieldWidth, int fieldHeight) {
        this.mineDensity = mineDensity;
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
    }

    /**
     * Getter for mineDensity
     * @return mineDensity
     */
    public double getMineDensity() {
        return mineDensity;
    }

    /**
     * Getter for fieldWidth
     * @return fieldWidth
     */
    public int getFieldWidth() {
        return fieldWidth;
    }

    /**
     * Getter for FieldHeight
     * @return fieldHeight
     */
    public int getFieldHeight() {
        return fieldHeight;
    }

}
