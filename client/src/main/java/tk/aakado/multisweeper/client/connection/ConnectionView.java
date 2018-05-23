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
    public TextField connectionField;
    @FXML
    public Label errorMessageLabel;
    @FXML
    public Button connectButton;


    @InjectViewModel
    private ConnectionViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // bind connectionField bidirectional to viewModel
        connectionField.textProperty().bindBidirectional(viewModel.connectionProperty());

        // Set the disabled state of the connectButton to connectionField.isEmpty
        connectButton.disableProperty().bind(connectionField.textProperty().isEmpty());
        //TODO: the disableProperty could also be bind with a valdate method, which checks the connectionField with a Pattern
    }

    /**
     * Handles connect button
     *
     * @param actionEvent event of click
     */
    @FXML
    public void onConnect(ActionEvent actionEvent) {
        // Regex that checks if string is hostname or ip address
        Pattern pattern = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9" +
                "]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$|^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-" +
                "z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])$");

        // Validate
        if (pattern.matcher(connectionField.textProperty().getValue()).matches()) {
            errorMessageLabel.setVisible(true);
        } else {
            viewModel.connect();
        }

    }
}
