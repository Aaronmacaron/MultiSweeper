package tk.aakado.multisweeper.client;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tk.aakado.multisweeper.client.views.MultiSweeperView;
import tk.aakado.multisweeper.client.views.authentication.AuthenticationNotificator;
import tk.aakado.multisweeper.client.views.authentication.AuthenticationView;
import tk.aakado.multisweeper.client.views.authentication.AuthenticationViewModel;
import tk.aakado.multisweeper.client.views.configuration.ConfigurationNotificator;
import tk.aakado.multisweeper.client.views.configuration.ConfigurationView;
import tk.aakado.multisweeper.client.views.configuration.ConfigurationViewModel;
import tk.aakado.multisweeper.client.views.connection.ConnectionNotificator;
import tk.aakado.multisweeper.client.views.connection.ConnectionView;
import tk.aakado.multisweeper.client.views.connection.ConnectionViewModel;
import tk.aakado.multisweeper.client.views.finished.FinishedNotificator;
import tk.aakado.multisweeper.client.views.finished.FinishedView;
import tk.aakado.multisweeper.client.views.finished.FinishedViewModel;
import tk.aakado.multisweeper.client.views.game.GameNotificator;
import tk.aakado.multisweeper.client.views.game.GameView;
import tk.aakado.multisweeper.client.views.game.GameViewModel;
import tk.aakado.multisweeper.client.views.gameselection.GameSelectionNotificator;
import tk.aakado.multisweeper.client.views.gameselection.GameSelectionView;
import tk.aakado.multisweeper.client.views.gameselection.GameSelectionViewModel;

/**
 * The client Application
 */
public class App extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MultiSweeperView<ConnectionView, ConnectionViewModel, ConnectionNotificator> connectionView = new MultiSweeperView<>(ConnectionView.class);
        MultiSweeperView<GameSelectionView, GameSelectionViewModel, GameSelectionNotificator> gameSelectionView = new MultiSweeperView<>(GameSelectionView.class);
        MultiSweeperView<AuthenticationView, AuthenticationViewModel, AuthenticationNotificator> authenticationView = new MultiSweeperView<>(AuthenticationView.class);
        MultiSweeperView<ConfigurationView, ConfigurationViewModel, ConfigurationNotificator> configurationView = new MultiSweeperView<>(ConfigurationView.class);
        MultiSweeperView<GameView, GameViewModel, GameNotificator> gameView = new MultiSweeperView<>(GameView.class);
        MultiSweeperView<FinishedView, FinishedViewModel, FinishedNotificator> finishedView = new MultiSweeperView<>(FinishedView.class);


        Parent root = connectionView.getView();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
