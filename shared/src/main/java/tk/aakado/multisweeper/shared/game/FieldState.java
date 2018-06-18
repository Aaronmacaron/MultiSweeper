package tk.aakado.multisweeper.shared.game;

import java.util.stream.Stream;

/**
 * All possible States of a field
 *
 * @author Dominik Strässle
 */
public enum FieldState {
    DISCOVERED, MINE, FLAG, FALSE_FLAGGED_MINE, UNDISCOVERED, MINE_EXPLODED;

    // TODO: only for testing
    public static FieldState getByValue(int value) {
        if (value < 0 || value > 5) {
            throw new IllegalArgumentException("The given value is invalid.");
        }
        return Stream.of(values())
                .filter(fieldType -> fieldType.ordinal() == value)
                .findFirst()
                .get();
    }
    }
