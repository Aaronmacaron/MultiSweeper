package tk.aakado.multisweeper.client.views.game;

import java.time.Duration;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseButton;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.views.connection.ConnectionView;
import tk.aakado.multisweeper.client.views.finished.FinishedView;
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
    private ListProperty<String> players = new SimpleListProperty<>(FXCollections.emptyObservableList());
    private BooleanProperty admin = new SimpleBooleanProperty(false);


    @Override
    public void playerDisconnected(String player, boolean isNewAdmin) {
        // check if the given player exists
        String playerToRemove = players.get().stream()
                .filter(s -> s.equals(player))
                .findFirst()
                .orElseThrow(() -> {
                    Logger.get(this).error("Can't remove player that doesn't exist");
                    return new IllegalArgumentException("Can't remove player that doesn't exist");
                });

        // remove the player
        players.get().remove(playerToRemove);

        // set the new admin
        admin.setValue(isNewAdmin);
    }

    @Override
    public void playerConnected(String player) {
        // check if a player with the same name already exists
        if (players.get().stream().anyMatch(s -> s.equals(player))) {
            Logger.get(this).error("Player with the same name already exists");
            throw new IllegalArgumentException("Player with the same name already exists");
        }
        players.get().add(player);
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
                newFields.add(new Field(i, j, FieldState.UNDISCOVERED, 0));
            }
        }

        // set the new fields as new value to the existing ListProperty
        fields.setValue(newFields);
    }

    @Override
    //TODO: new parameter value is needed
    public void updateField(int[] cords, FieldState newState) {

        Field field = fields.get().stream()
                .filter(f -> f.getX() == cords[0] && f.getY() == cords[1])
                .findFirst()
                .orElseThrow(() -> {
                    Logger.get(this).error("A field with the coordinates %s:%s does not exist", cords[0], cords[1]);
                    return new IllegalArgumentException("A field with the coordinates " + cords[0] + ":" + cords[1] + " does not exist");
                });

            field.setFieldState(newState);
    }

    @Override
    public void finished() {
        Client.getInstance().changeView(FinishedView.class);
    }

    /**
     * The Player disconnects from the game
     */
    public void disconnect() {
        Client.getInstance().getTransmitter().disconnect();
        Client.getInstance().changeView(ConnectionView.class);
    }

    /**
     * Handles a leftClick on a field
     * Send the leftClick to the server over the transmitter
     *
     * @param x x-coordinate of the field
     * @param y y-coordinate of the field
     */
    public void leftClick(int x, int y) {
        //TODO: This method call throws a nullpointer when the transmitter isn't initialized
        Client.getInstance().getTransmitter().click(x, y, MouseButton.PRIMARY);
    }

    /**
     * Handles a rightClick on a field
     * Send the rightClick to the server over the transmitter
     *
     * @param x x-coordinate of the field
     * @param y y-coordinate of the field
     */
    public void rightClick(int x, int y) {
        Client.getInstance().getTransmitter().click(x, y, MouseButton.SECONDARY);
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

    public boolean isAdmin() {
        return admin.get();
    }

    public BooleanProperty adminProperty() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin.set(admin);
    }

    public void sendRestart() {
        if (admin.get()) {
            Client.getInstance().getTransmitter().restart();
        }
    }
}
