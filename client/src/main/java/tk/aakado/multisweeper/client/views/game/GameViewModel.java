package tk.aakado.multisweeper.client.views.game;

import java.time.Duration;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tk.aakado.multisweeper.client.views.game.model.Field;
import tk.aakado.multisweeper.shared.Logger;
import tk.aakado.multisweeper.shared.game.FieldState;

public class GameViewModel implements ViewModel, GameNotificator {

    private ObjectProperty<Duration> elapsedTime = new SimpleObjectProperty<>(Duration.ZERO);//TODO to prevent NUllPointerException Duration.ZERO is set
    private IntegerProperty numberOfPlayers = new SimpleIntegerProperty();
    private IntegerProperty remainingMines = new SimpleIntegerProperty();
    private ListProperty<Field> fields = new SimpleListProperty<>(FXCollections.emptyObservableList());
    private IntegerProperty fieldWidth = new SimpleIntegerProperty();
    private IntegerProperty fieldHeight = new SimpleIntegerProperty();

    @Override
    public void playerDisconnected(String player, boolean isNewAdmin) {
        //TODO: Implement
    }

    @Override
    public void playerConnected(String player) {
        //TODO: Implement
    }

    @Override
    public void restart(int x, int y) {
        // set the size of the field
        this.fieldWidth.setValue(x);
        this.fieldHeight.setValue(y);

        // create a new empty ObservableArrayList
        ObservableList<Field> newFields = FXCollections.observableArrayList();

        // Create all required Fields and add them to the fields list
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                newFields.add(new Field(i, j, FieldState.UNDEFINED, 0));
            }
        }

        // set the new fields as new value to the existing ListProperty
        fields.setValue(newFields);
    }

    @Override
    public void updateField(int[] coords, String newState) {

        Field field = fields.get().stream()
                .filter(f -> f.getX() == coords[0] && f.getY() == coords[1])
                .findFirst()
                .orElseThrow(() -> {
                    Logger.get(this).error("A field with the coordinates %s:%s does not exist", coords[0], coords[1]);
                    return new IllegalArgumentException("A field with the coordinates " + coords[0] + ":" + coords[1] + " does not exist");
                });

        try {
            field.setFieldState(FieldState.valueOf(newState));
        } catch (IllegalArgumentException ex) {
            Logger.get(this).error("The field state %s does not exists", newState);
        }
    }

    @Override
    public void finished() {
        //TODO: Implement
    }

    /**
     * The Player disconnects from the game
     */
    public void disconnect() {
        // TODO: Implement
    }

    /**
     * Handles a click on a field
     * Send the click to the server over the transmitter
     *
     * @param x x-coordinate of the field
     * @param y y-coordinate of the field
     */
    public void click(int x, int y) {
        fields.stream()
                .filter(field -> field.getX() == x && field.getY() == y)
                .findAny()
                .get()
                .setFieldState(FieldState.FLAG);
        //TODO: Implement
    }

    public ObjectProperty<Duration> elapsedTimeProperty() {
        return elapsedTime;
    }

    public IntegerProperty numberOfPlayersProperty() {
        return numberOfPlayers;
    }

    public IntegerProperty remainingMinesProperty() {
        return remainingMines;
    }

    public ObservableList<Field> getFields() {
        return fields.get();
    }

    public ListProperty<Field> fieldsProperty() {
        return fields;
    }

    public int getFieldWidth() {
        return fieldWidth.get();
    }

    public IntegerProperty fieldWidthProperty() {
        return fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight.get();
    }

    public IntegerProperty fieldHeightProperty() {
        return fieldHeight;
    }
}
