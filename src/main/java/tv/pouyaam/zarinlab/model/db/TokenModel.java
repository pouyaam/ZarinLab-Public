package tv.pouyaam.zarinlab.model.db;

/**
 * Created by pouya on 6/22/18.
 */
public class TokenModel {
    public enum TOKEN_TYPE {
        ZARINPAL,
        STREAMLABS
    }
    private int id;
    private long expiresIn;
    private String accessToken;
    private String refreshToken;
    private TOKEN_TYPE tokenType;

    public TokenModel(long expiresIn, String accessToken, String refreshToken, TOKEN_TYPE tokenType) {
        this.expiresIn = expiresIn;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = tokenType;
    }

    public int getId() {
        return id;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public TOKEN_TYPE getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = TOKEN_TYPE.valueOf(tokenType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TokenModel that = (TokenModel) o;

        return id == that.id && expiresIn == that.expiresIn && accessToken.equals(that.accessToken) && refreshToken.equals(that.refreshToken);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) (expiresIn ^ (expiresIn >>> 32));
        result = 31 * result + accessToken.hashCode();
        result = 31 * result + refreshToken.hashCode();
        return result;
    }
}
