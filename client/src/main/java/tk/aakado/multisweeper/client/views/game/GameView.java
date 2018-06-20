package tk.aakado.multisweeper.client.views.game;

import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import tk.aakado.multisweeper.client.views.game.model.Field;
import tk.aakado.multisweeper.client.views.game.view.FieldButton;
import tk.aakado.multisweeper.client.views.game.view.FieldGrid;

public class GameView implements FxmlView<GameViewModel>, Initializable {

    @InjectViewModel
    private GameViewModel viewModel;

    @FXML
    public AnchorPane gamePane;
    @FXML
    private Label timeElapsedLabel;
    @FXML
    private Label numberOfPlayersLabel;
    @FXML
    private Label remainingMinesLabel;
    @FXML
    private Button restartButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Convert Duration to String and bind it to timeElapsedLabel
        timeElapsedLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            Duration duration = viewModel.elapsedTimeProperty().get();
            return String.format("%s:%s", duration.toMinutes(), duration.getSeconds());
        }));

        // Convert Int to String and bind it to numberOfPlayersLabel
        numberOfPlayersLabel.textProperty().bind(Bindings.createStringBinding(() ->
                Integer.toString(viewModel.numberOfPlayersProperty().get())));

        // Convert Int to String and bind it to remainingMinesLabel
        remainingMinesLabel.textProperty().bind(Bindings.createStringBinding(() ->
                Integer.toString(viewModel.remainingMinesProperty().get())));

        // Creates a new FieldGrid and add it to the AchnorPane
        gamePane.getChildren().add(new FieldGrid(viewModel.fieldsProperty(), viewModel.fieldWidthProperty(), viewModel.fieldHeightProperty(), this::onClick));

        // Hide restart Button if player isn't the admin
        restartButton.visibleProperty().bind(viewModel.adminProperty());
    }

    /**
     * Handles the Click on a Field.
     * Set a method reference to this method on the FieldButton.setOnAction method
     *
     * @param mouseEvent Click on a Field
     */
    private void onClick(MouseEvent mouseEvent) {
        // check if the source is a @FieldButton
        if (!(mouseEvent.getSource() instanceof FieldButton)) {
            throw new IllegalStateException("This method should only be called by a FieldButton");
        }
        // get the source
        FieldButton fieldButton = (FieldButton) mouseEvent.getSource();

        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) { // If left click
            // delegate the leftClick to the @GameViewModel
            viewModel.leftClick(fieldButton.getX(), fieldButton.getY());
        }

        if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) { // If right click
            // delegate the rightClick to the @GameViewModel
            viewModel.rightClick(fieldButton.getX(), fieldButton.getY());
        }

    }

    /**
     * Handle restart button
     *
     * @param actionEvent ActionEvent
     */
    @FXML
    public void onRestart(ActionEvent actionEvent) {
        viewModel.sendRestart();
    }

    /**
     * Handle disconnect button
     *
     * @param actionEvent ActionEvent
     */
    @FXML
    public void onDisconnect(ActionEvent actionEvent) {
        viewModel.disconnect();
    }
}
