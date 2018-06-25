package tv.pouyaam.zarinlab.network.base;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tv.pouyaam.zarinlab.network.model.base.BaseRequestModel;
import tv.pouyaam.zarinlab.network.model.base.BaseResponseModel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by pouya on 6/22/18.
 */
public interface NetworkInterface<R extends BaseRequestModel, T extends BaseResponseModel> {
    void addHeaderItem(String key, String value);
    void addHeaderItem(HashMap<String, String> items);
    void enqueue(Callback<T> callback);
    void initRetrofitCall(R request, HashMap<String, String> header, HashMap<String, String> query, RequestBody requestBody);

    Response<T> execute();


}
