package diaballik;

import diaballik.resource.GameResource;
import diaballik.resource.MyExceptionMapper;
import java.io.IOException;
import java.net.URI;
import javax.ws.rs.client.ClientBuilder;
import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public final class Main {
	// Base URI the Grizzly HTTP server will listen on
	// When building docker images, replace the address with http://0.0.0.0:4444/
	// http://localhost:4444/ is for testing purpose only.
	public static final String HTTP_ADDRESS = "http://localhost:4444/";

	private Main() {
		super();
	}

	/**
	 * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
	 * @return Grizzly HTTP server.
	 */
	public static HttpServer startServer() {
		final ResourceConfig rc = new ResourceConfig(GameResource.class)
			.register(MyExceptionMapper.class)
			.register(MoxyJsonFeature.class)
			.register(io.swagger.jaxrs.listing.ApiListingResource.class)
			.register(io.swagger.jaxrs.listing.SwaggerSerializers.class);

		return GrizzlyHttpServerFactory.createHttpServer(URI.create(HTTP_ADDRESS), rc);
	}

	// http://localhost:4444/swag/index.html
	public static void main(final String[] args) throws IOException {
		final HttpServer server = startServer();
		// Required to access the web pages stored in the webapp folder.
		final ClassLoader loader = Main.class.getClassLoader();
		final CLStaticHttpHandler docsHandler = new CLStaticHttpHandler(loader, "src/main/webapp/");
		docsHandler.setFileCacheEnabled(false);

		server.getServerConfiguration().addHttpHandler(docsHandler, "/diabalik/");
		ClientBuilder.newClient().target(HTTP_ADDRESS);
		System.in.read();
		server.shutdownNow();
	}
}

