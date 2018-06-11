package tk.aakado.multisweeper.client.views.connection;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ConnectionViewModel implements ViewModel, ConnectionNotificator {

    private StringProperty connection = new SimpleStringProperty("");
    private BooleanProperty correctAddress = new SimpleBooleanProperty(false);
    private BooleanProperty rejected = new SimpleBooleanProperty(false);

    /**
     * Connects player to MultiSweeper server
     */
    void connect() {
        //TODO implement
    }


    @Override
    public void connected(boolean authRequired, boolean isAdmin) {
        //TODO: Implement
    }

    @Override
    public void rejected() {
        rejected.setValue(true);
    }


    public boolean isRejected() {
        return rejected.get();
    }

    public BooleanProperty rejectedProperty() {
        return rejected;
    }

    public StringProperty connectionProperty() {
        return connection;
    }

    public boolean isCorrectAddress() {
        return correctAddress.get();
    }

    public BooleanProperty correctAddressProperty() {
        return correctAddress;
    }

    public void setCorrectAddress(boolean correctAddress) {
        this.correctAddress.set(correctAddress);
    }
}
