package tk.aakado.multisweeper.client.configuration;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

public class ConfigurationViewModel implements ViewModel {

    private SimpleIntegerProperty mineDensitiy = new SimpleIntegerProperty();
    private SimpleIntegerProperty fieldWidth = new SimpleIntegerProperty();
    private SimpleIntegerProperty fieldHeight = new SimpleIntegerProperty();
    private SimpleStringProperty passwordString = new SimpleStringProperty();
    private SimpleListProperty<String> playersList = new SimpleListProperty<>();//TODO: List type should be gamelogic.Player, in the #initialize Method we can specify the data to show

    public int getMineDensitiy() {
        return mineDensitiy.get();
    }

    public SimpleIntegerProperty mineDensitiyProperty() {
        return mineDensitiy;
    }

    public void setMineDensitiy(int mineDensitiy) {
        this.mineDensitiy.set(mineDensitiy);
    }

    public int getFieldWidth() {
        return fieldWidth.get();
    }

    public SimpleIntegerProperty fieldWidthProperty() {
        return fieldWidth;
    }

    public void setFieldWidth(int fieldWidth) {
        this.fieldWidth.set(fieldWidth);
    }

    public int getFieldHeight() {
        return fieldHeight.get();
    }

    public SimpleIntegerProperty fieldHeightProperty() {
        return fieldHeight;
    }

    public void setFieldHeight(int fieldHeight) {
        this.fieldHeight.set(fieldHeight);
    }

    public String getPasswordString() {
        return passwordString.get();
    }

    public SimpleStringProperty passwordStringProperty() {
        return passwordString;
    }

    public void setPasswordString(String passwordString) {
        this.passwordString.set(passwordString);
    }

    public ObservableList<String> getPlayersList() {
        return playersList.get();
    }

    public SimpleListProperty<String> playersListProperty() {
        return playersList;
    }

    public void setPlayersList(ObservableList<String> playersList) {
        this.playersList.set(playersList);
        //TODO: Listtype should be gamelogic.Player
    }


    public void start() {
        //TODO implement
    }

    public void save() {
        //TODO implement
    }

    public void disconnect() {
        //TODO implement
    }
}
