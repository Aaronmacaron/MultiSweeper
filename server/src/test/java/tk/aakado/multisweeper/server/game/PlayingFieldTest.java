package tk.aakado.multisweeper.server.game;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class PlayingFieldTest {

    private static final int WIDTH = 10;
    private static final int HEIGHT = 5;
    private static final Player DUMMY_PLAYER = new Player("Dummy");

    private PlayingField playingField;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        this.playingField = new PlayingField(WIDTH, HEIGHT, 10);
    }

    @Test
    public void testNumberOfMinesCannotExceedNumberOfFields() {
        // Field width fields >= mines does not fail
        new PlayingField(WIDTH, HEIGHT, WIDTH * HEIGHT);

        // Field with more mines than fields fails
        exception.expect(IllegalArgumentException.class);
        new PlayingField(WIDTH, HEIGHT, WIDTH * HEIGHT + 1);
    }

    @Test
    public void discoverField() {
        this.playingField.discoverField(new FieldCords(0, 0), DUMMY_PLAYER);

        // Not valid coordinates should throw exception
        exception.expect(IllegalArgumentException.class);
        this.playingField.discoverField(new FieldCords(WIDTH, HEIGHT), DUMMY_PLAYER);

    }

    @Test
    public void flagField() {
        this.playingField.flagField(new FieldCords(0, 0), DUMMY_PLAYER);

        // Not valid coordinate should throw exception
        exception.expect(IllegalArgumentException.class);
        this.playingField.flagField(new FieldCords(WIDTH, HEIGHT), DUMMY_PLAYER);
    }

    @Test
    public void isValidCoordinate() {
        FieldCords fieldCordsLowest = new FieldCords(0, 0);
        assertTrue(this.playingField.isValidCoordinate(fieldCordsLowest));

        FieldCords fieldCordsHighest = new FieldCords(WIDTH - 1, HEIGHT - 1);
        assertTrue(this.playingField.isValidCoordinate(fieldCordsHighest));

        FieldCords fieldCordsTooLow = new FieldCords(-1, -1);
        assertFalse(this.playingField.isValidCoordinate(fieldCordsTooLow));

        FieldCords fieldCordsTooHigh = new FieldCords(WIDTH, HEIGHT);
        assertFalse(this.playingField.isValidCoordinate(fieldCordsTooHigh));
    }

    @Test
    public void getWidth() {
        assertEquals(WIDTH, this.playingField.getWidth());
    }

    @Test
    public void getHeight() {
        assertEquals(HEIGHT, this.playingField.getHeight());
    }

}
