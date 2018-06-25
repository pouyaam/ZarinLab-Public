package tv.pouyaam.zarinlab.network.model.streamlabs;

import com.google.gson.annotations.SerializedName;
import tv.pouyaam.zarinlab.network.model.base.BaseResponseModel;

public class StreamLabTokenResponse extends BaseResponseModel {
    @SerializedName("expires_in")
    private int mExpiresIn;

    @SerializedName("access_token")
    private String mAccessToken;

    @SerializedName("refresh_token")
    private String mRefreshToken;

    public StreamLabTokenResponse(int expiresIn, String accessToken, String refreshToken) {
        mExpiresIn = expiresIn;
        mAccessToken = accessToken;
        mRefreshToken = refreshToken;
    }

    public int getExpiresIn() {
        return mExpiresIn;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public String getRefreshToken() {
        return mRefreshToken;
    }
}
