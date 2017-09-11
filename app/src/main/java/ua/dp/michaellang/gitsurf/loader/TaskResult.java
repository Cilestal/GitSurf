package ua.dp.michaellang.gitsurf.loader;

/**
 * Date: 04.03.17
 *
 * @author Michael Lang
 */
public class TaskResult<T> {
    private T mResult;
    private Exception mException;

    public TaskResult(T result) {
        mResult = result;
    }

    public TaskResult(Exception e) {
        mException = e;
    }

    public T getResult() {
        return mResult;
    }

    public Exception getException() {
        return mException;
    }

    public boolean isSuccess() {
        return mException == null;
    }

    public boolean isAuthError() {
        if (mException == null) {
            return false;
        }

        return "No authentication challenges found".equalsIgnoreCase(mException.getMessage()) ||
                "Received authentication challenge is null".equalsIgnoreCase(mException.getMessage()) ||
                "Bad credentials (401)".equalsIgnoreCase(mException.getMessage());
    }
}
