package tk.aakado.multisweeper.client.views.game.model;

import java.util.Objects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import tk.aakado.multisweeper.shared.game.FieldState;

/**
 * Model class for fields
 *
 * @author Dominik Strässle
 */
public class Field {

    private IntegerProperty x = new SimpleIntegerProperty();
    private IntegerProperty y = new SimpleIntegerProperty();
    private ObjectProperty<FieldState> fieldState = new SimpleObjectProperty<>();
    private IntegerProperty value = new SimpleIntegerProperty();

    /**
     * Create a new Field
     *
     * @param x     x-coordinate
     * @param y     y-coordinate
     * @param state Actual state of the field
     */
    public Field(int x, int y, FieldState state, int value) {
        this.x.setValue(x);
        this.y.setValue(y);
        this.fieldState.setValue(state);

        // check if the value is in the correct range
        if (value < 0 || value > 8) {
            throw new IllegalArgumentException("The value must be between (including) 0 and 8");
        }
        this.value.setValue(value);
    }

    // getter & setter
    public void setFieldState(FieldState fieldState) {
        this.fieldState.set(fieldState);
    }

    public int getX() {
        return x.get();
    }

    public IntegerProperty xProperty() {
        return x;
    }

    public int getY() {
        return y.get();
    }

    public IntegerProperty yProperty() {
        return y;
    }

    public FieldState getFieldState() {
        return fieldState.get();
    }

    public ObjectProperty<FieldState> fieldStateProperty() {
        return fieldState;
    }

    public int getValue() {
        return value.get();
    }

    public IntegerProperty valueProperty() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return Objects.equals(x.get(), field.x.get()) &&
                Objects.equals(y.get(), field.y.get()) &&
                Objects.equals(fieldState.get(), field.fieldState.get()) &&
                Objects.equals(value.get(), field.value.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, fieldState, value);
    }

    @Override
    public String toString() {
        return "Field{" +
                "x=" + x +
                ", y=" + y +
                ", fieldState=" + fieldState +
                ", value=" + value +
                '}';
    }
}
