package tv.pouyaam.zarinlab.network.services.transaction;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import tv.pouyaam.zarinlab.dao.token.AuthDao;
import tv.pouyaam.zarinlab.dao.token.Sql2oAuthDao;
import tv.pouyaam.zarinlab.network.base.ZarinLabProtectedService;
import tv.pouyaam.zarinlab.network.endpoint.TransactionEndPoint;
import tv.pouyaam.zarinlab.network.model.transaction.TransactionRequest;
import tv.pouyaam.zarinlab.network.model.transaction.TransactionResponse;

import java.util.HashMap;
import java.util.List;

public class TransactionService extends ZarinLabProtectedService<TransactionRequest, TransactionResponse> {
    private AuthDao mAuthDao;
    private Retrofit mRetrofit;

    private TransactionService(AuthDao authDao, Retrofit retrofit) {
        mAuthDao = authDao;
        mRetrofit = retrofit;
    }

    @Override
    public void initRetrofitCall(TransactionRequest request, HashMap<String, String> header, HashMap<String, String> query, RequestBody requestBody) {
        super.initRetrofitCall(request, header, query, requestBody);
        mCall = mRetrofit.create(TransactionEndPoint.class).getLastTransactions(mNetworkHeaders);
    }

    @Override
    public AuthDao getAuthDao() {
        return mAuthDao;
    }

    public static class Builder {
        private AuthDao autDao;
        private Retrofit retrofit;

        public Builder() {
        }

        public TransactionService build() {
            return new TransactionService(autDao, retrofit);
        }

        public Builder authDao(AuthDao authDao) {
            this.autDao = authDao;
            return this;
        }

        public Builder retrofit(Retrofit retrofit) {
            this.retrofit = retrofit;
            return this;
        }
    }
}
