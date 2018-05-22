package tk.aakado.multisweeper.client.configuration;

import java.net.URL;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.internal.viewloader.View;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import tk.aakado.multisweeper.client.connection.ConnectionViewModel;

public class ConfigurationView implements View<ConfigurationViewModel>, Initializable {
    @InjectViewModel
    private ConnectionViewModel viewModel;

    @FXML
    private TextField mineDensityString;

    @FXML
    private TextField fieldWidthString;

    @FXML
    private TextField FieldHeightString;

    @FXML
    private PasswordField passwordString;

    @FXML
    private ListView<?> playersList;//TODO: List type should be gamelogic.Player, in the #initialize Method we can specify the data to show

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void disconnect(ActionEvent event) {

    }

    @FXML
    void save(ActionEvent event) {

    }

    @FXML
    void start(ActionEvent event) {

    }
}
