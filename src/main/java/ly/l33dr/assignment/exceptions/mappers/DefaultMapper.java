package ly.l33dr.assignment.exceptions.mappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import ly.l33dr.assignment.errors.AppError;

/**
 * Maps the InternalServerErrorException to a response with a status code of 500
 * 
 */
@Provider
public class DefaultMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        AppError appError = new AppError(0, exception.getMessage());
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(appError).build();
    }
}
