package tk.aakado.multisweeper.server;

import java.util.Optional;

public class Field {

    private final FieldCords fieldCords;
    private final int fieldValue;
    private boolean isDiscovered;
    private Optional<Player> discoverPlayer;

    public Field(FieldCords fieldCords, int fieldValue) {
        this.fieldCords = fieldCords;
        this.fieldValue = fieldValue;
    }

    public void discover(Player player) {

    }

    // TODO: isMine() -> how to handle different kinds of field has to be discussed

    public FieldCords getFieldCords() {
        return fieldCords;
    }

    public int getFieldValue() {
        return fieldValue;
    }

    public boolean isDiscovered() {
        return isDiscovered;
    }

    public Optional<Player> getDiscoverPlayer() {
        return discoverPlayer;
    }

}
