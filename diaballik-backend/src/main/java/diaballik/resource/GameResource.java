package diaballik.resource;

import io.swagger.annotations.Api;
import javax.inject.Singleton;
import javax.ws.rs.Path;

@Singleton
@Path("game")
@Api(value = "game")
public class GameResource {
	public GameResource() {
		super();
	}
}
