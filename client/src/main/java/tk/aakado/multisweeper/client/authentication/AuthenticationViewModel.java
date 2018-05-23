package tk.aakado.multisweeper.client.authentication;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;

public class AuthenticationViewModel implements ViewModel {

    private SimpleStringProperty passwordString = new SimpleStringProperty("");

    public String getPasswordString() {
        return passwordString.get();
    }

    public SimpleStringProperty passwordStringProperty() {
        return passwordString;
    }

    public void setPasswordString(String passwordString) {
        this.passwordString.set(passwordString);
    }

    public void submit() {
        //TODO implement
    }
}
