package tk.aakado.multisweeper.client.game;

import java.net.URL;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.internal.viewloader.View;
import javafx.fxml.Initializable;

public class GameView implements View<GameViewModel>, Initializable {
    @InjectViewModel
    private GameViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
