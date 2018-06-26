package tk.aakado.multisweeper.client.connection.handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.connection.ClientMessage;
import tk.aakado.multisweeper.client.views.configuration.ConfigurationNotificator;
import tk.aakado.multisweeper.client.views.configuration.ConfigurationView;
import tk.aakado.multisweeper.client.views.connection.ConnectionNotificator;
import tk.aakado.multisweeper.client.views.connection.ConnectionView;
import tk.aakado.multisweeper.client.views.gameselection.GameSelectionView;
import tk.aakado.multisweeper.client.views.gameselection.GameSelectionViewModel;
import tk.aakado.multisweeper.shared.connection.ActionHandler;
import tk.aakado.multisweeper.shared.connection.ActionType;
import tk.aakado.multisweeper.shared.connection.dtos.DisconnectDTO;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * ActionHandler that handles connect action sent by server when client connects
 */
public class ConnectedHandler {

    /**
     * Action handler for when client successfully connected and the server accepted the connection
     * @param message The message sent by the server
     */
    @ActionHandler(actionType = ActionType.CONNECTED)
    public void onConnected(ClientMessage message) {
        ConnectionNotificator notificator = (ConnectionNotificator) Client.getInstance()
                .getActiveView(ConnectionView.class).getNotificator();

        // Use Platform.runLater since this is executed in other thread
        Platform.runLater(() -> {
            notificator.connected();
            setGameIds(message); // Set game Ids after view was changed
        });
    }

    /**
     * Action handler called when another player disconnected from game
     * @param message The message sent by the server
     */
    @ActionHandler(actionType = ActionType.DISCONNECTED)
    public void onDisconnected(ClientMessage message) {
        // Extract data from message
        DisconnectDTO disconnectDTO = new Gson().fromJson(message.getParams(), DisconnectDTO.class);
        ConfigurationNotificator notificator = (ConfigurationNotificator) Client.getInstance()
                .getActiveView(ConfigurationView.class).getNotificator();

        // Send to view using notificator
        Platform.runLater(() ->
                notificator.playerDisconnected(disconnectDTO.getPlayer(), disconnectDTO.isAdmin()));
    }

    /**
     * Sets game ids in GameSelectionView
     * @param message The message sent by server (passed on by onConnected)
     */
    private void setGameIds(ClientMessage message) {
        // Get gameIds of message
        Type setType = new TypeToken<Set<Integer>>(){}.getType(); // GSON hack to get type of generic
        Set<Integer> gameIds = new Gson().fromJson(message.getParams(), setType);

        // Set gameIds
        GameSelectionViewModel viewModel = (GameSelectionViewModel) Client.getInstance()
                .getActiveView(GameSelectionView.class).getViewModel();
        viewModel.setAvailableGames(gameIds);
    }

    /**
     * Gets executed when other player connects
     * @param message The message that is sent by the server
     */
    @ActionHandler(actionType = ActionType.PLAYER_CONNECTED)
    public void onPlayerConnect(ClientMessage message) {
        // Extract name of player
        String name = message.getParams().getAsString();

        ConfigurationNotificator notificator = (ConfigurationNotificator) Client.getInstance()
                .getActiveView(ConfigurationView.class).getNotificator();

        // Add player to view
        Platform.runLater(() -> notificator.playerConnected(name));
    }

}
