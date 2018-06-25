package tv.pouyaam.zarinlab.network.base;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tv.pouyaam.zarinlab.helper.LogHelper;
import tv.pouyaam.zarinlab.network.model.base.BaseRequestModel;
import tv.pouyaam.zarinlab.network.model.base.BaseResponseModel;

import java.io.IOException;
import java.util.HashMap;

abstract class NetworkBase<R extends BaseRequestModel, T extends BaseResponseModel> implements NetworkInterface<R, T> {
    private final String TAG = this.getClass().getSimpleName();

    protected HashMap<String, String> mNetworkHeaders;
    protected Call<T> mCall;

    @Override
    public void addHeaderItem(String key, String value) {
        initHeader();
        if (mNetworkHeaders.containsKey(key))
            mNetworkHeaders.remove(key);

        mNetworkHeaders.put(key, value);
    }
    @Override
    public void addHeaderItem(HashMap items) {
        initHeader();
        mNetworkHeaders.putAll(items);
    }

    @Override
    public void enqueue(Callback<T> callback) {
        mCall.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                LogHelper.log(TAG, "onResponse", response.message());
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                LogHelper.log(TAG, "onFailure", t.getMessage());
            }
        });
    }

    @Override
    public Response<T> execute() {
        try {
            return mCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initHeader() {
        if (mNetworkHeaders == null)
            mNetworkHeaders = new HashMap<>();
    }

}
