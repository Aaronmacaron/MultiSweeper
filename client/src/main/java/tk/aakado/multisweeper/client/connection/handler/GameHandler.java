package tk.aakado.multisweeper.client.connection.handler;

import com.google.gson.Gson;

import de.saxsys.mvvmfx.ViewModel;
import javafx.application.Platform;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.connection.ClientMessage;
import tk.aakado.multisweeper.client.views.Notificator;
import tk.aakado.multisweeper.client.views.finished.FinishedNotificator;
import tk.aakado.multisweeper.client.views.finished.FinishedView;
import tk.aakado.multisweeper.client.views.finished.FinishedViewModel;
import tk.aakado.multisweeper.client.views.game.GameNotificator;
import tk.aakado.multisweeper.client.views.game.GameView;
import tk.aakado.multisweeper.client.views.game.GameViewModel;
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
        // change to the game view

//        Client.getInstance().changeView(GameView.class);

//        if (Client.getInstance().getActiveView().getViewModel().getClass().equals(GameViewModel.class)){
//            Client.getInstance().changeView(GameView.class);
//        }
//        if (Client.getInstance().getActiveView().getViewModel().getClass().equals(FinishedViewModel.class)){
//            Client.getInstance().changeView(GameView.class);
//        }
    }


//    @ActionHandler(actionType = ActionType.GAME_STARTED)
//    public void onRestartChangeView(){
//        Client.getInstance().changeView(GameView.class);
//    }
    @ActionHandler(actionType = ActionType.GAME_FINISHED)
    public void onGameFinished(ClientMessage message) {
        GameNotificator notificator = (GameNotificator) Client.getInstance().getNotificator(GameView.class);
        Platform.runLater(notificator::finished);
    }

}
