package tk.aakado.multisweeper.shared.connection.dtos;

/**
 * POJO for start action params. This is used to store information when starting a new game about the game.
 */
public class StartInfoDTO {

    private double mineDensity;
    private int fieldWidth;
    private int fieldHeight;

    /**
     * Constructor
     * @param mineDensity mineDensity of game
     * @param fieldWidth fieldWidth of game
     * @param fieldHeight fieldHeight of game
     */
    public StartInfoDTO(double mineDensity, int fieldWidth, int fieldHeight) {
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
