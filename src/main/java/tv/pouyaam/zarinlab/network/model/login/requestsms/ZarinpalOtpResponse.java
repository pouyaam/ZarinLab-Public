package tv.pouyaam.zarinlab.network.model.login.requestsms;

import com.google.gson.annotations.SerializedName;
import tv.pouyaam.zarinlab.network.model.base.BaseResponseModel;

/**
 * Created by pouya on 6/22/18.
 */
public class ZarinpalOtpResponse extends BaseResponseModel{
    @SerializedName("data")
    private SmsCodeData mSmsCodeData;

    public ZarinpalOtpResponse(SmsCodeData smsCodeData) {
        mSmsCodeData = smsCodeData;
    }

    public ZarinpalOtpResponse() {
    }

    public SmsCodeData getSmsCodeData() {
        return mSmsCodeData;
    }

    public void setSmsCodeData(SmsCodeData smsCodeData) {
        mSmsCodeData = smsCodeData;
    }
}
