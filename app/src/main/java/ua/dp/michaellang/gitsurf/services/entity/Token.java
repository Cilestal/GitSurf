package ua.dp.michaellang.gitsurf.services.entity;

/**
 * Date: 20.04.17
 *
 * @author Michael Lang
 */
public class Token {
    private String access_token;
    private String scope;
    private String token_type;

    public Token() {
    }

    public Token(String access_token, String scope, String token_type) {
        this.access_token = access_token;
        this.scope = scope;
        this.token_type = token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }
}
