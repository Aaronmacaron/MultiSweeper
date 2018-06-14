package tk.aakado.multisweeper.client.views.game.view;

import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import tk.aakado.multisweeper.client.views.game.model.Field;
import tk.aakado.multisweeper.shared.game.FieldState;

/**
 * Special Button representing a @{@link Field} on the @{@link FieldGrid}
 *
 * @author Dominik Strässle
 */
public class FieldButton extends Button {

    private IntegerProperty x = new SimpleIntegerProperty();
    private IntegerProperty y = new SimpleIntegerProperty();
    private ObjectProperty<FieldState> fieldState = new SimpleObjectProperty<>();
    private IntegerProperty value = new SimpleIntegerProperty();

    /**
     * Create a new FieldButton.
     * Binds all properties, creates a istener for a changing state of a field and beautify the button appearance.
     *
     * @param field   Field containing the state, value and coordinates
     * @param onClick Action will be called on a click
     */
    public FieldButton(Field field, EventHandler<ActionEvent> onClick) {
        // bind the properties
        x.bind(field.xProperty());
        y.bind(field.yProperty());
        fieldState.bind(field.fieldStateProperty());
        value.bind(field.valueProperty());


        // change the graphic when the state changes
        fieldState.addListener(this::onChange);


        // send a click with the coordinates
        this.setOnAction(onClick);

        // set the size
        this.setPrefSize(45, 45);
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

    private void changeImage() {
        //TODO: Implement
        setText(this.fieldState.get().toString().substring(0, 1));
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
}
