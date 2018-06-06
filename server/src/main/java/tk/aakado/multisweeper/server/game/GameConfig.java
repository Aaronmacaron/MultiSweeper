package tk.aakado.multisweeper.server.game;

public class GameConfig {

    private final int width;
    private final int height;
    private final int minesPercentage;

    public GameConfig(int width, int height, int minesPercentage) {
        this.width = width;
        this.height = height;
        this.minesPercentage = minesPercentage;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMinesPercentage() {
        return minesPercentage;
    }

}
