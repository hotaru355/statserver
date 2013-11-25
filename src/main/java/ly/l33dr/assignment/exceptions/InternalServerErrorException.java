package ly.l33dr.assignment.exceptions;

import java.util.List;

import ly.l33dr.assignment.errors.AppError;
import ly.l33dr.assignment.errors.AppErrorType;

/**
 * Exception that should result in a 500 HTTP status code response
 *
 */
public class InternalServerErrorException extends AbstractAppException {
    private static final long serialVersionUID = 1L;

    public InternalServerErrorException() {
        super();
    }

    public InternalServerErrorException(String msg) {
        super(msg);
    }

    public InternalServerErrorException(AppErrorType appErrorType, Object... params) {
        super(appErrorType, params);
    }

    public InternalServerErrorException(AppError appError) {
        super(appError);
    }

    public InternalServerErrorException(List<AppError> appErrors) {
        super(appErrors);
    }

    public InternalServerErrorException(String msg, Exception e) {
        super(msg, e);
    }

}
