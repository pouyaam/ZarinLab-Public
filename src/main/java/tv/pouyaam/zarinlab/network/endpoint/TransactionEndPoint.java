package tv.pouyaam.zarinlab.network.endpoint;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import tv.pouyaam.zarinlab.network.model.local.UpdateTransactionResponse;
import tv.pouyaam.zarinlab.network.model.transaction.TransactionResponse;

import java.util.HashMap;

public interface TransactionEndPoint {
    @GET("TRANSACTION ROUTE")
    Call<TransactionResponse> getLastTransactions(@HeaderMap HashMap<String, String> header);

    @GET("transactions/update")
    Call<UpdateTransactionResponse> updateTransactions();
}
