package ly.l33dr.assignment.exceptions;

import java.util.List;

import ly.l33dr.assignment.errors.AppError;
import ly.l33dr.assignment.errors.AppErrorType;

/**
 * Exception that should result in a 404 HTTP status code response
 *
 */
public class NotFoundException extends AbstractAppException {
    private static final long serialVersionUID = 1L;

    public NotFoundException() {
        super();
    }

    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(AppErrorType appErrorType, Object... params) {
        super(appErrorType, params);
    }

    public NotFoundException(AppError appError) {
        super(appError);
    }

    public NotFoundException(List<AppError> appErrors) {
        super(appErrors);
    }

    public NotFoundException(String msg, Exception e) {
        super(msg, e);
    }

}
