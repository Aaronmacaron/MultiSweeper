package tk.aakado.multisweeper.client.views.configuration;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class ConfigurationViewModel implements ViewModel, ConfigurationNotificator {

    private BooleanProperty admin = new SimpleBooleanProperty(false);
    private DoubleProperty mineDensity = new SimpleDoubleProperty();
    private IntegerProperty fieldWidth = new SimpleIntegerProperty();
    private IntegerProperty fieldHeight = new SimpleIntegerProperty();
    private StringProperty password = new SimpleStringProperty();
    private ListProperty<String> players = new SimpleListProperty<>(FXCollections.emptyObservableList());

    public void start() {
        //TODO implement
    }

    public void save() {
        //TODO implement
    }

    public void disconnect() {
        //TODO implement
    }

    @Override
    public void playerDisconnected(String player, boolean isNewAdmin) {
        //TODO implement
    }

    @Override
    public void playerConnected(String player) {
        //TODO implement
    }

    @Override
    public void gameStarted() {
        //TODO implement
    }


    public DoubleProperty mineDensityProperty() {
        return mineDensity;
    }

    public IntegerProperty fieldWidthProperty() {
        return fieldWidth;
    }

    public IntegerProperty fieldHeightProperty() {
        return fieldHeight;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public ListProperty<String> playersProperty() {
        return players;
    }

    public boolean isAdmin() {
        return admin.get();
    }

    public BooleanProperty adminProperty() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin.set(admin);
    }
}
