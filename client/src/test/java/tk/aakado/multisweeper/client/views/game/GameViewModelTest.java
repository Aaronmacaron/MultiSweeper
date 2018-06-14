package tk.aakado.multisweeper.client.views.game;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import tk.aakado.multisweeper.client.views.game.model.Field;
import tk.aakado.multisweeper.shared.game.FieldState;

import static org.junit.Assert.assertEquals;

public class GameViewModelTest {

    private GameViewModel viewModel;

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
                exceptedFields.add(new Field(i, j, FieldState.UNDEFINED, 0));
            }
        }

        // let the viewmodel create new fields
        viewModel.restart(x, y);
        List<Field> actualFields = viewModel.getFields();

        // check all fields
        for (int i = 0; i < actualFields.size(); i++) {
            assertEquals(exceptedFields.get(i), actualFields.get(i));
        }

    }

    @Test
    public void click() {
    }
}