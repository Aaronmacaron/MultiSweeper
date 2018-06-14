package tk.aakado.multisweeper.client.views.game;

import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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

        // Sets a useable size for the GamePane
//        gamePane.setPrefSize(100, 100);

    }

    /**
     * Handles the click on a Field.
     * Set a method reference to this method on the FieldButton.setOnAction method
     *
     * @param actionEvent Click on a Field
     */
    private void onClick(ActionEvent actionEvent) {
        // check if the source is a @FieldButton
        if (!(actionEvent.getSource() instanceof FieldButton)) {
            throw new IllegalStateException("This method should only be called by a FieldButton");
        }
        // get the source
        FieldButton fieldButton = (FieldButton) actionEvent.getSource();

        // delegate the click to the @GameViewModel
        viewModel.click(fieldButton.getX(), fieldButton.getY());
    }

    /**
     * Handle restart button
     *
     * @param actionEvent ActionEvent
     */
    @FXML
    public void onRestart(ActionEvent actionEvent) {
        // TODO: implement
        // for test reasons a random field is generated
        int x = ThreadLocalRandom.current().nextInt(3, 20);
        int y = ThreadLocalRandom.current().nextInt(3, 20);
        viewModel.restart(x, x);
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

    public void click(Field field) {
        //TODO: Implement
    }
}
