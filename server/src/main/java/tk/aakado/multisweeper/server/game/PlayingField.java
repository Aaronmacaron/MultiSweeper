package tk.aakado.multisweeper.server.game;

import tk.aakado.multisweeper.server.game.Field.FieldType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class is the playing field of the minesweeper game.
 */
public class PlayingField {

    private final int width;
    private final int height;
    private final int numberOfMines;
    private List<Field> fields;

    /**
     * Constructor.
     * @param width The width of the playing field (number of fields on the x-axis).
     * @param height The height of the playing field (number of fields on the y-axis).
     * @param numberOfMines Total number of mines the playing field has. May not exceed the number of total fields.
     */
    public PlayingField(int width, int height, int numberOfMines) {
        this.width = width;
        this.height = height;

        int numberOfFields = width * height;
        if (numberOfMines > numberOfFields) {
            throw new IllegalArgumentException("The number of mines cannot exceed the number of total fields.");
        }
        this.numberOfMines = numberOfMines;

        this.fields = new ArrayList<>(numberOfFields);

        generate();
    }

    /**
     * Returns a field of this playing field by its coordinates.
     * @param fieldCords The coordinates of the field.
     * @return Optional containing the field if there is one at the given coordinates.
     */
    private Optional<Field> getField(FieldCords fieldCords) {
        return this.fields.stream().filter(field -> field.getFieldCords().equals(fieldCords)).findFirst();
    }

    /**
     * Generates the playing field.
     */
    private void generate() {
        List<FieldCords> allCords = new ArrayList<>();

        // generate all coordinates
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                FieldCords cords = new FieldCords(x, y);
                allCords.add(cords);
            }
        }

        // generate the mine fields
        Collections.shuffle(allCords);
        allCords.stream().limit(this.numberOfMines)
                .forEach(fieldCords -> this.fields.add(new Field(fieldCords, FieldType.MINE)));

        // generate the normal fields
        allCords.stream().skip(this.numberOfMines)
                .forEach(this::createField);

    }

    /**
     * Creates a non-mined field at the given coordinates if it does not already exist.
     * @param cords The coordinates of the field.
     */
    private void createField(FieldCords cords) {
        if (this.getField(cords).isPresent()) {
            // field does already exist
            return;
        }

        List<FieldCords> surroundingCords = getSurroundingCords(cords);
        List<Field> surroundingFields = this.fields.stream()
                .filter(field -> surroundingCords.contains(field.getFieldCords()))
                .collect(Collectors.toList());

        int value = (int) surroundingFields.stream().filter(Field::isMine).count();

        FieldType type = FieldType.getByValue(value);
        Field field = new Field(cords, type);
        this.fields.add(field);
    }

    /**
     * Returns a list of all coordinates which are surrounding the given coordinate.
     * @param fieldCords The coordinates of which to get the surrounding ones.
     * @return All surrounding coordinates.
     */
    private List<FieldCords> getSurroundingCords(FieldCords fieldCords) {
        final int x = fieldCords.getX();
        final int y = fieldCords.getY();
        final FieldCords lowerBound = new FieldCords(x - 1, y - 1);
        final FieldCords upperBound = new FieldCords(x + 1, y + 1);

        return this.fields.stream()
                .map(Field::getFieldCords)
                // remove all field not in the range
                .filter(cords -> cords.getX() >= lowerBound.getX() && cords.getY() >= lowerBound.getY())
                .filter(cords -> cords.getX() <= upperBound.getX() && cords.getY() <= upperBound.getY())
                // remove current field
                .filter(cords -> !cords.equals(fieldCords))
                .collect(Collectors.toList());
    }

    /**
     * Discovers a field of this playing field.
     * If the field is a mine the game ends.
     * @param fieldCords The coordinates of the field to discover.
     * @param player The player which triggered the action.
     */
    public void discoverField(FieldCords fieldCords, Player player) {
        Field theField = this.getField(fieldCords)
                .orElseThrow(() -> new IllegalArgumentException("The given coordinates are invalid."));

        if (theField.isFlagged()) {
            // Do not discover flagged field
            return;
        }

        theField.discover(player);

        if (theField.isMine()) {
            // TODO: end game
        }
    }

    /**
     * Flags a field of this playing field.
     * @param fieldCords The coordinates of the field to flag.
     * @param player The player which triggered the action.
     */
    public void flagField(FieldCords fieldCords, Player player) {
        Field theField = this.getField(fieldCords)
                .orElseThrow(() -> new IllegalArgumentException("The given coordinates are invalid."));

        if (theField.isDiscovered()) {
            // Already discovered field cannot be flagged
            return;
        }

        if (theField.isFlagged()) {
            theField.unflag();
        } else {
            theField.flag(player);
        }
    }

    /**
     * Checks if the given field coordinates are in the playing field.
     * @param fieldCords The coordinates to test.
     * @return If the field coordinates are valid.
     */
    public boolean isValidCoordinate(FieldCords fieldCords) {
        return this.fields.stream().map(Field::getFieldCords).anyMatch(cords -> cords.equals(fieldCords));
    }

    // Getters

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
