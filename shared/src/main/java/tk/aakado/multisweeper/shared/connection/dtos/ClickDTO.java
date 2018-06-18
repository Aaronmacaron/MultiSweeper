package tk.aakado.multisweeper.shared.connection.dtos;

/**
 * DTO for click actions
 */
public class ClickDTO {

    private int x;
    private int y;
    private String clickType;

    /**
     * Constructor
     * @param x X coordinate in playing field
     * @param y Y coordinate in playing field
     * @param clickType The click type (either "left" or "right")
     */
    public ClickDTO(int x, int y, String clickType) {
        this.x = x;
        this.y = y;
        this.clickType = clickType;
    }

    /**
     * Getter for x
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for y
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Getter for clickType
     * @return clickType
     */
    public String getClickType() {
        return clickType;
    }

}
