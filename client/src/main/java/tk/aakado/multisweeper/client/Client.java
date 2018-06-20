package tk.aakado.multisweeper.client;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tk.aakado.multisweeper.client.connection.Transmitter;
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

import java.util.HashMap;
import java.util.Map;

/**
 * The client Application
 */
public class Client extends Application {

    private Transmitter transmitter;
    private Map<Class, MultiSweeperView> views = new HashMap<>();
    private Stage stage;
    private static Client instance;

    /**
     * Constructor.
     * Checks that this singleton is only instantiated once.
     */
    public Client() {
        synchronized (Client.class) {
            if (instance != null) {
                throw new IllegalStateException("Constructor must not be called more than once. Use Client.getInstance().");
            }
            instance = this;
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        this.stage = primaryStage;

        // create all views
        MultiSweeperView<ConnectionView, ConnectionViewModel, ConnectionNotificator> connectionView = new MultiSweeperView<>(ConnectionView.class);
        MultiSweeperView<GameSelectionView, GameSelectionViewModel, GameSelectionNotificator> gameSelectionView = new MultiSweeperView<>(GameSelectionView.class);
        MultiSweeperView<AuthenticationView, AuthenticationViewModel, AuthenticationNotificator> authenticationView = new MultiSweeperView<>(AuthenticationView.class);
        MultiSweeperView<ConfigurationView, ConfigurationViewModel, ConfigurationNotificator> configurationView = new MultiSweeperView<>(ConfigurationView.class);
        MultiSweeperView<GameView, GameViewModel, GameNotificator> gameView = new MultiSweeperView<>(GameView.class);
        MultiSweeperView<FinishedView, FinishedViewModel, FinishedNotificator> finishedView = new MultiSweeperView<>(FinishedView.class);

        // cache all views
        views.put(ConnectionView.class, connectionView);
        views.put(GameSelectionView.class, gameSelectionView);
        views.put(AuthenticationView.class, authenticationView);
        views.put(ConfigurationView.class, configurationView);
        views.put(GameView.class, gameView);
        views.put(FinishedView.class, finishedView);

        changeView(ConnectionView.class);
        primaryStage.show();
    }

    public static Client getInstance() {
        return instance;
    }

    public Transmitter getTransmitter() {
        return transmitter;
    }

    public void setTransmitter(Transmitter transmitter) {
        this.transmitter = transmitter;
    }

    /**
     * Change to the given view
     *
     * @param viewType The Class Object of the requested View Class
     */
    public void changeView(Class viewType) {
        // check if the requested view exists
        if (this.views.entrySet().stream().noneMatch(entry -> entry.getKey().equals(viewType))) {
            throw new IllegalArgumentException("The requested view: " + viewType.getName() + " does not exists.");
        }

        // set the new view
        Parent parent = this.views.get(viewType).getView();
        Scene scene = new Scene(parent);
        this.stage.setScene(scene);
    }

}
