package tk.aakado.multisweeper.client.views.configuration;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

public class ConfigurationViewModel implements ViewModel,ConfigurationNotificator {

    private SimpleIntegerProperty mineDensitiy = new SimpleIntegerProperty();
    private SimpleIntegerProperty fieldWidth = new SimpleIntegerProperty();
    private SimpleIntegerProperty fieldHeight = new SimpleIntegerProperty();
    private SimpleStringProperty password = new SimpleStringProperty();
    private SimpleListProperty<String> players = new SimpleListProperty<>(FXCollections.emptyObservableList());//TODO: List type should be gamelogic.Player, in the #initialize Method we can specify the data to show

    public void start() {
        //TODO implement
    }

    public void save() {
        //TODO implement
    }

    public void disconnect() {
        //TODO implement
    }


    public SimpleIntegerProperty mineDensitiyProperty() {
        return mineDensitiy;
    }

    public SimpleIntegerProperty fieldWidthProperty() {
        return fieldWidth;
    }

    public SimpleIntegerProperty fieldHeightProperty() {
        return fieldHeight;
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public SimpleListProperty<String> playersProperty() {
        return players;
    }

    @Override
    public void playerDisconnected(String players) {

    }

    @Override
    public void playerConnected(String players) {

    }

    @Override
    public void gameStarted() {

    }
}
