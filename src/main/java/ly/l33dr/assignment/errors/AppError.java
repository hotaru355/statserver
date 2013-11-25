package ly.l33dr.assignment.errors;

/**
 * Application error that is returned as a JSON when an exception is thrown
 * 
 */
public class AppError {
    private String errorMsg;
    private int errorCode;

    public AppError(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
