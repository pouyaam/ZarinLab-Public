package tv.pouyaam.zarinlab.network.streamlab.token;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import tv.pouyaam.zarinlab.dao.token.AuthDao;
import tv.pouyaam.zarinlab.network.base.StreamlabProtectedService;
import tv.pouyaam.zarinlab.network.endpoint.StreamLabEndPoint;
import tv.pouyaam.zarinlab.network.model.streamlabs.StreamLabTokenRequest;
import tv.pouyaam.zarinlab.network.model.streamlabs.StreamLabTokenResponse;

import java.util.HashMap;
import java.util.List;

public class StreamLabTokenService extends StreamlabProtectedService<StreamLabTokenRequest, StreamLabTokenResponse> {
    private Retrofit mRetrofit;
    private AuthDao mAuthDao;

    private StreamLabTokenService(Retrofit retrofit, AuthDao authDao) {
        mRetrofit = retrofit;
        mAuthDao = authDao;
    }

    @Override
    public void initRetrofitCall(StreamLabTokenRequest request, HashMap<String, String> header, HashMap<String, String> query, RequestBody requestBody) {
        mCall = mRetrofit.create(StreamLabEndPoint.class).getAccessToken(query);
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

        public StreamLabTokenService build() {
            return new StreamLabTokenService(retrofit, authDao);
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
