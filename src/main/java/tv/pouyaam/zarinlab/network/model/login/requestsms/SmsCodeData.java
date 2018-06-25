package tv.pouyaam.zarinlab.network.model.login.requestsms;

import com.google.gson.annotations.SerializedName;
import tv.pouyaam.zarinlab.network.model.base.BaseResponseModel;

/**
 * Created by pouya on 6/22/18.
 */
public class SmsCodeData {
    @SerializedName("avatar")
    private String mAvatar;

    @SerializedName("channel")
    private String mChannel;

    protected SmsCodeData(String avatar, String channel) {
        mAvatar = avatar;
        mChannel = channel;
    }

    public SmsCodeData() {
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public String getChannel() {
        return mChannel;
    }

    public void setChannel(String channel) {
        mChannel = channel;
    }
}
