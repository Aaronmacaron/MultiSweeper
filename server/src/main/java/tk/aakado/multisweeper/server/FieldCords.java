package tk.aakado.multisweeper.server;

public class FieldCords {

    private final int x;
    private final int y;

    public FieldCords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isValid(PlayingField playingField) {
        // TODO: Implement
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
