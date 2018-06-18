package tk.aakado.multisweeper.client.views.game.model;

import org.junit.Before;
import org.junit.Test;

import tk.aakado.multisweeper.shared.game.FieldState;

import static org.junit.Assert.assertEquals;

public class FieldTest {

    private Field field;
    private int x = 4;
    private int y = 10;
    private FieldState fieldState = FieldState.UNDISCOVERED;
    private int value = 8;

    @Before
    public void setUp() throws Exception {
        this.field = new Field(x, y, fieldState, 8);
    }

    @Test
    public void setFieldState() {
        this.field.setFieldState(FieldState.MINE);
        assertEquals(FieldState.MINE, this.field.getFieldState());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHighFieldValue() {
        new Field(x, y, fieldState, 9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLowFieldValue() {
        new Field(x, y, fieldState, -1);
    }


    @Test
    public void getX() {
        assertEquals(this.x, this.field.getX());
    }

    @Test
    public void getY() {
        assertEquals(this.y, this.field.getY());
    }

    @Test
    public void getFieldState() {
        assertEquals(this.fieldState, this.field.getFieldState());
    }

    @Test
    public void getValue() {
        assertEquals(0, new Field(x, y, fieldState, 0).getValue());
    }
}