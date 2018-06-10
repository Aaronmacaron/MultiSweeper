package tk.aakado.multisweeper.client.views.authentication;

import java.net.URL;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class AuthenticationView implements FxmlView<AuthenticationViewModel>, Initializable {

    @InjectViewModel
    private AuthenticationViewModel viewModel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label failedLabel;

    @FXML
    private Button submitButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Bind passwordField bidirectional
        passwordField.textProperty().bindBidirectional(viewModel.passwordProperty());

        // Only set the Error Message visible, when the authentication failed
        failedLabel.visibleProperty().bind(viewModel.rejectedProperty());

        // Disable the submit button when no password is entered
        submitButton.disableProperty().bind(passwordField.textProperty().isEmpty());
    }

    /**
     * Handle submit button
     *
     * @param event ActionEvent
     */
    @FXML
    void onSubmit(ActionEvent event) {
        viewModel.submit();
    }
}
