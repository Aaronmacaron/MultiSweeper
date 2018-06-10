package tk.aakado.multisweeper.client.views.authentication;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AuthenticationViewModel implements ViewModel, AuthenticationNotificator {

    private StringProperty password = new SimpleStringProperty("");
    private BooleanProperty rejected = new SimpleBooleanProperty(false);


    public void submit() {
        //TODO implement
    }

    @Override
    public void rejected() {
        rejected.setValue(true);
    }

    @Override
    public void success() {
        //TODO: Implement
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public BooleanProperty rejectedProperty() {
        return rejected;
    }
}
