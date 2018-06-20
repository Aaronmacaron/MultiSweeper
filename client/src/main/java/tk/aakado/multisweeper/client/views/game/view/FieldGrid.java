package tk.aakado.multisweeper.client.views.game.view;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import tk.aakado.multisweeper.client.views.game.model.Field;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the playing field with @{@link FieldButton}.
 *
 * @author Dominik Strässle
 */
public class FieldGrid extends GridPane {
    /**
     * List with all FieldButtons
     */
    private ListProperty<FieldButton> fieldButtons = new SimpleListProperty<>(FXCollections.emptyObservableList());
    /**
     * List with all fields
     */
    private ListProperty<Field> fields = new SimpleListProperty<>(FXCollections.emptyObservableList());

    private IntegerProperty fieldWidth = new SimpleIntegerProperty();
    private IntegerProperty fieldHeight = new SimpleIntegerProperty();
    /**
     * Event which will be called on a leftClick on a FieldButton
     */
    private EventHandler<MouseEvent> onClick;

    /**
     * Cache for all tile images
     */
    private Map<String, Image> imageCache = new HashMap<>();

    /**
     * Create a new FieldGrid
     *
     * @param fields      All fields available on the field
     * @param fieldWidth  the width of the field / length of the x-axis
     * @param fieldHeight the height of the field / length of the y-axis
     * @param onClick     the action that should be called, when a FieldButton is clicked
     */
    public FieldGrid(ListProperty<Field> fields, IntegerProperty fieldWidth, IntegerProperty fieldHeight, EventHandler<MouseEvent> onClick) {
        // bind all variables
        this.fields.bind(fields);
        this.fieldWidth.bind(fieldWidth);
        this.fieldHeight.bind(fieldHeight);
        this.onClick = onClick;

        // build the field
        buildField();

        // add a change listener
        this.fields.addListener(this::change);
    }

    /**
     * When the List changes after a reset, the field should be rebuilt
     *
     * @param observable Observalbe
     * @param oldList    The old List
     * @param newList    The new List
     */
    private void change(ObservableValue<? extends ObservableList<Field>> observable, ObservableList<Field> oldList, ObservableList<Field> newList) {
        buildField();
    }

    /**
     * Rebuild the field
     */
    private void buildField() {
        // clear all Buttons in the GridPane
        this.getChildren().clear();

        // empty list
        ObservableList<FieldButton> newFieldButtons = FXCollections.observableArrayList();

        // create FieldButtons for every Field
        fields.get().forEach(field -> newFieldButtons.add(new FieldButton(field, this, this.onClick)));

        // set the new List as actual list of Buttons
        this.fieldButtons.setValue(newFieldButtons);

        // Add the FieldButtons in a correct order to the GridPane
        // count up the index of the next element
        int counter = 0;

        // all columns
        for (int i = 0; i < fieldWidth.get(); i++) {
            // all rows
            for (int j = 0; j < fieldHeight.get(); j++) {
                // add the corresponding FieldButton
                this.add(fieldButtons.get().get(counter), i, j);
                counter++;
            }
        }

    }

    public Map<String, Image> getImageCache() {
        return imageCache;
    }
}