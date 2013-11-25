package ly.l33dr.assignment.exceptions.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ly.l33dr.assignment.errors.AppError;

import org.codehaus.jackson.JsonParseException;

/**
 * Maps the JsonParseException to a response with a status code of 400
 *
 */
@Provider
public class JsonParseMapper implements ExceptionMapper<JsonParseException> {
    @Override
    public Response toResponse(JsonParseException exception) {
        AppError appError = new AppError(0, exception.getMessage());
        return Response.status(Status.BAD_REQUEST).entity(appError).build();
    }
}
