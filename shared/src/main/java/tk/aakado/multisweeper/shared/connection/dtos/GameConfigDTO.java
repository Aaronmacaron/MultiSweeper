package tk.aakado.multisweeper.shared.connection.dtos;

/**
 * DTO for GameConfig. This is used when starting a new game to hold the game config.
 */
public class GameConfigDTO {

    private final int width;
    private final int height;
    private final int minesPercentage;

    /**
     * Constructor
     * @param width The width of the playingField
     * @param height The width of the playingField
     * @param minesPercentage The percentage of field that are mines in this game
     */
    public GameConfigDTO(int width, int height, int minesPercentage) {
        this.width = width;
        this.height = height;
        this.minesPercentage = minesPercentage;
    }

    /**
     * Getter for width
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Getter for height
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Getter for minesPercentage
     * @return minesPercentage
     */
    public int getMinesPercentage() {
        return minesPercentage;
    }

}
