package tk.aakado.multisweeper.client.views.gameselection;

import java.net.URL;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.views.connection.ConnectionView;

/**
 * View where the player can choose a game to join.
 */
public class GameSelectionView implements FxmlView<GameSelectionViewModel>, Initializable {

    @InjectViewModel
    private GameSelectionViewModel viewModel;

    @FXML
    private ListView<String> gamesListView;
    @FXML
    private Button joinButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // gamesListView content
        this.viewModel.gameIdsProperty().addListener((observable, oldValue, newValue) -> newValue.forEach(
                gameId -> this.gamesListView.getItems().add("Game " + gameId)));

        // join button
        this.joinButton
                .disableProperty()
                .bind(this.gamesListView
                        .getSelectionModel()
                        .selectedItemProperty()
                        .isNull());
    }

    /**
     * Handle cancel button.
     */
    @FXML
    private void onCancel() {
        viewModel.cancel();
    }

    /**
     * Handle join button.
     */
    @FXML
    private void onJoin() {
        String selectedGame = this.gamesListView.getSelectionModel().getSelectedItem();
        if (selectedGame != null) {
            int gameId = Integer.parseInt(selectedGame.substring(5));
            viewModel.join(gameId);
        }
    }
}
