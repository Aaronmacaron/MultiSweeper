package tk.aakado.multisweeper.client.views.gameselection;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

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
        this.gamesListView.getSelectionModel().selectedItemProperty()
                .addListener(((observable, oldValue, newValue) -> this.joinButton.setDisable(newValue == null)));
    }

    @FXML
    private void onCancel() {
        // TODO: Go back to server selection
    }

    @FXML
    private void onJoin() {
        String selectedGame = this.gamesListView.getSelectionModel().getSelectedItem();
        if (selectedGame != null) {
            int gameId = Integer.parseInt(selectedGame.substring(5));
            // TODO: Transmitter call
        }
    }
}
