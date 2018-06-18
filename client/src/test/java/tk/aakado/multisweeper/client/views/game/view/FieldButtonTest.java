package tk.aakado.multisweeper.client.views.game.view;

import org.junit.Before;
import org.junit.Test;

import javafx.event.ActionEvent;
import tk.aakado.multisweeper.client.views.game.model.Field;
import tk.aakado.multisweeper.shared.game.FieldState;

import static org.junit.Assert.assertTrue;

public class FieldButtonTest {
    private FieldButton fieldButton;
    private Field field;
    private int x = 4;
    private int y = 10;
    private FieldState fieldState = FieldState.UNDEFINED;
    private int value = 8;
    private boolean onClickWorks = false;

    @Before
    public void setUp() throws Exception {
        this.field = new Field(x, y, fieldState, 8);
        // create a new FieldButton with a #dummyOnClick as onClickHandler
        // TODO: Lambdas dont work in junit 4
//         this.fieldButton = new FieldButton(this.field, this::dummyOnClick);
    }

    @Test
    public void testFieldStateBindinig() {
//         change the field state of the field, to check if the binding works
//        this.field.setFieldState(FieldState.MINE);

//        assertTrue(this.onClickWorks);
    }

    /**
     * Set onClickWorks to true
     *
     * @param actionEvent
     */
    private void dummyOnClick(ActionEvent actionEvent) {
        this.onClickWorks = true;
    }

    @Test
    public void getY() {
//        assertEquals(this.y, this.fieldButton.getY());
    }

    @Test
    public void getX() {
//        assertEquals(this.x, this.fieldButton.getX());
    }
}