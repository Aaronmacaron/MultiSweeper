package tk.aakado.multisweeper.shared.connection.dtos;

import tk.aakado.multisweeper.shared.game.FieldState;

import java.util.Optional;

/**
 * This data transfer object is used to transfer information about a field.
 */
public class FieldDTO {

    private final int x;
    private final int y;
    private final FieldState state;
    private final Optional<Integer> value;

    public FieldDTO(int x, int y, FieldState state) {
        this(x, y, state, Optional.empty());
    }

    public FieldDTO(int x, int y, FieldState state, int value) {
        this(x, y, state, Optional.of(value));
    }

    private FieldDTO(int x, int y, FieldState state, Optional<Integer> value) {
        this.x = x;
        this.y = y;
        this.state = state;
        this.value = value;
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

    public Optional<Integer> getValue() {
        return value;
    }
}
