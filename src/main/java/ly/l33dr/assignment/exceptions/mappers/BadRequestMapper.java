package ly.l33dr.assignment.exceptions.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ly.l33dr.assignment.exceptions.BadRequestException;

/**
 * Maps the BadRequestException to a response with a status code of 400
 *
 */
@Provider
public class BadRequestMapper implements ExceptionMapper<BadRequestException> {
    @Override
    public Response toResponse(BadRequestException exception) {
        if (exception.isAppError()) {
            return Response.status(Status.BAD_REQUEST).entity(exception.getAppErrors()).build();
        } else {
            return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
        }
    }
}
