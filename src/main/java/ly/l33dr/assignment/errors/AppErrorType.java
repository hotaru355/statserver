package ly.l33dr.assignment.errors;

/**
 * List of all types of application errors including error code and message
 *
 */
public enum AppErrorType {
    MISSING_QUERY_PARAM(10000, "Request requires query parameter(s): %s"),
    ENTITY_NOT_FOUND(20000, "No entity found for given value. Entity: %s, Field: %s, Value: %s"),
    ILLEGAL_VALUE(30000, "Illegal input: %s");

    private String errorMsg;
    private int errorCode;

    AppErrorType(int errorCode, String errorMsg) {
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
