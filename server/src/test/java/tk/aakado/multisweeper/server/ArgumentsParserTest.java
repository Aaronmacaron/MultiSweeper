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
        Optional<Integer> port = Parser.getPort(new String[]{});
        assertFalse(port.isPresent());
    }

    @Test
    public void portEmptyOnInvalidPort() {
        Optional<Integer> port = Parser.getPort(new String[]{"0"});
        assertFalse(port.isPresent());

        port = Parser.getPort(new String[]{"65536"});
        assertFalse(port.isPresent());
    }

    @Test
    public void portEmptyOnInvalidNumber() {
        Optional<Integer> port = Parser.getPort(new String[]{""});
        assertFalse(port.isPresent());

        port = Parser.getPort(new String[]{"Not a number"});
        assertFalse(port.isPresent());
    }

    @Test
    public void portAvailableOnValidPort() {
        String portString = "1";
        int portNum = 1;

        Optional<Integer> port = Parser.getPort(new String[]{portString});
        assertTrue(port.isPresent());
        assertEquals((Integer) portNum, port.get());


        portString = "65535";
        portNum = 65535;

        port = Parser.getPort(new String[]{portString});
        assertTrue(port.isPresent());
        assertEquals((Integer) portNum, port.get());
    }

    @Test
    public void gamesEmptyOnEmptyArgs() {
        Optional<Integer> games = Parser.getNumberOfGames(new String[]{});
        assertFalse(games.isPresent());
    }

    @Test
    public void gamesEmptyOnInvalidNumberOfGames() {
        Optional<Integer> games = Parser.getNumberOfGames(new String[]{"", "0"});
        assertFalse(games.isPresent());
    }

    @Test
    public void gamesEmptyOnInvalidNumber() {
        Optional<Integer> games = Parser.getPort(new String[]{"", "Not a number"});
        assertFalse(games.isPresent());
    }

    @Test
    public void gamesAvailableOnValidNumberOfGames() {
        String gamesString = "1";
        int games = 1;

        Optional<Integer> parsedGames = Parser.getNumberOfGames(new String[]{"", gamesString});
        assertTrue(parsedGames.isPresent());
        assertEquals((Integer) games, parsedGames.get());
    }

}