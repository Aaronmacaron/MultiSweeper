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
        //TODO: variante 1
        //hier wird ein bidirectional binding verwendet, da das ViewModel den eingegebenen String verwenden muss
        connectionString.textProperty().bindBidirectional(viewModel.connectionStringProperty());
        //TODO: variante 2
        //kein binding
    }

    @FXML
    public void connect(ActionEvent actionEvent) {
        //TODO: variante 1
        viewModel.connect();
        //TODO: variante 2
        viewModel.connect(connectionString.getText());
    }
}
