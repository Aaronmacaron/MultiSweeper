package tk.aakado.multisweeper.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.stage.Stage;
import tk.aakado.multisweeper.client.connection.Transmitter;
import tk.aakado.multisweeper.client.connection.handler.ConfigurationHandler;
import tk.aakado.multisweeper.client.connection.handler.ConnectedHandler;
import tk.aakado.multisweeper.client.connection.handler.GameSelectionHandler;
import tk.aakado.multisweeper.client.connection.handler.*;
import tk.aakado.multisweeper.client.views.GameProperties;
import tk.aakado.multisweeper.client.views.MultiSweeperView;
import tk.aakado.multisweeper.client.views.Notificator;
import tk.aakado.multisweeper.client.views.ViewEnteredListener;
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
import tk.aakado.multisweeper.shared.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The client Application of MultiSweeper.
 * This class is a singleton. It holds the {@link Transmitter}, Views and {@link GameProperties}.
 *
 * @author Aaron Ebnöther
 * @author Dominik Strässle
 * @author Kay Mattern
 */
public class Client extends Application {

    private Transmitter transmitter;
    private Map<Class, MultiSweeperView> views = new HashMap<>();
    private Stage stage;
    private static Client instance;
    private GameProperties gameProperties = new GameProperties();
    private MultiSweeperView activeView;

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

        // Disconnect on close
        primaryStage.setOnCloseRequest(this::closeApp);

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

        // show the connection view
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
        if (!this.views.containsKey(viewType)) {
            throw new IllegalArgumentException("The requested view: " + viewType.getName() + " does not exists.");
        }

        // set the new view
        activeView = this.views.get(viewType);
        this.stage.setScene(activeView.getScene());

        // call the view entered listener if the view has one
        if (activeView.getView() instanceof ViewEnteredListener) {
            ((ViewEnteredListener) activeView.getView()).viewEntered();
        }
    }

    public GameProperties getGameProperties() {
        return gameProperties;
    }

    /**
     * Returns all actionHandlers
     */
    public List<Class> getAllActionHandlers() {
        return Arrays.asList(
                ConnectedHandler.class,
                ClickedHandler.class,
                GameHandler.class,
                GameSelectionHandler.class,
                ConfigurationHandler.class
        );
    }

    /**
     * Returns the currently active view.
     * @return The active view.
     */
    public MultiSweeperView getActiveView() {
        return this.activeView;
    }

    /**
     * Returns the currently active view if the given viewType matches with the active one. Else the optional is empty.
     * @return Optional of activeView
     */
    public MultiSweeperView getActiveView(Class viewType) {
        if (viewType.equals(activeView.getCodeBehind().getClass())) {
            return activeView;
        }
        throw new IllegalStateException(String.format(
                "The view of type %s cannot be accessed right now since the active view has the type %s",
                viewType.getName(),
                activeView.getCodeBehind().getClass().getName())
        );
    }

    /**
     * Returns the notificator of the given view.
     * @param viewType The view class.
     * @return The notificator of the view.
     */
    public Notificator getNotificator(Class viewType) {
        if (!this.views.containsKey(viewType)) {
            throw new IllegalArgumentException("The view " + viewType.getSimpleName() + " is not existent.");
        }
        return this.views.get(viewType).getNotificator();
    }

    private void closeApp(Event event) {
        Logger.get(this).info("Received close request. Application is shutting down now.");
        if (transmitter != null) {
            Logger.get(this).info("Disconnecting form server.");
            transmitter.disconnect();
        }

        Logger.get(this).info("Shut down application");
        Platform.exit();
        System.exit(0);
    }

}
