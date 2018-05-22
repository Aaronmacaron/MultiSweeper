package tk.aakado.multisweeper.client.connection;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ConnectionView implements FxmlView<ConnectionViewModel>, Initializable {

    @FXML
    public TextField connectionString;
    @FXML
    public Label errorMessage;
    @FXML
    public Button connectButton;


    @InjectViewModel
    private ConnectionViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // bind connectionString bidirectional to viewModel
        connectionString.textProperty().bindBidirectional(viewModel.connectionStringProperty());

        // Set the disabled state of the connectButton to connectionString.isEmpty
        connectButton.disableProperty().bind(connectionString.textProperty().isEmpty());
    }

    /**
     * Handles click on the onConnect button
     * @param actionEvent event of click
     */
    @FXML
    public void onConnect(ActionEvent actionEvent) {
        // Regex that checks if string is hostname or ip address
        Pattern pattern = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9" +
                "]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$|^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-" +
                "z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$");

        // Validate
        if (pattern.matcher(connectionString.textProperty().getValue()).matches()) {
            errorMessage.setVisible(true);
        }

        viewModel.connect();
    }
}
