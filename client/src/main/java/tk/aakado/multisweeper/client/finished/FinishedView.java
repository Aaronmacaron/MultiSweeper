package tk.aakado.multisweeper.client.finished;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;

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
    private Button newGameButton;
    @FXML
    private Button reconfigureButton;
    @FXML
    private Button disconnectButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Labels
        victoryLabel.textProperty().bind(viewModel.victoryStringProperty());
        totalPlayersLabel.textProperty().bind(viewModel.totalPlayersProperty().asString());
        timeLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            Duration duration = viewModel.totalTimeProperty().get();
            return String.format("%s:%s", duration.toMinutes(), duration.getSeconds());
        }));

        // Buttons
        newGameButton.setOnAction(e -> viewModel.startNewGame());
        reconfigureButton.setOnAction(e -> viewModel.reconfigure());
        disconnectButton.setOnAction(e -> viewModel.disconnect());
    }

}
