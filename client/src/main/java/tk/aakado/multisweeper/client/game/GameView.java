package tk.aakado.multisweeper.client.game;

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
    public AnchorPane gameContainer;
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

        // Adds the GridPane of the MineSweeper Game
        // Now called GamePane
        gameContainer.getChildren().add(createGamePane());
        // Sets a useable size for the GamePane
        gameContainer.setPrefSize(100, 100);

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

    // Click handler for onRestart-button
    @FXML
    public void onRestart(ActionEvent actionEvent) {
        viewModel.restart();
    }

    // Click handler for onDisconnect-button
    @FXML
    public void onDisconnect(ActionEvent actionEvent) {
        viewModel.disconnect();
    }
}
