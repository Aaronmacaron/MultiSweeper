package tk.aakado.multisweeper.shared.connection.dtos;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameConfigDTOTest {

    private GameConfigDTO gameConfigDTO;

    final private static int width = 20;
    final private static int height = 30;
    final private static int minesPercentage = 19;

    @Before
    public void setUp() throws Exception {
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
        assertEquals(minesPercentage, gameConfigDTO.getMinesPercentage());
    }

}
