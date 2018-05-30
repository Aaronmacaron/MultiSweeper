package tk.aakado.multisweeper.server.game;

import java.util.Optional;

public class Field {

    private final FieldCords fieldCords;
    private final FieldType type;
    private boolean isDiscovered;
    private Optional<Player> discoverPlayer;

    public Field(FieldCords fieldCords, FieldType type) {
        this.fieldCords = fieldCords;
        this.type = type;
    }

    public void discover(Player player) {

    }

    public boolean isMine() {
        return type == FieldType.MINE;
    }

    public FieldCords getFieldCords() {
        return fieldCords;
    }

    public int getFieldValue() {
        return this.type.ordinal();
    }

    public boolean isDiscovered() {
        return isDiscovered;
    }

    public Optional<Player> getDiscoverPlayer() {
        return discoverPlayer;
    }

    public enum FieldType {
        MINE, FIELD_1, FIELD_2, FIELD_3, FIELD_4, FIELD_5,  FIELD_6, FIELD_7, FIELD_8;
    }

}
