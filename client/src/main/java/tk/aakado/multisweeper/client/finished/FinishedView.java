package tk.aakado.multisweeper.client.finished;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class FinishedView implements FxmlView<FinishedViewModel>, Initializable {

    @InjectViewModel
    private FinishedViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
