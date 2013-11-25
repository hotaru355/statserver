package ly.l33dr.assignment.exceptions.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ly.l33dr.assignment.exceptions.NotFoundException;

/**
 * Maps the NotFoundException to a response with a status code of 404
 *
 */
@Provider
public class NotFoundMapper implements ExceptionMapper<NotFoundException> {
    @Override
    public Response toResponse(NotFoundException exception) {
        if (exception.isAppError()) {
            return Response.status(Status.NOT_FOUND).entity(exception.getAppErrors()).build();
        } else {
            return Response.status(Status.NOT_FOUND).entity(exception.getMessage()).build();
        }
    }
}
