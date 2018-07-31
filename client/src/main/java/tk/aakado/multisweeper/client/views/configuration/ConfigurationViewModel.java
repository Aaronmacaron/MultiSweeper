package tk.aakado.multisweeper.client.views.configuration;

import java.util.List;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.views.connection.ConnectionView;
import tk.aakado.multisweeper.client.views.game.GameView;
import tk.aakado.multisweeper.client.views.gameselection.GameSelectionView;
import tk.aakado.multisweeper.shared.Logger;

public class ConfigurationViewModel implements ViewModel, ConfigurationNotificator {

    private DoubleProperty mineDensity = new SimpleDoubleProperty();
    private IntegerProperty fieldWidth = new SimpleIntegerProperty();
    private IntegerProperty fieldHeight = new SimpleIntegerProperty();
    private StringProperty password = new SimpleStringProperty();
    private ListProperty<String> players = new SimpleListProperty<>(FXCollections.observableArrayList());

    /**
     * Starts the configured game
     */
    public void start() {
        if (Client.getInstance().isAdmin()) {
            Client.getInstance().getTransmitter().start(fieldWidth.get(), fieldHeight.get(), mineDensity.get());
        }
    }

    /**
     * Saves the password to persistence
     */
    public void save() {
        if (Client.getInstance().isAdmin()) {
            Client.getInstance().getTransmitter().savePassword(password.get());
        }
    }

    /**
     * Disconnects from server
     */
    public void leaveGame() {
//        Client.getInstance().getTransmitter().leaveGame();
//        Client.getInstance().changeView(GameSelectionView.class);
        Client.getInstance().getTransmitter().disconnect();
        Client.getInstance().changeView(ConnectionView.class);
    }

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
        Client.getInstance().getGameProperties().adminProperty().setValue(isNewAdmin);
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
    public void setPlayers(List<String> newPlayers) {
        players.get().setAll(newPlayers);
    }

    @Override
    public void gameStarted() {
        Client.getInstance().changeView(GameView.class);
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

}
