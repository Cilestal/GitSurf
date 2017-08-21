package ua.dp.michaellang.gitsurf.presenter.login;

import java.io.IOException;

/**
 * Date: 13.05.17
 *
 * @author Michael Lang
 */
public class AccessTokenException extends IOException {
    public AccessTokenException() {
    }

    public AccessTokenException(String message) {
        super(message);
    }
}
