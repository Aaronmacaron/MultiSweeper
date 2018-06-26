package tk.aakado.multisweeper.client.connection.handler;

import javafx.application.Platform;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.connection.ClientMessage;
import tk.aakado.multisweeper.client.views.configuration.ConfigurationNotificator;
import tk.aakado.multisweeper.client.views.configuration.ConfigurationView;
import tk.aakado.multisweeper.shared.connection.ActionHandler;
import tk.aakado.multisweeper.shared.connection.ActionType;

public class ConfigurationHandler {

    /**
     * Gets executed when server started the game
     * @param clientMessage The message that was sent by client
     */
    @ActionHandler(actionType = ActionType.GAME_STARTED)
    public void onGameStarted(ClientMessage clientMessage) {
        // Start game
        ConfigurationNotificator notificator = (ConfigurationNotificator) Client.getInstance()
                .getNotificator(ConfigurationView.class);
        Platform.runLater(notificator::gameStarted);
    }
}
