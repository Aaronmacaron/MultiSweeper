package tk.aakado.multisweeper.client.views.game;

import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class GameView implements FxmlView<GameViewModel>, Initializable {

    @InjectViewModel
    private GameViewModel viewModel;

    @FXML
    public AnchorPane gamePane;
    @FXML
    private Label timeElapsedLabel;
    @FXML
    private Label numberOfPlayersLabel;
    @FXML
    private Label remainingMinesLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Convert Duration to String and bind it to timeElapsedLabel
        timeElapsedLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            Duration duration = viewModel.elapsedTimeProperty().get();
            return String.format("%s:%s", duration.toMinutes(), duration.getSeconds());
        }));

        // Convert Int to String and bind it to numberOfPlayersLabel
        numberOfPlayersLabel.textProperty().bind(Bindings.createStringBinding(() ->
                Integer.toString(viewModel.numberOfPlayersProperty().get())));

        // Convert Int to String and bind it to remainingMinesLabel
        remainingMinesLabel.textProperty().bind(Bindings.createStringBinding(() ->
                Integer.toString(viewModel.remainingMinesProperty().get())));

        // Adds the GridPane of the MineSweeper Game
        // Now called GamePane
        gamePane.getChildren().add(createGamePane());
        // Sets a useable size for the GamePane
        gamePane.setPrefSize(100, 100);

    }

    /**
     * Creates the GridPane for the MineSweeper game
     *
     * @return The MineSweeper GridPane
     */
    private GridPane createGamePane() {
        //TODO implement
        return new GridPane();
    }

    /**
     * Handle restart button
     *
     * @param actionEvent ActionEvent
     */
    @FXML
    public void onRestart(ActionEvent actionEvent) {
        viewModel.restart();
    }

    /**
     * Handle disconnect button
     *
     * @param actionEvent ActionEvent
     */
    @FXML
    public void onDisconnect(ActionEvent actionEvent) {
        viewModel.disconnect();
    }
}
