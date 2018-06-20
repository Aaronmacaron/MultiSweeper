package tk.aakado.multisweeper.shared.game;

import java.util.stream.Stream;

/**
 * All possible States of a field
 *
 * @author Dominik Strässle
 */
public enum FieldState {
    DISCOVERED, MINE, FLAG, FALSE_FLAGGED_MINE, UNDISCOVERED, MINE_EXPLODED
}
