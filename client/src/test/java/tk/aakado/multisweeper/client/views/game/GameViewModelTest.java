package tk.aakado.multisweeper.client.views.game;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tk.aakado.multisweeper.client.Client;
import tk.aakado.multisweeper.client.views.game.model.Field;
import tk.aakado.multisweeper.shared.game.FieldState;

import static org.junit.Assert.assertEquals;

public class GameViewModelTest {

    private GameViewModel viewModel;

    @BeforeClass
    public static void setUpApp() {
        new Client();
    }

    @Before
    public void setUp() throws Exception {
        viewModel = new GameViewModel();
    }

    @Test
    public void restart() {
        int x = 4;
        int y = 4;
        List<Field> exceptedFields = new ArrayList<>();

        // create new fields
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                exceptedFields.add(new Field(i, j, FieldState.UNDISCOVERED, 0));
            }
        }

        // let the viewmodel create new fields
        viewModel.configureField(x, y, 0);
        List<Field> actualFields = viewModel.getFields();

        // check all fields
        for (int i = 0; i < actualFields.size(); i++) {
            assertEquals(exceptedFields.get(i), actualFields.get(i));
        }

    }

    @Test
    public void updateField() {
        // create and add a field to the list
        ObservableList<Field> fieldList = FXCollections.observableArrayList();
        FieldState newState = FieldState.MINE;
        fieldList.add(new Field(0, 0, FieldState.UNDISCOVERED, 0));

        // set the list as value
        this.viewModel.fieldsProperty().setValue(fieldList);

        // change the state of the field
        this.viewModel.updateField(0, 0, newState, Optional.empty());

        // check if the new field state is set
        assertEquals(newState, this.viewModel.getFields().get(0).getFieldState());

    }
}