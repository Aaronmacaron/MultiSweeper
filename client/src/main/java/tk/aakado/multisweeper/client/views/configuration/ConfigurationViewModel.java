package tk.aakado.multisweeper.client.views.configuration;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import tk.aakado.multisweeper.client.App;

public class ConfigurationViewModel implements ViewModel, ConfigurationNotificator {

    private BooleanProperty admin = new SimpleBooleanProperty(false);
    private DoubleProperty mineDensity = new SimpleDoubleProperty();
    private IntegerProperty fieldWidth = new SimpleIntegerProperty();
    private IntegerProperty fieldHeight = new SimpleIntegerProperty();
    private StringProperty password = new SimpleStringProperty();
    private ListProperty<String> players = new SimpleListProperty<>(FXCollections.emptyObservableList());

    /**
     * Starts the configured game
     */
    public void start() {
        App.getInstance().getTransmitter().start(fieldWidth.get(), fieldHeight.get(), mineDensity.get());
    }

    /**
     * Saves the password to persistence
     */
    public void save() {
        App.getInstance().getTransmitter().savePassword(password.get());
    }

    /**
     * Disconnects from server
     */
    public void disconnect() {
        App.getInstance().getTransmitter().disconnect();
    }

    @Override
    public void playerDisconnected(String player, boolean isNewAdmin) {
        // check if the given player exists
        String playerToRemove = players.get().stream()
                .filter(s -> s.equals(player))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can't remove player that don't exist"));

        // remove the player
        players.get().remove(playerToRemove);

        // set the new admin
        admin.setValue(isNewAdmin);
    }

    @Override
    public void playerConnected(String player) {
        // check if a player with the same name already exists
        if (players.get().stream().anyMatch(s -> s.equals(player))) {
            throw new IllegalArgumentException("Player with the same name already exists");
        }
        players.get().add(player);
    }

    @Override
    public void gameStarted() {
        //TODO implement
    }


    public DoubleProperty mineDensityProperty() {
        return mineDensity;
    }

    public IntegerProperty fieldWidthProperty() {
        return fieldWidth;
    }

    public IntegerProperty fieldHeightProperty() {
        return fieldHeight;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public ListProperty<String> playersProperty() {
        return players;
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
}
