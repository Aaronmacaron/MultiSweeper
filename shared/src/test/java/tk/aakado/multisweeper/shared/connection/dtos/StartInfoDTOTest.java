package tk.aakado.multisweeper.shared.connection.dtos;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class StartInfoDTOTest {

    private StartInfoDTO startInfoDTO;

    @Before
    public void setUp() {
        startInfoDTO = new StartInfoDTO(34, 16, Collections.emptyList());
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
