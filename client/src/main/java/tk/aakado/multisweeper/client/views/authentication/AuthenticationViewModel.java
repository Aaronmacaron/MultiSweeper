package tk.aakado.multisweeper.client.views.authentication;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;

public class AuthenticationViewModel implements ViewModel, AuthenticationNotificator {

    private SimpleStringProperty password = new SimpleStringProperty("");

    public void submit() {
        //TODO implement
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    @Override
    public void rejected() {

    }

    @Override
    public void success() {

    }
}
