package tk.aakado.multisweeper.client.connection;

import java.net.URL;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class ConnectionView implements FxmlView<ConnectionViewModel>, Initializable {

    @FXML
    public TextField connectionString;

    @InjectViewModel
    private ConnectionViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // bind connectionString bidirectional to viewModel
        connectionString.textProperty().bindBidirectional(viewModel.connectionStringProperty());
    }

    /**
     * Handles click on the onConnect button
     * @param actionEvent event of click
     */
    @FXML
    public void onConnect(ActionEvent actionEvent) {
        viewModel.connect();
    }
}
