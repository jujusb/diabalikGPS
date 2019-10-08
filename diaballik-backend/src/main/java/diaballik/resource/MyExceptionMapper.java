package diaballik.resource;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class MyExceptionMapper implements ExceptionMapper<Exception> {
	@Override
	public Response toResponse(final Exception exception) {
		exception.printStackTrace();
		if(exception instanceof WebApplicationException) {
			return ((WebApplicationException) exception).getResponse();
		}
		return Response.status(500).build();
	}
}
