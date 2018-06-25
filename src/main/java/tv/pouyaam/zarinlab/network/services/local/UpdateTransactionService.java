package tv.pouyaam.zarinlab.network.services.local;

import okhttp3.RequestBody;
import retrofit2.Retrofit;
import tv.pouyaam.zarinlab.network.base.ZarinLabPublicService;
import tv.pouyaam.zarinlab.network.endpoint.TransactionEndPoint;
import tv.pouyaam.zarinlab.network.model.local.UpdateTransactionRequest;
import tv.pouyaam.zarinlab.network.model.local.UpdateTransactionResponse;

import java.util.HashMap;

public class UpdateTransactionService extends ZarinLabPublicService<UpdateTransactionRequest, UpdateTransactionResponse> {
    private Retrofit mRetrofit;

    private UpdateTransactionService(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    @Override
    public void initRetrofitCall(UpdateTransactionRequest request, HashMap<String, String> header, HashMap<String, String> query, RequestBody requestBody) {
        mCall = mRetrofit.create(TransactionEndPoint.class).updateTransactions();
    }

    public static class Builder {
        private Retrofit retrofit;

        public Builder() {
        }

        public UpdateTransactionService build() {
            return new UpdateTransactionService(retrofit);
        }

        public Builder retrofit(Retrofit retrofit) {
            this.retrofit = retrofit;
            return this;
        }
    }
}
