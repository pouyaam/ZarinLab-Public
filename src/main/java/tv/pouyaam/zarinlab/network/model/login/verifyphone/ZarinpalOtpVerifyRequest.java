package tv.pouyaam.zarinlab.network.model.login.verifyphone;

import com.google.gson.annotations.SerializedName;
import tv.pouyaam.zarinlab.network.model.base.BaseRequestModel;

public class ZarinpalOtpVerifyRequest extends BaseRequestModel {
    @SerializedName("grant_type")
    private final String mGrantType = "password";

    @SerializedName("client_id")
    private final String mPanelClient = "panel-client";

    @SerializedName("client_secret")
    private final String mClientSecret = "55619f94-75d6-4078-9478-16e9654c2105";

    @SerializedName("scope")
    private final String mScode = "full-access";

    @SerializedName("is_web_app")
    private final boolean mIsWebApp = true;

    @SerializedName("username")
    private String mUsername;

    @SerializedName("otp")
    private String mOtp;

    private ZarinpalOtpVerifyRequest(String username, String otp) {
        mUsername = username;
        mOtp = otp;
    }

    public String getGrantType() {
        return mGrantType;
    }

    public String getPanelClient() {
        return mPanelClient;
    }

    public String getClientSecret() {
        return mClientSecret;
    }

    public String getScode() {
        return mScode;
    }

    public boolean isWebApp() {
        return mIsWebApp;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getOtp() {
        return mOtp;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public void setOtp(String otp) {
        mOtp = otp;
    }

    public static class Builder {
        private String username;
        private String otp;

        public Builder() {
        }

        public ZarinpalOtpVerifyRequest build() {
            return new ZarinpalOtpVerifyRequest(username, otp);
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder otp(String otp) {
            this.otp = otp;
            return this;
        }
    }
}
