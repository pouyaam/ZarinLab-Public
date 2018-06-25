package tv.pouyaam.zarinlab.network.services.login;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import tv.pouyaam.zarinlab.network.base.ZarinLabPublicService;
import tv.pouyaam.zarinlab.network.endpoint.LoginEndPoint;
import tv.pouyaam.zarinlab.network.model.login.requestsms.SmsCodeRequest;
import tv.pouyaam.zarinlab.network.model.login.requestsms.ZarinpalOtpResponse;

import java.util.HashMap;

public class ZarinpalRequestCodeService extends ZarinLabPublicService<SmsCodeRequest, ZarinpalOtpResponse> {
    private Retrofit mRetrofit;

    private ZarinpalRequestCodeService(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    @Override
    public void initRetrofitCall(SmsCodeRequest request,
                                                  HashMap<String, String> header,
                                                  HashMap<String, String> query, RequestBody requestBody) {
        mCall = mRetrofit.create(LoginEndPoint.class).requestZarinpalOtp(request);
    }

    public static class Builder {
        private Retrofit retrofit;

        public ZarinpalRequestCodeService build() {
            return new ZarinpalRequestCodeService(retrofit);
        }

        public Builder retrofit(Retrofit retrofit) {
            this.retrofit = retrofit;
            return this;
        }
    }
}
