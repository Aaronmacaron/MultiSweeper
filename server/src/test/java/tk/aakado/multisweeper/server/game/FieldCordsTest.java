package tk.aakado.multisweeper.server.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class FieldCordsTest {

    private static final int X = 8;
    private static final int Y = -4;

    @Test
    public void testSameCoordinatesAreEqual() {
        // FieldCords with same x and y coordinate should be equal.
        FieldCords cord1 = new FieldCords(X, Y);
        FieldCords cord2 = new FieldCords(X, Y);
        assertEquals(cord1, cord2);
    }

    @Test
    public void testDifferentCoordinatesAreNotEqual() {
        // FieldCords that do not have the same coordinates are not equal.
        FieldCords cord1 = new FieldCords(X, Y);
        FieldCords cord2 = new FieldCords(6, 2);
        assertNotEquals(cord1, cord2);
    }

    @Test
    public void getX() {
        FieldCords fieldCords = new FieldCords(X, Y);
        assertEquals(X, fieldCords.getX());
    }

    @Test
    public void getY() {
        FieldCords fieldCords = new FieldCords(X, Y);
        assertEquals(Y, fieldCords.getY());
    }
}