package tk.aakado.multisweeper.client.connection.handler;

import javafx.application.Platform;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.connection.ClientMessage;
import tk.aakado.multisweeper.client.views.MultiSweeperView;
import tk.aakado.multisweeper.client.views.connection.ConnectionNotificator;
import tk.aakado.multisweeper.client.views.connection.ConnectionView;
import tk.aakado.multisweeper.shared.Logger;
import tk.aakado.multisweeper.shared.connection.ActionHandler;
import tk.aakado.multisweeper.shared.connection.ActionType;

import java.util.Optional;

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
        Platform.runLater(notificator::connected);
    }

}
