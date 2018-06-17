package tk.aakado.multisweeper.shared.connection.dtos;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameConfigDTOTest {

    private GameConfigDTO gameConfigDTO;

    final private static int width = 20;
    final private static int height = 30;
    final private static double minesPercentage = 19;

    @Before
    public void setUp() {
        gameConfigDTO = new GameConfigDTO(width, height, minesPercentage);
    }

    @Test
    public void getWidth() {
        assertEquals(width, gameConfigDTO.getWidth());
    }

    @Test
    public void getHeight() {
        assertEquals(height, gameConfigDTO.getHeight());
    }

    @Test
    public void getMinesPercentage() {
        assertEquals(minesPercentage, gameConfigDTO.getMinesPercentage(), 0.00001);
    }

}
