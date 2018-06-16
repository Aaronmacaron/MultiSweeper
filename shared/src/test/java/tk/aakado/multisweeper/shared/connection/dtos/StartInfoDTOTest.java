package tk.aakado.multisweeper.shared.connection.dtos;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StartInfoDTOTest {

    private StartInfoDTO startInfoDTO;

    @Before
    public void setUp() {
        startInfoDTO = new StartInfoDTO(0.4, 34,  16);
    }

    @Test
    public void getMineDensity() {
        assertEquals(0.4, startInfoDTO.getMineDensity(), 0.0001);
    }

    @Test
    public void getFieldWidth() {
        assertEquals(34, startInfoDTO.getFieldWidth());
    }

    @Test
    public void getFieldHeight() {
        assertEquals(16, startInfoDTO.getFieldHeight());
    }

}
