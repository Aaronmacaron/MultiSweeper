package tk.aakado.multisweeper.client.views.authentication;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.views.configuration.ConfigurationView;

public class AuthenticationViewModel implements ViewModel, AuthenticationNotificator {

    private StringProperty password = new SimpleStringProperty("");
    private BooleanProperty rejected = new SimpleBooleanProperty(false);


    /**
     * Is called when user submits password
     */
    //TODO: missing game id as parameter
    public void submit() {
//        Client.getInstance().getTransmitter().authenticate(password.get());
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
