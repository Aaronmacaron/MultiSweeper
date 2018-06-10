package tk.aakado.multisweeper.client;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tk.aakado.multisweeper.client.views.authentication.AuthenticationView;
import tk.aakado.multisweeper.client.views.authentication.AuthenticationViewModel;
import tk.aakado.multisweeper.client.views.configuration.ConfigurationView;
import tk.aakado.multisweeper.client.views.configuration.ConfigurationViewModel;
import tk.aakado.multisweeper.client.views.connection.ConnectionView;
import tk.aakado.multisweeper.client.views.connection.ConnectionViewModel;
import tk.aakado.multisweeper.client.views.finished.FinishedView;
import tk.aakado.multisweeper.client.views.finished.FinishedViewModel;
import tk.aakado.multisweeper.client.views.game.GameView;
import tk.aakado.multisweeper.client.views.game.GameViewModel;

/**
 * The client Application
 */
public class App extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ViewTuple<ConnectionView, ConnectionViewModel> tuple1 = FluentViewLoader.fxmlView(ConnectionView.class).load();
        ViewTuple<AuthenticationView, AuthenticationViewModel> tuple2 = FluentViewLoader.fxmlView(AuthenticationView.class).load();
        ViewTuple<ConfigurationView, ConfigurationViewModel> tuple3 = FluentViewLoader.fxmlView(ConfigurationView.class).load();
        ViewTuple<GameView, GameViewModel> tuple4 = FluentViewLoader.fxmlView(GameView.class).load();
        ViewTuple<FinishedView, FinishedViewModel> tuple5 = FluentViewLoader.fxmlView(FinishedView.class).load();



        Parent root = tuple1.getView();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
