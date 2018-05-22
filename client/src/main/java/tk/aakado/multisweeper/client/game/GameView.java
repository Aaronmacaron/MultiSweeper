package tk.aakado.multisweeper.client.game;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;

public class GameView implements FxmlView<GameViewModel>, Initializable {
    @InjectViewModel
    private GameViewModel viewModel;

    @FXML
    private Label timeElapsed;
    @FXML
    private Label numberOfPlayers;
    @FXML
    private Label remainingMines;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Convert Duration to String and bind it to timeElapsed
        timeElapsed.textProperty().bind(Bindings.createStringBinding(() -> {
            Duration duration = viewModel.elapsedTimeProperty().get();
            return String.format("%s:%s", duration.toMinutes(), duration.getSeconds());
        }));

        // Convert Int to String and bind it to numberOfPlayers
        numberOfPlayers.textProperty().bind(Bindings.createStringBinding(() ->
                Integer.toString(viewModel.numberOfPlayersProperty().get())));

        // Convert Int to String and bind it to remainingMines
        remainingMines.textProperty().bind(Bindings.createStringBinding(() ->
                Integer.toString(viewModel.remainingMinesProperty().get())));

    }

    // Click handler for onRestart-button
    public void onRestart(ActionEvent actionEvent) {
        viewModel.restart();
    }

    // Click handler for onDisconnect-button
    public void onDisconnect(ActionEvent actionEvent) {
        viewModel.disconnect();
    }
}
