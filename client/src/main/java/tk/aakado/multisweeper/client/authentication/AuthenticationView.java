package tk.aakado.multisweeper.client.authentication;

import java.net.URL;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.internal.viewloader.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;

public class AuthenticationView implements View<AuthenticationViewModel>, Initializable {
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
