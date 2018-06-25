package tv.pouyaam.zarinlab.network.services.login;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import tv.pouyaam.zarinlab.network.base.ZarinLabPublicService;
import tv.pouyaam.zarinlab.network.endpoint.LoginEndPoint;
import tv.pouyaam.zarinlab.network.model.login.verifyphone.ZarinpalOtpVerifyRequest;
import tv.pouyaam.zarinlab.network.model.login.verifyphone.ZarinpalOtpVerifyResponse;

import java.util.HashMap;

public class VerifyPhoneService extends ZarinLabPublicService<ZarinpalOtpVerifyRequest, ZarinpalOtpVerifyResponse> {
    private Retrofit mRetrofit;

    private VerifyPhoneService(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    @Override
    public void initRetrofitCall(ZarinpalOtpVerifyRequest request, HashMap<String, String> header, HashMap<String, String> query, RequestBody requestBody) {
        mCall = mRetrofit.create(LoginEndPoint.class).verifyZarinpalOtp(request);
    }

    public static class Builder {
        private Retrofit retrofit;

        public Builder() {
        }

        public VerifyPhoneService build() {
            return new VerifyPhoneService(retrofit);
        }

        public Builder retrofit(Retrofit retrofit) {
            this.retrofit = retrofit;
            return this;
        }
    }
}
