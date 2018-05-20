package tk.aakado.multisweeper.client.connection;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;

public class ConnectionViewModel implements ViewModel {

    private SimpleStringProperty connectionString = new SimpleStringProperty();

    public String getConnectionString() {
        return connectionString.get();
    }

    public SimpleStringProperty connectionStringProperty() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString.set(connectionString);
    }

    public void connect() {
        //TODO: variante 1
        String tuwas = connectionString.get();
    }

    public void connect(String text) {
        //TODO: variante 2
        String tuwas = text;
    }
}
