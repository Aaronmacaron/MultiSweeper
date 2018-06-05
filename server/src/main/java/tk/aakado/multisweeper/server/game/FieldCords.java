package tk.aakado.multisweeper.server.game;

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FieldCords) {
            FieldCords other = (FieldCords) obj;
            return other.getX() == this.getX() && other.getY() == this.getY();
        }
        return false;
    }

    // Getters

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
