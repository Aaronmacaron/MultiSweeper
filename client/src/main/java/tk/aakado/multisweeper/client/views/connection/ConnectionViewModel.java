package tk.aakado.multisweeper.client.views.connection;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;

public class ConnectionViewModel implements ViewModel, ConnectionNotificator {

    private SimpleStringProperty connection = new SimpleStringProperty("");

    /**
     * Connects player to MultiSweeper server
     */
    void connect() {
        //TODO implement
    }

    @Override
    public void connected(boolean authRequired) {

    }

    @Override
    public void rejected() {

    }

    // property getters

    public SimpleStringProperty connectionProperty() {
        return connection;
    }


}
