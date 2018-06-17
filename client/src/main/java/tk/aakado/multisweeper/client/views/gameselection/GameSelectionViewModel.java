package tk.aakado.multisweeper.client.views.gameselection;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.util.Set;

public class GameSelectionViewModel implements ViewModel, GameSelectionNotificator {

    private ListProperty<Integer> gameIds = new SimpleListProperty<>(FXCollections.emptyObservableList());

    @Override
    public void setAvailableGames(Set<Integer> gameIds) {
        this.gameIds.setAll(gameIds);
    }

    public ListProperty<Integer> gameIdsProperty() {
        return gameIds;
    }
}
