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

import diaballik.Coordinates.ActionCoord;
import diaballik.Coordinates.Coordinate;
import diaballik.GameElements.GameBoard;
import diaballik.GameElements.Pawn;
import diaballik.Players.AiPlayer;
import diaballik.Players.Algorithms.EAiType;
import diaballik.Players.HumanPlayer;
import diaballik.Players.Player;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


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

    @BeforeEach
    void setUp() {
        p1 = new HumanPlayer("foo", true);
        p3 = new HumanPlayer("Zack", true);
        p2 = new AiPlayer(EAiType.PROGRESSIVE, "foo", false);
        c1 = new Coordinate(0, 0);
        c2 = new Coordinate(0, 1);
        ac = new ActionCoord(c1, c2);
        pawn1 = new Pawn(c2,p3);
        board = new GameBoard(p1,p2);
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
        assertTrue(p.getColor());
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
        assertEquals(p.getPosition(),c2);
        assertTrue(p.isBallOwner());
    }

    @Test
    void testGameBoard() throws IOException, JAXBException {
        final GameBoard g = marshall(board);
        assertEquals(board.getUndoable_mode(),g.getUndoable_mode());
        assertEquals(board.getRedoable_mode(),g.getRedoable_mode());
    }
}
