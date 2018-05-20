package tk.aakado.multisweeper.client;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tk.aakado.multisweeper.client.connection.ConnectionView;
import tk.aakado.multisweeper.client.connection.ConnectionViewModel;

/**
 * The client Application
 */
public class App extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ViewTuple<ConnectionView, ConnectionViewModel> viewTuple = FluentViewLoader.fxmlView(ConnectionView.class).load();

        Parent root = viewTuple.getView();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
