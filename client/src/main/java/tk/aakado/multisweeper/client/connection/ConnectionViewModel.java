package tk.aakado.multisweeper.client.connection;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;

public class ConnectionViewModel implements ViewModel {

    private SimpleStringProperty connection = new SimpleStringProperty("");

    /**
     * Connects player to MultiSweeper server
     */
    void connect() {
        //TODO implement
    }


    public SimpleStringProperty connectionProperty() {
        return connection;
    }
}
