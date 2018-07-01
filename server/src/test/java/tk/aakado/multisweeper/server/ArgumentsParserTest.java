package tk.aakado.multisweeper.server;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tk.aakado.multisweeper.server.Arguments.Parser;

import java.util.Optional;

import static org.junit.Assert.*;

public class ArgumentsParserTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void parseFailsOnInvalidPort() {
        Parser lowerParser = new Parser(new String[] { "0" });
        Parser upperParser = new Parser(new String[] { "65536" });
        try {
            lowerParser.parse();
            fail("The parsing should have failed!");

            upperParser.parse();
            fail("The parsing should have failed!");
        } catch (ArgumentParseException e) {
            // expected behaviour
        }
    }

    @Test
    public void parseFailsOnInvalidNumberOfGames() throws ArgumentParseException {
        Parser parser = new Parser(new String[] { "1", "0" });
        expectedException.expect(ArgumentParseException.class);
        parser.parse();
    }

    @Test
    public void parseSucceedsOnValidArgs() throws ArgumentParseException {
        Parser parser = new Parser(new String[] { "1", "1" });
        Arguments args = parser.parse();
        assertEquals(args.getPort(), 1);
        assertEquals(args.getNumberOfGames(), 1);
    }

    @Test
    public void portEmptyOnEmptyArgs() {
        Optional<Integer> port = Parser.parsePort(null);
        assertFalse(port.isPresent());
    }

    @Test
    public void portEmptyOnInvalidPort() {
        Optional<Integer> port = Parser.parsePort("0");
        assertFalse(port.isPresent());

        port = Parser.parsePort("65536");
        assertFalse(port.isPresent());
    }

    @Test
    public void portEmptyOnInvalidNumber() {
        Optional<Integer> port = Parser.parsePort("");
        assertFalse(port.isPresent());

        port = Parser.parsePort("Not a number");
        assertFalse(port.isPresent());
    }

    @Test
    public void portAvailableOnValidPort() {
        String portString = "1";
        int portNum = 1;

        Optional<Integer> port = Parser.parsePort(portString);
        assertTrue(port.isPresent());
        assertEquals((Integer) portNum, port.get());


        portString = "65535";
        portNum = 65535;

        port = Parser.parsePort(portString);
        assertTrue(port.isPresent());
        assertEquals((Integer) portNum, port.get());
    }


}