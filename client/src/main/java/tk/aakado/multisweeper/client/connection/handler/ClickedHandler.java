package tk.aakado.multisweeper.client.connection.handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.connection.ClientMessage;
import tk.aakado.multisweeper.client.views.game.GameNotificator;
import tk.aakado.multisweeper.client.views.game.GameView;
import tk.aakado.multisweeper.shared.connection.ActionHandler;
import tk.aakado.multisweeper.shared.connection.ActionType;
import tk.aakado.multisweeper.shared.connection.dtos.FieldDTO;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Handles the UI updates after a click has successfully been made.
 */
public class ClickedHandler {

    @ActionHandler(actionType = ActionType.CLICKED)
    public void onClicked(ClientMessage message) {
        Type setType = new TypeToken<List<FieldDTO>>(){}.getType(); // GSON hack to get type of generic
        List<FieldDTO> changedFields = new Gson().fromJson(message.getParams(), setType);

        Platform.runLater(() -> updateFields(changedFields));
    }

    /**
     * Update the changed fields.
     * Has to be executed on JavaFX Application thread.
     * @param changedFields The fields that have changed.
     */
    private void updateFields(List<FieldDTO> changedFields) {
        GameNotificator notificator = (GameNotificator) Client.getInstance().getNotificator(GameView.class);
        changedFields.forEach(field -> notificator.updateField(field.getX(), field.getY(), field.getState(), field.getValue()));
    }

}
