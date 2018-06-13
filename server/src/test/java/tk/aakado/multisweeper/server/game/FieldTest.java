package tk.aakado.multisweeper.server.game;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tk.aakado.multisweeper.server.game.Field.FieldType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class FieldTest {

    private static final FieldCords DUMMY_CORDS = new FieldCords(10, 6);
    private static final Player DUMMY_PLAYER = new Player("Dummy");

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private Field dummyNormalField;

    @Before
    public void setUp() {
        this.dummyNormalField = new Field(DUMMY_CORDS, FieldType.FIELD_0);
    }

    @Test
    public void discover() {
        dummyNormalField.discover(DUMMY_PLAYER);

        exception.expect(IllegalStateException.class);
        dummyNormalField.discover(DUMMY_PLAYER);
    }

    @Test
    public void flag() {
        dummyNormalField.flag(DUMMY_PLAYER);

        exception.expect(IllegalStateException.class);
        dummyNormalField.flag(DUMMY_PLAYER);
    }

    @Test
    public void unflag() {
        exception.expect(IllegalStateException.class);
        dummyNormalField.unflag();
    }

    @Test
    public void testDiscoverFlaggedField() {
        this.dummyNormalField.flag(DUMMY_PLAYER);

        exception.expect(IllegalStateException.class);
        this.dummyNormalField.discover(DUMMY_PLAYER);
    }

    @Test
    public void testFlagDiscoveredField() {
        this.dummyNormalField.discover(DUMMY_PLAYER);

        exception.expect(IllegalStateException.class);
        this.dummyNormalField.flag(DUMMY_PLAYER);
    }

    @Test
    public void isMine() {
        Field mineField = new Field(DUMMY_CORDS, FieldType.MINE);
        assertTrue(mineField.isMine());

        getAllNormalFields().forEach(normalField -> assertFalse(normalField.isMine()));
    }

    @Test
    public void getFieldCords() {
        assertEquals(DUMMY_CORDS, dummyNormalField.getFieldCords());
    }

    @Test
    public void getFieldValue() {
        List<Field> normalFields = getAllNormalFields();
        for (int i = 0; i < normalFields.size(); i++) {
            assertEquals(i, normalFields.get(i).getFieldValue());
        }

        Field mine = new Field(DUMMY_CORDS, FieldType.MINE);
        exception.expect(IllegalStateException.class);
        mine.getFieldValue();
    }

    @Test
    public void isDiscovered() {
        Field discoveredField = new Field(DUMMY_CORDS, FieldType.FIELD_0);
        discoveredField.discover(DUMMY_PLAYER);
        assertTrue(discoveredField.isDiscovered());


        Field hiddenField = new Field(DUMMY_CORDS, FieldType.FIELD_0);
        assertFalse(hiddenField.isDiscovered());
    }

    @Test
    public void getDiscoverPlayer() {
        Field field = new Field(DUMMY_CORDS, FieldType.FIELD_0);
        field.discover(DUMMY_PLAYER);

        Optional<Player> discoverPlayer = field.getDiscoverPlayer();

        assertTrue(discoverPlayer.isPresent());
        assertEquals(DUMMY_PLAYER, discoverPlayer.get());
    }

    @Test
    public void isFlagged() {
        Field flaggedField = new Field(DUMMY_CORDS, FieldType.FIELD_0);
        flaggedField.flag(DUMMY_PLAYER);
        assertTrue(flaggedField.isFlagged());

        Field notFlaggedField = new Field(DUMMY_CORDS, FieldType.FIELD_0);
        assertFalse(notFlaggedField.isFlagged());
    }

    @Test
    public void getFlagPlayer() {
        Field field = new Field(DUMMY_CORDS, FieldType.FIELD_0);
        field.flag(DUMMY_PLAYER);

        Optional<Player> flagPlayer = field.getFlagPlayer();

        assertTrue(flagPlayer.isPresent());
        assertEquals(DUMMY_PLAYER, flagPlayer.get());
    }

    private List<Field> getAllNormalFields() {
        return Arrays.stream(FieldType.values())
                .filter(type -> type != FieldType.MINE)
                .map(type -> new Field(DUMMY_CORDS, type))
                .collect(Collectors.toList());
    }

}