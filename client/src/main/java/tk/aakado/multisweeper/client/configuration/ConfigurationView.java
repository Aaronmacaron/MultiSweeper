package tk.aakado.multisweeper.client.configuration;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigurationView implements FxmlView<ConfigurationViewModel>, Initializable {
    @InjectViewModel
    private ConfigurationViewModel viewModel;

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
