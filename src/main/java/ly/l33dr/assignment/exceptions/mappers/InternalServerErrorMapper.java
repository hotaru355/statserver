package ly.l33dr.assignment.exceptions.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ly.l33dr.assignment.exceptions.InternalServerErrorException;

/**
 * Maps the InternalServerErrorException to a response with a status code of 500
 *
 */
@Provider
public class InternalServerErrorMapper implements ExceptionMapper<InternalServerErrorException> {
    @Override
    public Response toResponse(InternalServerErrorException exception) {
        if (exception.isAppError()) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception.getAppErrors())
                    .build();
        } else {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage())
                    .build();
        }
    }
}
