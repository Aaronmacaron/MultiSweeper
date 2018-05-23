package tk.aakado.multisweeper.client.authentication;

import java.net.URL;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;

public class AuthenticationView implements FxmlView<AuthenticationViewModel>, Initializable {

    @InjectViewModel
    private AuthenticationViewModel viewModel;

    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Bind passwordField bidirectional
        passwordField.textProperty().bindBidirectional(viewModel.passwordProperty());
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
