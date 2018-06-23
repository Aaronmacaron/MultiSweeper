package tk.aakado.multisweeper.client.connection.handler;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.connection.ClientMessage;
import tk.aakado.multisweeper.client.views.MultiSweeperView;
import tk.aakado.multisweeper.client.views.authentication.AuthenticationNotificator;
import tk.aakado.multisweeper.client.views.authentication.AuthenticationView;
import tk.aakado.multisweeper.client.views.gameselection.GameSelectionNotificator;
import tk.aakado.multisweeper.client.views.gameselection.GameSelectionView;
import tk.aakado.multisweeper.shared.Logger;
import tk.aakado.multisweeper.shared.connection.ActionHandler;
import tk.aakado.multisweeper.shared.connection.ActionType;
import tk.aakado.multisweeper.shared.connection.dtos.GameJoinedInfoDTO;

import java.util.Optional;

public class GameSelectionHandler {

    /**
     * This handler shows the authentication view if a password is needed to join a game.
     * @param message The message.
     */
    @ActionHandler(actionType = ActionType.PASSWORD_NEEDED)
    public void onPasswordNeeded(ClientMessage message) {
        int gameId = message.getParams().getAsInt();

        // This should be save since the type is being checked in getActiveView
        AuthenticationNotificator notificator = (AuthenticationNotificator) Client.getInstance().getNotificator(AuthenticationView.class);
        notificator.setGameId(gameId);

        // Change to authentication view, on JavaFX Application thread
        Platform.runLater(() -> Client.getInstance().changeView(AuthenticationView.class));
    }

    /**
     * This handler notifies the authentication view that the password has been rejected.
     * @param message The message.
     */
    @ActionHandler(actionType = ActionType.PASSWORD_WRONG)
    public void onWrongPassword(ClientMessage message) {
        MultiSweeperView authView = Client.getInstance().getActiveView(AuthenticationView.class);

        // This should be save since the type is being checked in getActiveView
        AuthenticationNotificator notificator = (AuthenticationNotificator) authView.getNotificator();

        // Execute on JavaFX Application thread
        Platform.runLater(notificator::rejected);
    }

    @ActionHandler(actionType = ActionType.GAME_JOINED)
    public void onGameJoined(ClientMessage message) {
        GameJoinedInfoDTO gameJoinedInfo = new Gson().fromJson(message.getParams(), GameJoinedInfoDTO.class);

        MultiSweeperView gameSelectionView = Client.getInstance().getActiveView(GameSelectionView.class);

        // This should be save since the type is being checked in getActiveView
        GameSelectionNotificator notificator = (GameSelectionNotificator) gameSelectionView.getNotificator();
        notificator.gameSelected(gameJoinedInfo.isAdmin(), gameJoinedInfo.isRunning());
    }

    /**
     * If the id of the game the user submitted to join is wrong the user gets notified.
     * @param message The message.
     */
    @ActionHandler(actionType = ActionType.WRONG_GAME_ID)
    public void onWrongGameId(ClientMessage message) {
        MultiSweeperView currentView = Client.getInstance().getActiveView();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Game invalid");
        alert.setHeaderText("The game you selected is not valid nor existing.");
        alert.setContentText("Please reselect the game you want to join and retry.");
        alert.initOwner(currentView.getScene().getWindow());
        Platform.runLater(alert::show);
    }

}
