package tk.aakado.multisweeper.client.views.finished;

import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FinishedView implements FxmlView<FinishedViewModel>, Initializable {

    @InjectViewModel
    private FinishedViewModel viewModel;

    @FXML
    private Label victoryLabel;
    @FXML
    private Label totalPlayersLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Button startNewButton;

    @FXML
    private Button reconfigureButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Labels
        viewModel.victoryProperty().addListener(this::onVictoryChanged);
        totalPlayersLabel.textProperty().bind(viewModel.totalPlayersProperty().asString());
        timeLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            //TODO: should we outsource large lambdas in own mehtods?
            Duration duration = viewModel.totalTimeProperty().get();
            return String.format("%s:%s", duration.toMinutes(), duration.getSeconds());
        }));


        // Hide the restart and reconfigure Button if player isn't admin
        reconfigureButton.visibleProperty().bind(viewModel.adminProperty());
        startNewButton.visibleProperty().bind(viewModel.adminProperty());
    }

    private void onVictoryChanged(ObservableValue<? extends Boolean> observable, boolean oldValue, boolean newValue) {
        String victoryText = newValue
                ? "You won!"
                : "You loose!";

        victoryLabel.setText(victoryText);
    }

    @FXML
    public void onStartNewGame(ActionEvent actionEvent) {
        viewModel.startNewGame();
    }

    @FXML
    public void onReconfigure(ActionEvent actionEvent) {
        viewModel.sendReconfigure();
    }

    @FXML
    public void onDisconnect(ActionEvent actionEvent) {
        viewModel.disconnect();
    }
}
