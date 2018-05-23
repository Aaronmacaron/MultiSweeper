package tk.aakado.multisweeper.client;

import javax.security.auth.login.Configuration;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tk.aakado.multisweeper.client.authentication.AuthenticationView;
import tk.aakado.multisweeper.client.authentication.AuthenticationViewModel;
import tk.aakado.multisweeper.client.configuration.ConfigurationView;
import tk.aakado.multisweeper.client.configuration.ConfigurationViewModel;
import tk.aakado.multisweeper.client.connection.ConnectionView;
import tk.aakado.multisweeper.client.connection.ConnectionViewModel;
import tk.aakado.multisweeper.client.finished.FinishedView;
import tk.aakado.multisweeper.client.finished.FinishedViewModel;
import tk.aakado.multisweeper.client.game.GameView;
import tk.aakado.multisweeper.client.game.GameViewModel;

/**
 * The client Application
 */
public class App extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
//        ViewTuple<ConnectionView, ConnectionViewModel> tuple = FluentViewLoader.fxmlView(ConnectionView.class).load();
//        ViewTuple<AuthenticationView, AuthenticationViewModel> tuple = FluentViewLoader.fxmlView(AuthenticationView.class).load();
//        ViewTuple<ConfigurationView, ConfigurationViewModel> tuple = FluentViewLoader.fxmlView(ConfigurationView.class).load();
//        ViewTuple<GameView, GameViewModel> tuple = FluentViewLoader.fxmlView(GameView.class).load();
        ViewTuple<FinishedView, FinishedViewModel> tuple = FluentViewLoader.fxmlView(FinishedView.class).load();



        Parent root = tuple.getView();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
