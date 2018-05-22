package tk.aakado.multisweeper.client.authentication;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthenticationView implements FxmlView<AuthenticationViewModel>, Initializable {
    @InjectViewModel
    private AuthenticationViewModel viewModel;

    @FXML
    private PasswordField passwordString;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void submit(ActionEvent event) {

    }
}
