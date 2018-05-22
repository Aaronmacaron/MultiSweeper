package tk.aakado.multisweeper.client.finished;

import java.net.URL;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.internal.viewloader.View;
import javafx.fxml.Initializable;

public class FinishedView implements View<FinishedViewModel>, Initializable {
    @InjectViewModel
    private FinishedViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
