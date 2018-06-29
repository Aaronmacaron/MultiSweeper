package tk.aakado.multisweeper.client.views.finished;

import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.views.ViewEnteredListener;

public class FinishedView implements FxmlView<FinishedViewModel>, Initializable, ViewEnteredListener {

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
        totalPlayersLabel.textProperty().bind(viewModel.totalPlayersProperty().asString());
        timeLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            Duration duration = viewModel.totalTimeProperty().get();
            return String.format("%s:%s", duration.toMinutes(), duration.getSeconds());
        }));

        // Hide the restart and reconfigure Button if player isn't admin
        reconfigureButton.visibleProperty().bind(Client.getInstance().getGameProperties().adminProperty());
        startNewButton.visibleProperty().bind(Client.getInstance().getGameProperties().adminProperty());
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
    public void onLeave(ActionEvent actionEvent) {
        viewModel.leaveGame();
    }

    /**
     * Invoked every time the view is shown to the users.
     */
    @Override
    public void viewEntered() {
        // check which text should be shown when entering the view
        if (Client.getInstance().getGameProperties().isVictory()) {
            this.victoryLabel.textProperty().setValue("You Won!");
        } else {
            this.victoryLabel.textProperty().setValue("You Lost!");
        }
    }
}
