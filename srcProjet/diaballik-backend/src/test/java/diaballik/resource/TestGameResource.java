package diaballik.resource;

import com.github.hanleyt.JerseyExtension;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import diaballik.Coordinates.ActionCoord;
import diaballik.Coordinates.Coordinate;
import diaballik.Supervisors.Game;
import diaballik.Players.Player;
import diaballik.Players.HumanPlayer;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.*;

public class TestGameResource {
	static final Logger log = Logger.getLogger(TestGameResource.class.getSimpleName());

	@SuppressWarnings("unused") @RegisterExtension JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey);

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
		 	.path("/newPvP/Bob/Bob2/true")
		 	.request()
		 	.post(Entity.text(""));

		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

		final Game game = LogJSONAndUnmarshallValue(res, Game.class);
		assertNotNull(game);

		// checks we have a human white player with the right name
		assertTrue(game.getCurrentPlayer() instanceof  HumanPlayer);
		assertTrue(game.getCurrentPlayer().getColor());
		assertEquals("Bob",game.getCurrentPlayer().getName());

		game.moveOfPlayer(new ActionCoord(new Coordinate(0,3),new Coordinate(0,4)));
		game.moveOfPlayer(new ActionCoord(new Coordinate(0,3),new Coordinate(1,3)));
		game.moveOfPlayer(new ActionCoord(new Coordinate(1,3),new Coordinate(2,3)));

		// checks we have a human black player with the right name
		assertTrue(game.getCurrentPlayer() instanceof  HumanPlayer);
		assertFalse(game.getCurrentPlayer().getColor());
		assertEquals("Bob2",game.getCurrentPlayer().getName());
	}

	@Test
	void testNewGamePvE(final Client client, final URI baseUri) {
		final Response res = client
				.target(baseUri)
				.path("/newPvP/Bob/Bob2/true")
				.request()
				.post(Entity.text(""));

		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

		final Game game = LogJSONAndUnmarshallValue(res, Game.class);
		assertNotNull(game);

		// checks we have a human white player with the right name
		assertTrue(game.getCurrentPlayer() instanceof  HumanPlayer);
		assertTrue(game.getCurrentPlayer().getColor());
		assertEquals("Bob",game.getCurrentPlayer().getName());

		game.moveOfPlayer(new ActionCoord(new Coordinate(0,3),new Coordinate(0,4)));
		game.moveOfPlayer(new ActionCoord(new Coordinate(0,3),new Coordinate(1,3)));
		game.moveOfPlayer(new ActionCoord(new Coordinate(1,3),new Coordinate(2,3)));

		// checks we have a human black player with the right name
		assertTrue(game.getCurrentPlayer() instanceof  HumanPlayer);
		assertFalse(game.getCurrentPlayer().getColor());
		assertEquals("Bob2",game.getCurrentPlayer().getName());
	}
}
