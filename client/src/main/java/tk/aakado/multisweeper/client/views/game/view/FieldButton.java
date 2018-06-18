package tk.aakado.multisweeper.client.views.game.view;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tk.aakado.multisweeper.client.views.game.model.Field;
import tk.aakado.multisweeper.shared.game.FieldState;

/**
 * Special Button representing a @{@link Field} on the @{@link FieldGrid}
 *
 * @author Dominik Strässle
 * @author Aaron Ebnöther
 */
public class FieldButton extends Button {

    private IntegerProperty x = new SimpleIntegerProperty();
    private IntegerProperty y = new SimpleIntegerProperty();
    private ObjectProperty<FieldState> fieldState = new SimpleObjectProperty<>();
    private IntegerProperty value = new SimpleIntegerProperty();
    final private static double size = 45;

    /**
     * Create a new FieldButton.
     * Binds all properties, creates a istener for a changing state of a field and beautify the button appearance.
     *
     * @param field   Field containing the state, value and coordinates
     * @param onClick Action will be called on a leftClick
     */
    public FieldButton(Field field, EventHandler<MouseEvent> onClick) {
        // bind the properties
        x.bind(field.xProperty());
        y.bind(field.yProperty());
        fieldState.bind(field.fieldStateProperty());
        value.bind(field.valueProperty());

        // Remove padding
        setPadding(new Insets(0));

        // change the graphic when the state changes
        fieldState.addListener(this::onChange);

        // send a leftClick with the coordinates
        this.setOnMouseClicked(onClick);

        // set the size
        this.setPrefSize(size, size);
        this.setStyle("-fx-background-radius: 0");

        // set the graphic
        changeImage();

    }


    /**
     * Changes the deposited graphic corresponding to the actual state
     *
     * @param observable Observable
     * @param oldValue   the old state
     * @param newValue   the new state
     */
    private void onChange(Observable observable, FieldState oldValue, FieldState newValue) {
        changeImage();
    }

    /**
     * Sets the correct graphic according to object state (fieldState and value)
     */
    private void changeImage() {

        // Get correct image
        ImageView imageView = new ImageView(getImage(getTileType()));

        // Make sure image is not bigger than button
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(size);
        imageView.setFitWidth(size);

        setGraphic(imageView);
    }

    /**
     * Returns a mapping of values and TileTypes
     *
     * @return Mapping of value and TileType
     */
    private Map<Integer, TileType> getValueTileTypeMap() {
        Map<Integer, TileType> images = new HashMap<>();
        images.put(0, TileType.EMPTY);
        images.put(1, TileType.ONE);
        images.put(2, TileType.TWO);
        images.put(3, TileType.THREE);
        images.put(4, TileType.FOUR);
        images.put(5, TileType.FIVE);
        images.put(6, TileType.SIX);
        images.put(7, TileType.SEVEN);
        images.put(8, TileType.EIGHT);
        return images;
    }

    /**
     * Evaluates TileType based on fieldState and value
     *
     * @return The correct TileType
     */
    private TileType getTileType() {
        switch (fieldState.get()) {
            case DISCOVERED: {
                return getValueTileTypeMap().get(value.get());
            }
            case FLAG: {
                return TileType.FLAG;
            }
            case MINE: {
                return TileType.MINE;
            }
            case FALSE_FLAGGED_MINE: {
                return TileType.WRONG_MINE;
            }
            case MINE_EXPLODED: {
                return TileType.MINE_EXPLODED;
            }
            case UNDISCOVERED:
            default: {
                return TileType.UNDISCOVERED;
            }
        }
    }

    /**
     * Returns Tile Image by image name
     *
     * @param name The name of the image (without extension)
     * @return The Image object associated with the name
     */
    private Image getImageByName(String name) {
        return new Image(this.getClass().getResourceAsStream(String.format("../tiles/%s.png", name)));
    }

    /**
     * Returns Tile Image by TileType
     *
     * @param tileType TileType of Image
     * @return The Image object associated with the TileType
     */
    private Image getImage(TileType tileType) {
        return getImageByName(tileType.getImageName());
    }

    // getters & setters
    public int getX() {
        return x.get();
    }

    public IntegerProperty xProperty() {
        return x;
    }

    public int getY() {
        return y.get();
    }

    public IntegerProperty yProperty() {
        return y;
    }

    public static double getSize() {
        return size;
    }

    /**
     * Represents the different tiles a FieldButton can morph into. TileTypes can directly be mapped to images of tiles.
     */
    private enum TileType {

        EMPTY, UNDISCOVERED, FLAG, MINE, MINE_EXPLODED, WRONG_MINE, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT;

        /**
         * Returns imageName of TileType (without extension)
         *
         * @return Image name
         */
        public String getImageName() {
            return name().toLowerCase();
        }

    }

}
