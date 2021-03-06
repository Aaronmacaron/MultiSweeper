package tk.aakado.multisweeper.client.views.game;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.LocalTimeStringConverter;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.views.game.view.FieldButton;
import tk.aakado.multisweeper.client.views.game.view.FieldGrid;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class GameView implements FxmlView<GameViewModel>, Initializable {

    @InjectViewModel
    private GameViewModel viewModel;

    @FXML
    public ScrollPane gamePane;
    @FXML
    private Label timeElapsedLabel;
    @FXML
    private Label numberOfPlayersLabel;
    @FXML
    private Label remainingMinesLabel;
    @FXML
    private Button restartButton;
    @FXML
    private Button continueButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Convert LocalTime to String and bind it to timeElapsedLabel
        LocalTimeStringConverter timeConverter = new LocalTimeStringConverter(DateTimeFormatter.ofPattern("mm:ss"), null);
        Bindings.bindBidirectional(this.timeElapsedLabel.textProperty(), this.viewModel.elapsedTimeProperty(), timeConverter);

        // Bind number of players
        this.numberOfPlayersLabel.textProperty().bind(this.viewModel.numberOfPlayersProperty().asString());

        // Bind remaining mines
        this.remainingMinesLabel.textProperty().bind(this.viewModel.remainingMinesProperty().asString());

        // Creates a new FieldGrid and add it to the AchnorPane
        gamePane.setContent(new FieldGrid(viewModel.fieldsProperty(), viewModel.fieldWidthProperty(), viewModel.fieldHeightProperty(), this::onClick));

        // Hide restart Button if player isn't the admin
        restartButton.visibleProperty().bind(Client.getInstance().getGameProperties().adminProperty());

        // disable the field, after the game finished
        gamePane.disableProperty().bind(viewModel.isFinishedProperty());
        // make the continue button visible after the game finished
        continueButton.visibleProperty().bind(viewModel.isFinishedProperty());
    }

    /**
     * The player want to see the finished view
     * @param actionEvent
     */
    @FXML
    private void onContinue(ActionEvent actionEvent){
        viewModel.onContinue();
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
