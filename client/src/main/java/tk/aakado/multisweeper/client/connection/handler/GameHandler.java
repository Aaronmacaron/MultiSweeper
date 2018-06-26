package tk.aakado.multisweeper.client.connection.handler;

import com.google.gson.Gson;
import javafx.application.Platform;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.connection.ClientMessage;
import tk.aakado.multisweeper.client.views.game.GameNotificator;
import tk.aakado.multisweeper.client.views.game.GameView;
import tk.aakado.multisweeper.shared.connection.ActionHandler;
import tk.aakado.multisweeper.shared.connection.ActionType;
import tk.aakado.multisweeper.shared.connection.dtos.StartInfoDTO;

public class GameHandler {

    @ActionHandler(actionType = ActionType.CONFIGURE_GAME)
    public void onRestartOfGame(ClientMessage message) {
        StartInfoDTO startInfo = new Gson().fromJson(message.getParams(), StartInfoDTO.class);

        GameNotificator notificator = (GameNotificator) Client.getInstance().getNotificator(GameView.class);
        Platform.runLater(() -> {
            notificator.configureField(startInfo.getFieldWidth(), startInfo.getFieldHeight());
            startInfo.getInitialFieldState().forEach(fieldDTO -> notificator
                    .updateField(fieldDTO.getX(), fieldDTO.getY(), fieldDTO.getState(), fieldDTO.getValue()));
        });
    }

    @ActionHandler(actionType = ActionType.GAME_FINISHED)
    public void onGameFinished(ClientMessage message) {
        GameNotificator notificator = (GameNotificator) Client.getInstance().getNotificator(GameView.class);
        notificator.finished();
    }

}
