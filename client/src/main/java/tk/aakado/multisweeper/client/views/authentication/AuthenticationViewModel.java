package tk.aakado.multisweeper.client.views.authentication;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.views.configuration.ConfigurationView;
import tk.aakado.multisweeper.client.views.gameselection.GameSelectionView;

public class AuthenticationViewModel implements ViewModel, AuthenticationNotificator {

    private StringProperty password = new SimpleStringProperty("");
    private BooleanProperty rejected = new SimpleBooleanProperty(false);
    private int gameId;

    /**
     * Is called when user submits password
     */
    public void submit() {
        Client.getInstance().getTransmitter().authenticate(this.gameId, password.get());
    }

    public void cancel() {
        Client.getInstance().changeView(GameSelectionView.class);
    }

    @Override
    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public void rejected() {
        rejected.setValue(true);
    }

    @Override
    public void success() {
        Client.getInstance().changeView(ConfigurationView.class);
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public BooleanProperty rejectedProperty() {
        return rejected;
    }
}
