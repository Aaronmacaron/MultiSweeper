package tk.aakado.multisweeper.client.views;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GameProperties {
    private BooleanProperty admin = new SimpleBooleanProperty(false);
    private BooleanProperty victory = new SimpleBooleanProperty();

    public boolean isAdmin() {
        return admin.get();
    }

    public BooleanProperty adminProperty() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin.set(admin);
    }

    public boolean isVictory() {
        return victory.get();
    }

    public BooleanProperty victoryProperty() {
        return victory;
    }

    public void setVictory(boolean victory) {
        this.victory.set(victory);
    }
}
