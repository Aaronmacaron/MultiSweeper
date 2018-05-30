package tk.aakado.multisweeper.logic;

import java.util.List;

import tk.aakado.multisweeper.logic.Field;
import tk.aakado.multisweeper.logic.FieldCords;

public class PlayingField {

    private final int width;
    private final int height;
    private final int numberOfMines;
    private List<Field> fields;

    public PlayingField(int width, int height, int numberOfMines) {
        this.width = width;
        this.height = height;
        this.numberOfMines = numberOfMines;
    }

    public void generate() {

    }

    public void discoverField(FieldCords fieldCords) {

    }

    public void flagField(FieldCords fieldCords) {

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

}
