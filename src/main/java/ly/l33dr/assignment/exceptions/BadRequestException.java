package ly.l33dr.assignment.exceptions;

import java.util.List;

import ly.l33dr.assignment.errors.AppError;
import ly.l33dr.assignment.errors.AppErrorType;

/**
 * Exception that should result in a 400 HTTP status code response
 *
 */
public class BadRequestException extends AbstractAppException {
    private static final long serialVersionUID = 1L;

    public BadRequestException() {
        super();
    }

    public BadRequestException(String msg) {
        super(msg);
    }

    public BadRequestException(AppErrorType appErrorType, Object... params) {
        super(appErrorType, params);
    }

    public BadRequestException(AppError appError) {
        super(appError);
    }

    public BadRequestException(List<AppError> appErrors) {
        super(appErrors);
    }

    public BadRequestException(String msg, Exception e) {
        super(msg, e);
    }
}
