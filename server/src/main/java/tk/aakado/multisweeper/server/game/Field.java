package tk.aakado.multisweeper.server.game;

import tk.aakado.multisweeper.shared.connection.dtos.FieldDTO;
import tk.aakado.multisweeper.shared.game.FieldState;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * A field of the minesweeper game.
 */
public class Field {

    private final FieldCords fieldCords;
    private final FieldType type;

    private final FieldState initialState;
    private FieldState state;

    private Player discoverPlayer;

    private Player flagPlayer;

    /**
     * Constructor.
     * @param fieldCords The coordinates of this field.
     * @param type The type of this field.
     */
    public Field(FieldCords fieldCords, FieldType type) {
        this.fieldCords = fieldCords;
        this.type = type;

        if (isMine()) {
            this.initialState = FieldState.MINE;
        } else {
            this.initialState = FieldState.UNDISCOVERED;
        }

        this.state = initialState;
    }

    /**
     * Discovers this field.
     * @param player The player which discovers this field.
     */
    public void discover(Player player) {
        if (this.isDiscovered() || this.isFlagged()) {
            throw new IllegalStateException("This field has already been discovered.");
        }

        if (isMine()) {
            this.state = FieldState.MINE;
        } else {
            this.state = FieldState.DISCOVERED;
        }
        this.discoverPlayer = player;
    }

    /**
     * Flags this field. This means that it is potentially a mined field.
     * @param player The player that flags the field.
     */
    public void flag(Player player) {
        if (this.isFlagged() || this.isDiscovered()) {
            throw new IllegalStateException("The field has already been flagged.");
        }

        if (isMine()) {
            this.state = FieldState.FLAG;
        } else {
            this.state = FieldState.FALSE_FLAGGED_MINE;
        }

        this.flagPlayer = player;
    }

    /**
     * Unflaggs this field.
     */
    public void unflag() {
        if (! this.isFlagged() || this.isDiscovered()) {
            throw new IllegalStateException("The field is not flagged and thus cannot be unflagged.");
        }

        this.state = this.initialState;
        this.flagPlayer = null;
    }

    /**
     * Get the corresponding {@link FieldDTO} of this field.
     * @return This field as {@link FieldDTO}
     */
    public FieldDTO toFieldDTO(boolean exposed) {
        int x = this.fieldCords.getX();
        int y = this.fieldCords.getY();
        FieldState displayState = this.state;
        if (this.isDiscovered()) {
            return new FieldDTO(x, y, this.state, this.type.getValue());
        } else if (this.isFlagged()) {
            if (exposed) {
                displayState = this.state;
            } else {
                displayState = FieldState.FLAG;
            }
        } else if (isMine()) {
            if (exposed) {
                displayState = FieldState.MINE;
            } else {
                displayState = FieldState.UNDISCOVERED;
            }
        }
        return new FieldDTO(this.fieldCords.getX(), this.fieldCords.getY(), displayState);
    }

    // Getters

    public boolean isMine() {
        return type == FieldType.MINE;
    }

    public FieldCords getFieldCords() {
        return this.fieldCords;
    }

    /**
     * Returns the value / number of mines nearby of this field.
     * @return Value / number of mines.
     */
    public int getFieldValue() {
        return this.type.getValue();
    }

    public boolean isDiscovered() {
        return this.state == FieldState.DISCOVERED;
    }

    public Optional<Player> getDiscoverPlayer() {
        return Optional.ofNullable(this.discoverPlayer);
    }

    public boolean isFlagged() {
        return this.state == FieldState.FLAG || this.state == FieldState.FALSE_FLAGGED_MINE;
    }

    public Optional<Player> getFlagPlayer() {
        return Optional.ofNullable(this.flagPlayer);
    }

    /**
     * Determines the type of a {@link Field}.
     * The field can either be a mine or a normal field. The normal fields have a value which
     * tells how many mines are surrounding them.
     */
    public enum FieldType {
        FIELD_0, FIELD_1, FIELD_2, FIELD_3, FIELD_4, FIELD_5,  FIELD_6, FIELD_7, FIELD_8, MINE;

        /**
         * Returns the value of this {@link FieldType}.
         * @return The value.
         */
        public int getValue() {
            if (this == MINE) {
                throw new IllegalStateException("A mine does not have a value.");
            }
            return this.ordinal();
        }

        /**
         * Get the field type by value.
         * @param value The value.
         * @return The field type corresponding the given value.
         */
        public static FieldType getByValue(int value) {
            if (value < 0 || value > 8) {
                throw new IllegalArgumentException("The given value is invalid.");
            }
            return Stream.of(values())
                    .filter(fieldType -> fieldType.ordinal() == value)
                    .findFirst()
                    .get();
        }
    }

}
