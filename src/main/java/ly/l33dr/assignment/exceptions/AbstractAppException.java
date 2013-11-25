package ly.l33dr.assignment.exceptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ly.l33dr.assignment.errors.AppError;
import ly.l33dr.assignment.errors.AppErrorType;

/**
 * Application exception base class with constructors taking app error instances
 *
 */
public abstract class AbstractAppException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<AppError> appErrors = new ArrayList<AppError>();

    public AbstractAppException() {
        super();
    }

    public AbstractAppException(String msg) {
        super(msg);
    }

    public AbstractAppException(AppErrorType appErrorType, Object... params) {
        super(String.format(appErrorType.getErrorMsg(), params));
        AppError appError = new AppError(appErrorType.getErrorCode(), String.format(
                appErrorType.getErrorMsg(), params));
        appErrors.add(appError);
    }

    public AbstractAppException(AppError appError) {
        super(appError.getErrorMsg());
        appErrors.add(appError);
    }

    public AbstractAppException(List<AppError> appErrors) {
        super("multiple Exceptions thrown.");
        this.appErrors = appErrors;
    }

    public AbstractAppException(String msg, Exception e) {
        super(msg, e);
    }

    public List<AppError> getAppErrors() {
        return appErrors;
    }

    public boolean isAppError() {
        return !appErrors.isEmpty();
    }
}
