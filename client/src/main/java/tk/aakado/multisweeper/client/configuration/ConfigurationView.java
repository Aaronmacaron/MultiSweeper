package tk.aakado.multisweeper.client.configuration;

import java.net.URL;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

public class ConfigurationView implements FxmlView<ConfigurationViewModel>, Initializable {
    @InjectViewModel
    private ConfigurationViewModel viewModel;

    @FXML
    private TextField mineDensity;

    @FXML
    private TextField fieldWidth;

    @FXML
    private TextField fieldHeight;

    @FXML
    private PasswordField passwordString;

    @FXML
    private ListView<String> playersList;//TODO: List type should be gamelogic.Player

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Bind mineDensity bidirectional with a NumberStringConverter
        Bindings.bindBidirectional(mineDensity.textProperty(), viewModel.mineDensitiyProperty(), new NumberStringConverter());

        // Bind fieldWidth bidirectional with a NumberStringConverter
        Bindings.bindBidirectional(fieldWidth.textProperty(), viewModel.fieldWidthProperty(), new NumberStringConverter());

        // Bind fieldHeight bidirectional with a NumberStringConverter
        Bindings.bindBidirectional(fieldHeight.textProperty(), viewModel.fieldHeightProperty(), new NumberStringConverter());

        // Bind passwordstring bidirectional
        passwordString.textProperty().bindBidirectional(viewModel.passwordStringProperty());

        // Bind playerslist unidirectional
        playersList.itemsProperty().bind(viewModel.playersListProperty());

        //TODO: use the following to show the correct and formatted data
        //        playersList.setCellFactory();
    }

    /**
     * Hanlde disconnect button
     *
     * @param event ActionEvent
     */
    @FXML
    void onDisconnect(ActionEvent event) {
        viewModel.disconnect();
    }

    /**
     * Handle save button
     *
     * @param event ActionEvent
     */
    @FXML
    void onSave(ActionEvent event) {
        viewModel.save();
    }

    /**
     * Handle start button
     *
     * @param event ActionEvent
     */
    @FXML
    void onStart(ActionEvent event) {
        viewModel.start();
    }
}
