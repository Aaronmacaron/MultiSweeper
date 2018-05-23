package tk.aakado.multisweeper.client.finished;

import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Labels
        victoryLabel.textProperty().bind(viewModel.victoryStringProperty());
        totalPlayersLabel.textProperty().bind(viewModel.totalPlayersProperty().asString());
        timeLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            //TODO: should we outsource large lambdas in own mehtods?
            Duration duration = viewModel.totalTimeProperty().get();
            return String.format("%s:%s", duration.toMinutes(), duration.getSeconds());
        }));

    }

    @FXML
    public void onStartNewGame(ActionEvent actionEvent) {
        viewModel.startNewGame();
    }

    @FXML
    public void onReonfigure(ActionEvent actionEvent) {
        viewModel.reconfigure();
    }

    @FXML
    public void onDisconnect(ActionEvent actionEvent) {
        viewModel.disconnect();
    }
}
