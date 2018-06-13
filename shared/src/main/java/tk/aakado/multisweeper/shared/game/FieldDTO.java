package tk.aakado.multisweeper.shared.game;

/**
 * This data transfer object is used to transfer information about a field.
 */
public class FieldDTO {

    private final int x;
    private final int y;
    private final FieldState state;

    public FieldDTO(int x, int y, FieldState state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public FieldState getState() {
        return state;
    }
}
