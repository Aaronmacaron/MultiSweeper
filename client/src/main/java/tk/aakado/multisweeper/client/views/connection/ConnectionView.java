package tk.aakado.multisweeper.client.views.connection;

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
    private TextField connectionField;
    @FXML
    private Label errorMessageLabel;
    @FXML
    private Button connectButton;

    /**
     * Regex that checks if string is hostname or ip address
     */
    private static final Pattern pattern = Pattern.compile("(^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.)" +
            "{3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])|^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-" +
            "9])\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9]))(:([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|" +
            "65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5]))?$");

    @InjectViewModel
    private ConnectionViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // bind connectionField bidirectional to viewModel
        connectionField.textProperty().bindBidirectional(viewModel.connectionProperty());

        // Only shows enabled Connect Button when the Connection String is correct
        viewModel.connectionProperty().addListener((observable, oldValue, newValue) -> viewModel.setCorrectAddress(isCorrectAddress(newValue)));
        connectButton.disableProperty().bind(viewModel.correctAddressProperty().not());

        // Shows error message when the connection failed
        errorMessageLabel.visibleProperty().bind(viewModel.rejectedProperty());
    }

    /**
     * Checks if the given String matches the Address pattern
     *
     * @param address Address to check
     * @return match or no match
     */
    private boolean isCorrectAddress(String address) {
        return pattern.matcher(address).matches();
    }

    /**
     * Handles connect button
     *
     * @param actionEvent event of click
     */
    @FXML
    public void onConnect(ActionEvent actionEvent) {
        viewModel.connect();
    }
}
