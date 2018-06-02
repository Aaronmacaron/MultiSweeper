package tk.aakado.multisweeper.client.views.configuration;

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
    private TextField mineDensityField;
    @FXML
    private TextField fieldWidthField;
    @FXML
    private TextField fieldHeightField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ListView<String> playersList;//TODO: List type should be gamelogic.Player

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Bind mineDensityField bidirectional with a NumberStringConverter
        Bindings.bindBidirectional(mineDensityField.textProperty(), viewModel.mineDensityProperty(), new NumberStringConverter());

        // Bind fieldWidthField bidirectional with a NumberStringConverter
        Bindings.bindBidirectional(fieldWidthField.textProperty(), viewModel.fieldWidthProperty(), new NumberStringConverter());

        // Bind fieldHeightField bidirectional with a NumberStringConverter
        Bindings.bindBidirectional(fieldHeightField.textProperty(), viewModel.fieldHeightProperty(), new NumberStringConverter());

        // Bind password string bidirectional
        passwordField.textProperty().bindBidirectional(viewModel.passwordProperty());

        // Bind players list unidirectional
        playersList.itemsProperty().bind(viewModel.playersProperty());

        //TODO: use the following to show the correct and formatted data
        //        playersList.setCellFactory();
    }

    /**
     * Handle disconnect button
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
