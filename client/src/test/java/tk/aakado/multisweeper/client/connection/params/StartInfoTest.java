package tk.aakado.multisweeper.client.connection.params;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StartInfoTest {

    private StartInfo startInfo;

    @Before
    public void setUp() {
        startInfo = new StartInfo(0.4, 34,  16);
    }

    @Test
    public void getMineDensity() {
        assertEquals(0.4, startInfo.getMineDensity(), 0.0001);
    }

    @Test
    public void getFieldWidth() {
        assertEquals(34, startInfo.getFieldWidth());
    }

    @Test
    public void getFieldHeight() {
        assertEquals(16, startInfo.getFieldHeight());
    }

}
