package tv.pouyaam.zarinlab.network.model.login.verifyphone;

import com.google.gson.annotations.SerializedName;

public class VerifyPhoneData {
    @SerializedName("expires_in")
    private int mExpiresIn;

    @SerializedName("access_token")
    private String mAccessToken;

    @SerializedName("refresh_token")
    private String mRefreshToken;

    protected VerifyPhoneData(int expiresIn, String accessToken, String refreshToken) {
        mExpiresIn = expiresIn;
        mAccessToken = accessToken;
        mRefreshToken = refreshToken;
    }

    public VerifyPhoneData() {
    }

    public long getExpiresIn() {
        return mExpiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        mExpiresIn = expiresIn;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public String getRefreshToken() {
        return mRefreshToken;
    }
}
