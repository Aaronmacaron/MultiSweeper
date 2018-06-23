package tk.aakado.multisweeper.client.connection.handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.connection.ClientMessage;
import tk.aakado.multisweeper.client.views.MultiSweeperView;
import tk.aakado.multisweeper.client.views.connection.ConnectionNotificator;
import tk.aakado.multisweeper.client.views.connection.ConnectionView;
import tk.aakado.multisweeper.client.views.gameselection.GameSelectionView;
import tk.aakado.multisweeper.client.views.gameselection.GameSelectionViewModel;
import tk.aakado.multisweeper.shared.Logger;
import tk.aakado.multisweeper.shared.connection.ActionHandler;
import tk.aakado.multisweeper.shared.connection.ActionType;

import java.lang.reflect.Type;
import java.util.Optional;
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
        Optional<MultiSweeperView> optionalMultiSweeperView = Client.getInstance().getActiveView(ConnectionView.class);
        if (!optionalMultiSweeperView.isPresent()) {
            // Do nothing if the wrong view is set
            Logger.get(this).warn("An actionHandler was called that does not match the currently set view.");
            return;
        }

        // This should be save since the type is being checked in getActiveView
        ConnectionNotificator notificator = (ConnectionNotificator) optionalMultiSweeperView.get().getNotificator();

        // Use Platform.runLater since this is executed in other thread
        Platform.runLater(() -> {
            notificator.connected();
            setGameIds(message); // Set game Ids after view was changed
        });
    }

    /**
     * Sets game ids in GameSelectionView
     * @param message The message sent by server (passed on by onConnected)
     */
    private void setGameIds(ClientMessage message) {
        Optional<MultiSweeperView> optionalMultiSweeperView = Client.getInstance().getActiveView(GameSelectionView.class);
        if (!optionalMultiSweeperView.isPresent()) {
            // Do nothing if the wrong view is set
            Logger.get(this).warn("An actionHandler was called that does not match the currently set view.");
            return;
        }

        // Get gameIds of message
        Type setType = new TypeToken<Set<Integer>>(){}.getType(); // GSON hack to get type of generic
        Set<Integer> gameIds = new Gson().fromJson(message.getParams(), setType);

        // Set gameIds
        GameSelectionViewModel viewModel = (GameSelectionViewModel) optionalMultiSweeperView.get().getViewModel();
        viewModel.setAvailableGames(gameIds);
    }

}
