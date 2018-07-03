package tk.aakado.multisweeper.client.views.connection;

import java.net.URL;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import tk.aakado.multisweeper.client.views.validation.ControlDecorator;
import tk.aakado.multisweeper.client.views.validation.ControlDecoratorImpl;

public class ConnectionView implements FxmlView<ConnectionViewModel>, Initializable {

    @FXML
    private TextField connectionField;
    @FXML
    private Label errorMessageLabel;
    @FXML
    private Button connectButton;

    private ControlDecorator decorator;

    @InjectViewModel
    private ConnectionViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // bind connectionField bidirectional to viewModel
        connectionField.textProperty().bindBidirectional(viewModel.connectionProperty());

        // Shows error message when the connection failed
        errorMessageLabel.visibleProperty().bind(viewModel.rejectedProperty());

//        ValidationVisualizer visualizer = new ControlsFxVisualizer();
//        visualizer.initVisualization(viewModel.addressValidation(), connectionField, true);

        // Only shows enabled Connect Button when the Connection String is correct
        connectButton.disableProperty().bind(viewModel.addressValidation().validProperty().not());

        // decorates the
        decorator = new ControlDecoratorImpl();
        decorator.setDecorationClasses(connectionField.getClass(), "text-validation");
        decorator.initDecoration(connectionField, viewModel.addressValidation().validProperty());
    }

    /**
     * Handles connect button
     *
     * @param actionEvent event of click
     */
    @FXML
    public void onConnect(ActionEvent actionEvent) {
        viewModel.connect();
    }
}
