package tk.aakado.multisweeper.shared.connection.dtos;

import java.util.List;

/**
 * POJO for start action params. This is used to store information when starting a new game about the game.
 */
public class StartInfoDTO {

    private final int fieldWidth;
    private final int fieldHeight;
    private final int numberOfMines;
    private final List<FieldDTO> initialFieldState;

    /**
     * Constructor
     * @param fieldWidth fieldWidth of game
     * @param fieldHeight fieldHeight of game
     * @param initialFieldState The initial fields of the game
     */
    public StartInfoDTO(int fieldWidth, int fieldHeight, int numberOfMines, List<FieldDTO> initialFieldState) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        this.numberOfMines = numberOfMines;
        this.initialFieldState = initialFieldState;
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

    /**
     * Getter for numberOfMines
     * @return numberOfMines
     */
    public int getNumberOfMines() {
        return numberOfMines;
    }

    /**
     * Getter for initialFieldState
     * @return initialFieldState
     */
    public List<FieldDTO> getInitialFieldState() {
        return initialFieldState;
    }

}
