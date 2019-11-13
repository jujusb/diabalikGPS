package diaballik.resource;

import com.github.hanleyt.JerseyExtension;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import diaballik.coordinates.ActionCoord;
import diaballik.coordinates.Coordinate;
import diaballik.supervisors.Game;
import diaballik.players.Player;
import diaballik.players.HumanPlayer;
import diaballik.players.AiPlayer;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.*;

public class TestGameResource {
    static final Logger log = Logger.getLogger(TestGameResource.class.getSimpleName());

    @SuppressWarnings("unused")
    @RegisterExtension
    JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey);

    Application configureJersey() {
        return new ResourceConfig(GameResource.class)
                .register(MyExceptionMapper.class)
                .register(MoxyJsonFeature.class);
    }

    <T> T LogJSONAndUnmarshallValue(final Response res, final Class<T> classToRead) {
        res.bufferEntity();
        final String json = res.readEntity(String.class);
        log.log(Level.INFO, "JSON received: " + json);
        final T obj = res.readEntity(classToRead);
        res.close();
        return obj;
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void testNewGamePvP(final Client client, final URI baseUri) {
        final Response res = client
                .target(baseUri)
                .path("/game/newPvP/Bob/Bob2/true")
                .request()
                .post(Entity.text(""));
        System.out.println(res);
        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        final Game game = LogJSONAndUnmarshallValue(res, Game.class);
        assertNotNull(game);

        // checks we have a human white player with the right name
        Player p = game.getPlayer1();
        System.out.println(p);
        assertEquals(p, game.getPlayer1());
        assertTrue(p instanceof HumanPlayer);
        assertTrue(p.getColor());
        assertEquals("Bob", game.getCurrentPlayer().getName());

        // checks we have a human black player with the right name
        Player p2 = game.getPlayer2();
        System.out.println(p2);
        assertTrue(p2 instanceof HumanPlayer);
        assertFalse(p2.getColor());
        assertEquals("Bob2", p2.getName());
    }

    @Test
    void testNewGamePvE(final Client client, final URI baseUri) {
        final Response res = client
                .target(baseUri)
                .path("/game/newPvE/Bob/true/NOOB")
                .request()
                .post(Entity.text(""));

        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        final Game game = LogJSONAndUnmarshallValue(res, Game.class);
        assertNotNull(game);

        //checks we have a human white player with the right name
        Player p1 = game.getPlayer1();
        assertTrue(p1 instanceof HumanPlayer);
        assertEquals(p1, game.getCurrentPlayer());
        assertTrue(p1.getColor());
        assertEquals("Bob", p1.getName());

        // checks we have an AI black player
        Player p2 = game.getPlayer2();
        assertTrue(p2 instanceof AiPlayer);
        assertFalse(p2.getColor());
    }

    @Test
    void testNewGamePvEWithName(final Client client, final URI baseUri) {
        final Response res = client
                .target(baseUri)
                .path("/game/newPvE/Bob/Bob2/true/NOOB")
                .request()
                .post(Entity.text(""));

        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        final Game game = LogJSONAndUnmarshallValue(res, Game.class);
        assertNotNull(game);

        // checks we have a human white player with the right name
        assertTrue(game.getPlayer1() instanceof HumanPlayer);
        assertEquals(game.getPlayer1(), game.getCurrentPlayer());
        assertTrue(game.getPlayer1().getColor());
        assertEquals("Bob", game.getPlayer1().getName());

        // checks we have an AI black player with the right name
        assertTrue(game.getPlayer2() instanceof AiPlayer);
        assertFalse(game.getPlayer2().getColor());
        assertEquals("Bob2", game.getPlayer2().getName());
    }

    @Test
    void testMove(final Client client, final URI baseUri) {
        client
                .target(baseUri)
                .path("/game/newPvE/Bob/Bob2/true/NOOB")
                .request()
                .post(Entity.text(""));

        Response res = client
                .target(baseUri)
                .path("/game/action/move/0/0/0/1")
                .request()
                .post(Entity.text(""));

        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        final Game game = LogJSONAndUnmarshallValue(res, Game.class);
        assertNotNull(game);

        assertEquals(1, game.getNbActions());
        assertEquals(0, game.getCurrentTurn());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 1)).isPresent());
        assertEquals(new ActionCoord(new Coordinate(0, 0), new Coordinate(0, 1)), game.getGameBoard().getUndoable_mode().getFirst());
    }

    @Test
    void test3Move(final Client client, final URI baseUri) {
        client
                .target(baseUri)
                .path("/game/newPvP/Bob/Bob2/true")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/0/0/1")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/1/0/2")
                .request()
                .post(Entity.text(""));

        Response res = client
                .target(baseUri)
                .path("/game/action/move/0/2/0/3")
                .request()
                .post(Entity.text(""));

        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        final Game game = LogJSONAndUnmarshallValue(res, Game.class);
        assertNotNull(game);

        assertEquals(3, game.getNbActions());
        assertEquals(0, game.getCurrentTurn());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 3)).isPresent());
        assertEquals(new ActionCoord(new Coordinate(0, 2), new Coordinate(0, 3)), game.getGameBoard().getUndoable_mode().getFirst());
        System.out.println(game);
    }

    @Test
    void test4MoveEOTPvP(final Client client, final URI baseUri) {
        client
                .target(baseUri)
                .path("/game/newPvP/Bob/Bob2/true")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/0/0/1")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/1/0/2")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/2/0/3")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/endOfTurn")
                .request()
                .post(Entity.text(""));

        Response res = client
                .target(baseUri)
                .path("/game/action/move/0/3/0/4")
                .request()
                .post(Entity.text(""));

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), res.getStatus()); //TODO la méthode n'y accède jamais à cette réponse car it's the same think color and p.getColor()

        final Game game = LogJSONAndUnmarshallValue(res, Game.class);
        assertNotNull(game);

        assertEquals(0, game.getNbActions());
        assertEquals(0, game.getCurrentTurn());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 3)).isPresent());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 4)).isEmpty());
        assertTrue(game.getGameBoard().getUndoable_mode().isEmpty());
    }

    @Test
    void testEndOfTurnPvE(final Client client, final URI baseUri) {
        client
                .target(baseUri)
                .path("/game/newPvE/Bob/Bob2/true/STARTING")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/0/0/1")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/1/0/2")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/2/0/3")
                .request()
                .post(Entity.text(""));

        final Response res = client
                .target(baseUri)
                .path("/game/endOfTurn")
                .request()
                .post(Entity.text(""));

        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        final Game game = LogJSONAndUnmarshallValue(res, Game.class);
        assertNotNull(game);

        assertEquals(0, game.getNbActions());
        assertEquals(1, game.getCurrentTurn());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 3)).isPresent());
    }

    @Test
    void test4MoveEOTPvE(final Client client, final URI baseUri) {
        client
                .target(baseUri)
                .path("/game/newPvE/Bob/Bob2/true/NOOB")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/0/0/1")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/1/0/2")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/2/0/3")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/endOfTurn")
                .request()
                .post(Entity.text(""));

        Response res = client
                .target(baseUri)
                .path("/game/action/move/0/3/0/4")
                .request()
                .post(Entity.text(""));

        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        final Game game = LogJSONAndUnmarshallValue(res, Game.class);
        assertNotNull(game);

        assertEquals(1, game.getNbActions());
        assertEquals(1, game.getCurrentTurn());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 4)).isPresent());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 3)).isEmpty());
        assertEquals(new ActionCoord(new Coordinate(0, 3), new Coordinate(0, 4)), game.getGameBoard().getUndoable_mode().getFirst());
    }


    @Test
    void testEndOfTurnPvP(final Client client, final URI baseUri) {
        client
                .target(baseUri)
                .path("/game/newPvP/Bob/Bob2/true")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/0/0/1")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/1/0/2")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/2/0/3")
                .request()
                .post(Entity.text(""));

        final Response res = client
                .target(baseUri)
                .path("/game/endOfTurn")
                .request()
                .post(Entity.text(""));

        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        final Game game = LogJSONAndUnmarshallValue(res, Game.class);
        assertNotNull(game);

        assertEquals(0, game.getNbActions());
        assertEquals(0, game.getCurrentTurn());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 3)).isPresent());
        System.out.println(game.getGameBoard().getUndoable_mode());
        assertTrue(game.getGameBoard().getUndoable_mode().isEmpty());
    }

    @Test
    void testUndo(final Client client, final URI baseUri) {
        client
                .target(baseUri)
                .path("/game/newPvE/Bob/Bob2/true/NOOB")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/0/0/1")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/1/0/2")
                .request()
                .post(Entity.text(""));

        final Response res = client
                .target(baseUri)
                .path("/game/action/undo")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        final Game game = LogJSONAndUnmarshallValue(res, Game.class);
        assertNotNull(game);

        assertEquals(1, game.getNbActions());
        assertEquals(0, game.getCurrentTurn());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 1)).isPresent());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 2)).isEmpty());
        assertEquals(new ActionCoord(new Coordinate(0, 0), new Coordinate(0, 1)), game.getGameBoard().getUndoable_mode().getFirst());
        assertEquals(new ActionCoord(new Coordinate(0, 2), new Coordinate(0, 1)), game.getGameBoard().getRedoable_mode().getFirst());
    }

    @Test
    void test3Undo(final Client client, final URI baseUri) {
        client
                .target(baseUri)
                .path("/game/newPvE/Bob/Bob2/true/NOOB")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/0/0/1")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/1/0/2")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/2/0/3")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/undo")
                .request()
                .put(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/undo")
                .request()
                .put(Entity.text(""));

        final Response res = client
                .target(baseUri)
                .path("/game/action/undo")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        final Game game = LogJSONAndUnmarshallValue(res, Game.class);
        assertNotNull(game);

        assertEquals(0, game.getNbActions());
        assertEquals(0, game.getCurrentTurn());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 0)).isPresent());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 1)).isEmpty());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 2)).isEmpty());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 3)).isEmpty());
        assertEquals(new ActionCoord(new Coordinate(0, 1), new Coordinate(0, 0)), game.getGameBoard().getRedoable_mode().getFirst());
        assertTrue(game.getGameBoard().getUndoable_mode().isEmpty());
        assertEquals(game.getGameBoard().getRedoable_mode().size(), 3);
    }

    @Test
    void testRedo(final Client client, final URI baseUri) {
        client
                .target(baseUri)
                .path("/game/newPvE/Bob/Bob2/true/NOOB")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/0/0/1")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/1/0/2")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/undo")
                .request()
                .put(Entity.text(""));

        final Response res = client
                .target(baseUri)
                .path("/game/action/redo")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        final Game game = LogJSONAndUnmarshallValue(res, Game.class);
        assertNotNull(game);

        assertEquals(2, game.getNbActions());
        assertEquals(0, game.getCurrentTurn());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 2)).isPresent());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 1)).isEmpty());
        assertEquals(new ActionCoord(new Coordinate(0, 1), new Coordinate(0, 2)), game.getGameBoard().getUndoable_mode().getFirst());
        assertTrue(game.getGameBoard().getRedoable_mode().isEmpty());
    }

    @Test
    void testUndoRedoAction(final Client client, final URI baseUri) {
        client
                .target(baseUri)
                .path("/game/newPvE/Bob/Bob2/true/NOOB")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/0/0/1")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/1/0/2")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/undo")
                .request()
                .put(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/redo")
                .request()
                .put(Entity.text(""));

        final Response res = client
                .target(baseUri)
                .path("/game/action/move/0/2/1/2")
                .request()
                .post(Entity.text(""));

        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        final Game game = LogJSONAndUnmarshallValue(res, Game.class);
        assertNotNull(game);

        assertEquals(3, game.getNbActions());
        assertEquals(0, game.getCurrentTurn());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(1, 2)).isPresent());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 2)).isEmpty());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 1)).isEmpty());
        assertEquals(new ActionCoord(new Coordinate(0, 2), new Coordinate(1, 2)), game.getGameBoard().getUndoable_mode().getFirst());
        assertTrue(game.getGameBoard().getRedoable_mode().isEmpty());
    }

    @Test
    void test3Undo2Redo(final Client client, final URI baseUri) {
        client
                .target(baseUri)
                .path("/game/newPvE/Bob/Bob2/true/NOOB")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/0/0/1")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/1/0/2")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/move/0/2/0/3")
                .request()
                .post(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/undo")
                .request()
                .put(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/undo")
                .request()
                .put(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/undo")
                .request()
                .put(Entity.text(""));

        client
                .target(baseUri)
                .path("/game/action/redo")
                .request()
                .put(Entity.text(""));

        final Response res = client
                .target(baseUri)
                .path("/game/action/redo")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        final Game game = LogJSONAndUnmarshallValue(res, Game.class);
        assertNotNull(game);

        assertEquals(2, game.getNbActions());
        assertEquals(0, game.getCurrentTurn());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 2)).isPresent());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 0)).isEmpty());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 1)).isEmpty());
        assertTrue(game.getGameBoard().getPawn(new Coordinate(0, 3)).isEmpty());
        assertEquals(new ActionCoord(new Coordinate(0, 1), new Coordinate(0, 2)), game.getGameBoard().getUndoable_mode().getFirst());
        assertEquals(2, game.getGameBoard().getUndoable_mode().size());
        assertEquals(1, game.getGameBoard().getRedoable_mode().size());
    }

    @Test
    void testKill(final Client client, final URI baseUri) {
        final Response r = client
                .target(baseUri)
                .path("/game/newPvE/Bob/Bob2/true/NOOB")
                .request()
                .post(Entity.text(""));

        final Game g = LogJSONAndUnmarshallValue(r, Game.class);
        assertNotNull(g);

        final Response res = client
                .target(baseUri)
                .path("/game/kill")
                .request()
                .put(Entity.text(""));

        assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

        final Game game = LogJSONAndUnmarshallValue(res, Game.class);
        assertNull(game.getGameBoard());
        assertNull(game.getCurrentPlayer());
        assertNull(game.getPlayer1());
        assertNull(game.getPlayer2());
        assertEquals(-1, game.getNbActions());
        assertEquals(-1, game.getCurrentTurn());
        assertEquals(3, game.getNbActionsPerTurn());
    }
}