package tv.pouyaam.zarinlab.network.streamlab.donate;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import tv.pouyaam.zarinlab.dao.token.AuthDao;
import tv.pouyaam.zarinlab.network.base.StreamlabProtectedService;
import tv.pouyaam.zarinlab.network.endpoint.StreamLabEndPoint;
import tv.pouyaam.zarinlab.network.model.streamlabs.StreamLabDonateRequest;
import tv.pouyaam.zarinlab.network.model.streamlabs.StreamLabDonateResponse;

import java.util.HashMap;

public class StreamLabDonateService extends StreamlabProtectedService<StreamLabDonateRequest, StreamLabDonateResponse> {

    private Retrofit mRetrofit;
    private AuthDao mAuthDao;

    private StreamLabDonateService(Retrofit retrofit, AuthDao authDao) {
        mRetrofit = retrofit;
        mAuthDao = authDao;
    }

    @Override
    public void initRetrofitCall(StreamLabDonateRequest request, HashMap<String, String> header, HashMap<String, String> query, RequestBody requestBody) {
        super.initRetrofitCall(request, header, query, requestBody);
        mCall = mRetrofit.create(StreamLabEndPoint.class).donate(mQuery);
    }

    @Override
    public AuthDao getAuthDao() {
        return mAuthDao;
    }

    public static class Builder {
        private Retrofit retrofit;
        private AuthDao authDao;

        public Builder() {
        }

        public StreamLabDonateService build() {
            return new StreamLabDonateService(retrofit, authDao);
        }

        public Builder retrofit(Retrofit retrofit) {
            this.retrofit = retrofit;
            return this;
        }

        public Builder authDao(AuthDao authDao) {
            this.authDao = authDao;
            return this;
        }
    }
}
