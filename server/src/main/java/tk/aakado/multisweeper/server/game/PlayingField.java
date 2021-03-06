package tk.aakado.multisweeper.server.game;

import tk.aakado.multisweeper.server.game.Field.FieldType;
import tk.aakado.multisweeper.shared.connection.dtos.FieldDTO;
import tk.aakado.multisweeper.shared.game.FieldState;

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
    private int numberOfFlags;
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

        int value = (int) this.fields.stream()
                .filter(field -> surroundingCords.contains(field.getFieldCords())) // only the surrounding fields
                .filter(Field::isMine) // only count the mines
                .count();

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
     * @return A list of all fields that have been discovered.
     */
    public List<FieldDTO> discoverField(FieldCords fieldCords, Player player) {
        Field theField = this.getField(fieldCords)
                .orElseThrow(() -> new IllegalArgumentException("The given coordinates are invalid."));

        List<FieldDTO> changedFields = new ArrayList<>();

        if (theField.isFlagged() || theField.isDiscovered()) {
            // Do not discover already flagged/discovered field
            return changedFields;
        }

        theField.discover(player);

        changedFields.add(convertToFieldDTO(theField, false));

        if (theField.isMine()) {
            // The game ends: expose all fields to the player
            return this.fields.stream()
                    .map(field -> this.convertToFieldDTO(field, true))
                    .collect(Collectors.toList());
        }

        if (theField.getFieldValue() == FieldType.FIELD_0.getValue()) {
            // for empty field also discover the surrounding fields
            final List<FieldCords> surrounding = getSurroundingCords(theField.getFieldCords());
            this.fields.stream()
                    // get surrounding fields
                    .filter(field -> surrounding.contains(field.getFieldCords()))
                    // skip discovered ones
                    .filter(field -> !field.isDiscovered())
                    .forEach(field -> changedFields.addAll(discoverField(field.getFieldCords(), player)));
        }

        return changedFields;
    }

    /**
     * Flags a field of this playing field. The field is only flagged when it is not
     * @param fieldCords The coordinates of the field to flag.
     * @param player The player which triggered the action.
     * @return If the field has been flagged or not.
     */
    public Optional<FieldDTO> flagField(FieldCords fieldCords, Player player) {
        Field theField = this.getField(fieldCords)
                .orElseThrow(() -> new IllegalArgumentException("The given coordinates are invalid."));

        if (theField.isDiscovered()) {
            // Already discovered field cannot be flagged
            return Optional.empty();
        }

        if (theField.isFlagged()) {
            this.numberOfFlags--;
            theField.unflag();
        } else {
            this.numberOfFlags++;
            // it is only allowed to have as many flags as there are mines
            if (numberOfFlags > numberOfMines) {
                return Optional.empty();
            }
            theField.flag(player);
        }
        return Optional.of(convertToFieldDTO(theField, false));
    }

    /**
     * Checks if the given field coordinates are in the playing field.
     * @param fieldCords The coordinates to test.
     * @return If the field coordinates are valid.
     */
    public boolean isValidCoordinate(FieldCords fieldCords) {
        return this.fields.stream().map(Field::getFieldCords).anyMatch(cords -> cords.equals(fieldCords));
    }

    /**
     * Get all fields of this playing fields as {@link FieldDTO}.
     * @return The {@link FieldDTO}s of this playing field.
     */
    public List<FieldDTO> getCurrentPlayingFieldState() {
        return this.fields.stream()
                .map(field -> convertToFieldDTO(field, false))
                .collect(Collectors.toList());
    }

    /**
     * Converts a {@link Field} into a {@link FieldDTO}.
     * @param field The field to convert.
     * @param exposed If all details of the field are exposed.
     * @return The converted field.
     */
    private FieldDTO convertToFieldDTO(Field field, boolean exposed) {
        int x = field.getFieldCords().getX();
        int y = field.getFieldCords().getY();
        FieldState state = exposed ? getExposedFieldState(field) : getFieldState(field);

        if (!field.isMine() && state == FieldState.DISCOVERED) {
            // if the field is not a mine and discovered, return also the value of the field
            return new FieldDTO(x, y, state, field.getFieldValue());
        }

        return new FieldDTO(x, y, state);
    }

    /**
     * Returns the {@link FieldState} of the given {@link Field}.
     * @param field The field of which to get the field state.
     * @return The field state that corespondents to the field.
     */
    private FieldState getFieldState(Field field) {
        if (field.isFlagged()) {
            return FieldState.FLAG;
        }
        if (field.isDiscovered()) {
            return FieldState.DISCOVERED;
        }
        return FieldState.UNDISCOVERED;
    }


    /**
     * Returns the {@link FieldState} of the given {@link Field} but with all information exposed.
     * @param field The field of which to get the exposed field state.
     * @return The field state that corespondents to the field.
     */
    private FieldState getExposedFieldState(Field field) {
        if (field.isMine()) {
            if (field.isDiscovered()) {
                return FieldState.MINE_EXPLODED;
            }
            return FieldState.MINE;
        }
        if (field.isFlagged() && !field.isMine()) {
            return FieldState.FALSE_FLAGGED_MINE;
        }
        return getFieldState(field);
    }

    /**
     * Returns if the game is won.
     * @return if the game is won.
     */
    public boolean gameWon() {
        // filters all fields that are completed
        return this.fields.stream()
                // filter out all mines that are flagged
                .filter(field -> !(field.isMine() && field.isFlagged()))
                // fields that are discovered and not a mine
                .allMatch(field -> field.isDiscovered() && !field.isMine());
    }

    // Getters

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }

}
