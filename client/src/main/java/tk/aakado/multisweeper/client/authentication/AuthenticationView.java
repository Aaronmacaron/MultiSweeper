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
    private PasswordField passwordString;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Bind passwordString bidirectional
        passwordString.textProperty().bindBidirectional(viewModel.passwordStringProperty());
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
