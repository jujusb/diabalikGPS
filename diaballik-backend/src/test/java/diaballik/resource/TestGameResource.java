package diaballik.resource;

import com.github.hanleyt.JerseyExtension;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

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
		// final Response res = client
		// 	.target(baseUri)
		// 	.path("TODO")
		// 	.request()
		// 	.post(Entity.text(""));

		// assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());

		// final Game game = LogJSONAndUnmarshallValue(res, Game.class);

		// assertNotNull(game);
		// etc.
	}
}
