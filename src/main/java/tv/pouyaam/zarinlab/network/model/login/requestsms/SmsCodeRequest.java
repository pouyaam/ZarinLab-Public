package tv.pouyaam.zarinlab.network.model.login.requestsms;

import com.google.gson.annotations.SerializedName;
import tv.pouyaam.zarinlab.network.model.base.BaseRequestModel;

public class SmsCodeRequest extends BaseRequestModel {
    @SerializedName("username")
    private String mUsername;

    @SerializedName("channel")
    private String mChannel;

    private SmsCodeRequest(String username, String channel) {
        mUsername = username;
        mChannel = channel;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getChannel() {
        return mChannel;
    }

    public static class Builder {
        private String username;
        private String channel;

        public SmsCodeRequest build() {
            return new SmsCodeRequest(username, channel);
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder channel(String channel) {
            this.channel = channel;
            return this;
        }
    }
}
