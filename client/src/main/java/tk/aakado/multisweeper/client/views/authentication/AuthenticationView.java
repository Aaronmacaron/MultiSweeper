package tk.aakado.multisweeper.client.views.authentication;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import tk.aakado.multisweeper.client.views.ViewEnteredListener;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthenticationView implements FxmlView<AuthenticationViewModel>, Initializable, ViewEnteredListener {

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

    @Override
    public void viewEntered() {
        this.passwordField.clear();
    }

    /**
     * Handle submit button
     *
     * @param event ActionEvent
     */
    @FXML
    private void onSubmit(ActionEvent event) {
        viewModel.submit();
    }

    @FXML
    private void onCancel() {
        viewModel.cancel();
    }
}
