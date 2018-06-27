package tk.aakado.multisweeper.client.views.configuration;

import java.net.URL;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;

public class ConfigurationView implements FxmlView<ConfigurationViewModel>, Initializable {
    @InjectViewModel
    private ConfigurationViewModel viewModel;

    @FXML
    private Slider mineDensitySlider;

    @FXML
    private TextField fieldWidthField;

    @FXML
    private TextField fieldHeightField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ListView<String> playersList;

    @FXML
    private Button startButton;

    @FXML
    private Button saveButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Bind mineDensityField bidirectional
        mineDensitySlider.valueProperty().bindBidirectional(viewModel.mineDensityProperty());

        // Bind fieldWidthField bidirectional with a NumberStringConverter
        Bindings.bindBidirectional(fieldWidthField.textProperty(), viewModel.fieldWidthProperty(), new NumberStringConverter());

        // Bind fieldHeightField bidirectional with a NumberStringConverter
        Bindings.bindBidirectional(fieldHeightField.textProperty(), viewModel.fieldHeightProperty(), new NumberStringConverter());

        // Bind passwordstring bidirectional
        passwordField.textProperty().bindBidirectional(viewModel.passwordProperty());

        // Bind playerslist unidirectional
        playersList.itemsProperty().bind(viewModel.playersProperty());

        // Make all Configuration Properties not editable when Player isn't an admin
        passwordField.editableProperty().bind(viewModel.adminProperty());
        fieldHeightField.editableProperty().bind(viewModel.adminProperty());
        fieldWidthField.editableProperty().bind(viewModel.adminProperty());
        // Make all Configuration Buttons disabled when Player isn't an admin
        mineDensitySlider.disableProperty().bind(viewModel.adminProperty().not());
        startButton.disableProperty().bind(viewModel.adminProperty().not());
        saveButton.disableProperty().bind(viewModel.adminProperty().not());

    }

    /**
     * Hanlde leaveGame button
     *
     * @param event ActionEvent
     */
    @FXML
    void onLeave(ActionEvent event) {
        viewModel.leaveGame();
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
        if (correctParams()) viewModel.start();
    }

    /**
     * Checks if the given height and width are valid.
     *
     * @return true if valid
     */
    private boolean correctParams() {
        if (viewModel.fieldWidthProperty().get() == 0) return false;
        if (viewModel.fieldHeightProperty().get() == 0) return false;
        if (viewModel.fieldWidthProperty().get() == 0) return false;
        if (viewModel.fieldWidthProperty().get() == 0) return false;
        if (viewModel.mineDensityProperty().get() == 0d) return false;
        return viewModel.isAdmin();
    }
}
