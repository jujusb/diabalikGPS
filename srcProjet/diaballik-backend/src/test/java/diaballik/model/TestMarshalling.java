package diaballik.model;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import diaballik.coordinates.ActionCoord;
import diaballik.coordinates.Coordinate;
import diaballik.gameElements.GameBoard;
import diaballik.gameElements.Pawn;
import diaballik.players.AiPlayer;
import diaballik.players.algorithms.EAiType;
import diaballik.players.HumanPlayer;
import diaballik.players.Player;
import diaballik.supervisors.Game;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestMarshalling {
    <T> T marshall(final T objectToMarshall) throws IOException, JAXBException {
        final Map<String, Object> properties = new HashMap<>();
        properties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
        properties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, Boolean.TRUE);

        final JAXBContext ctx = JAXBContextFactory.createContext(new Class[]{objectToMarshall.getClass()}, properties);
        final Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        final StringWriter sw = new StringWriter();
        marshaller.marshal(objectToMarshall, sw);
        marshaller.marshal(objectToMarshall, System.out);

        final Unmarshaller unmarshaller = ctx.createUnmarshaller();
        final StringReader reader = new StringReader(sw.toString());
        final T o = (T) unmarshaller.unmarshal(reader);

        sw.close();
        reader.close();

        return o;
    }

    Player p1;
    Player p2;
    Player p3;
    Coordinate c1;
    Coordinate c2;
    ActionCoord ac;
    Pawn pawn1;
    GameBoard board;
    Game game;

    @BeforeEach
    void setUp() {
        p1 = new HumanPlayer("foo", true);
        p2 = new AiPlayer(EAiType.PROGRESSIVE, "foo", false);
        p3 = new HumanPlayer("Zack", true);
        c1 = new Coordinate(0, 0);
        c2 = new Coordinate(0, 1);
        ac = new ActionCoord(c1, c2);
        pawn1 = new Pawn(c2, p3);
        game = new Game(p1, p2);
    }

    @Test
    void testHumanPlayer() throws IOException, JAXBException {
        final Player p = marshall(p1);
        assertEquals("foo", p.getName());
        assertTrue(p.getColor());
    }

    @Test
    void testAiPlayer() throws IOException, JAXBException {
        final Player p = marshall(p2);
        assertEquals("foo", p.getName());
        assertFalse(p.getColor());
    }

    @Test
    void testCoordinate() throws IOException, JAXBException {
        final Coordinate c = marshall(c2);
        assertEquals(0, c2.getPosX());
        assertEquals(1, c2.getPosY());
    }


    @Test
    void testActionCoord() throws IOException, JAXBException {
        final ActionCoord c = marshall(ac);
        assertEquals(c1, ac.getSource());
        assertEquals(c2, ac.getTarget());
    }

    @Test
    void testPawn() throws IOException, JAXBException {
        pawn1.setBallOwner(true);
        final Pawn p = marshall(pawn1);
        assertEquals(p.getPlayer(), p3);
        assertEquals(p.getPosition(), c2);
        assertTrue(p.isBallOwner());
    }

    @Test
    void testGameBoard() throws IOException, JAXBException {
        board = new GameBoard(p1, p2);
        board.getUndoable_mode().add(ac);
        board.getRedoable_mode().add(ac);
        final GameBoard g = marshall(board);
        assertEquals(board.getUndoable_mode().size(), g.getUndoable_mode().size());
        assertEquals(board.getRedoable_mode().size(), g.getRedoable_mode().size());
    }

    @Test
    void testGame() throws IOException, JAXBException {
        final Game g = marshall(game);
        assertEquals(g.getCurrentPlayer(),p1);
    }
}
