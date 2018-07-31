package tk.aakado.multisweeper.client.views.configuration;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.converter.NumberStringConverter;
import tk.aakado.multisweeper.client.Client;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigurationView implements FxmlView<ConfigurationViewModel>, Initializable {
    @InjectViewModel
    private ConfigurationViewModel viewModel;

    @FXML
    private Pane mainContainer;
    @FXML
    private Pane configurationContainer;
    @FXML
    private Slider mineDensitySlider;
    @FXML
    private Label mineDensityLabel;
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

        // Show / hide configuration panel
        Client.getInstance().getGameProperties().adminProperty().addListener((observable, oldValue, isAdmin) -> this.toggleConfigurationPanel(isAdmin));
        toggleConfigurationPanel(Client.getInstance().getGameProperties().isAdmin());

        // Make all Configuration Buttons disabled when Player isn't an admin
        mineDensitySlider.disableProperty().bind(Client.getInstance().getGameProperties().adminProperty().not());
        startButton.disableProperty().bind(Client.getInstance().getGameProperties().adminProperty().not());
        saveButton.disableProperty().bind(Client.getInstance().getGameProperties().adminProperty().not());

        // Bind mineDensityLabelValue to Slider
        mineDensityLabel.textProperty().bind(Bindings.format("%.2f", mineDensitySlider.valueProperty()));
    }

    /**
     * Shows/hides the configuration panel whether the client is an admin or not.
     * @param isAdmin If the client is admin.
     */
    private void toggleConfigurationPanel(boolean isAdmin) {
        boolean isCurrentlyActive = this.mainContainer.getChildren().contains(this.configurationContainer);
        if (isAdmin && !isCurrentlyActive) {
            this.mainContainer.getChildren().add(0, this.configurationContainer);
        } else if (!isAdmin && isCurrentlyActive) {
            this.mainContainer.getChildren().remove(this.configurationContainer);
        }
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
        return Client.getInstance().isAdmin();
    }
}
