package tk.aakado.multisweeper.server.game;

/**
 * The coordinates of a field.
 */
public class FieldCords {

    private final int x;
    private final int y;

    /**
     * Constructor.
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public FieldCords(int x, int y) {
        this.x = x;
        this.y = y;
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
