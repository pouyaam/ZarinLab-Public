package tv.pouyaam.zarinlab.network.endpoint;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import tv.pouyaam.zarinlab.network.model.login.requestsms.SmsCodeRequest;
import tv.pouyaam.zarinlab.network.model.login.requestsms.ZarinpalOtpResponse;
import tv.pouyaam.zarinlab.network.model.login.verifyphone.ZarinpalOtpVerifyRequest;
import tv.pouyaam.zarinlab.network.model.login.verifyphone.ZarinpalOtpVerifyResponse;

/**
 * Created by pouya on 6/22/18.
 */
public interface LoginEndPoint {
    @POST("LOGIN ROUTE")
    Call<ZarinpalOtpResponse> requestZarinpalOtp(@Body SmsCodeRequest smsCodeRequest);

    @POST("LOGIN ROUTE")
    Call<ZarinpalOtpVerifyResponse> verifyZarinpalOtp(@Body ZarinpalOtpVerifyRequest zarinpalOtpVerifyRequest);
}
