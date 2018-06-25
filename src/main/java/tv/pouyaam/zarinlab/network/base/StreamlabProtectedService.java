package tv.pouyaam.zarinlab.network.base;

import okhttp3.RequestBody;
import tv.pouyaam.zarinlab.dao.token.AuthDao;
import tv.pouyaam.zarinlab.model.db.TokenModel;
import tv.pouyaam.zarinlab.network.model.base.BaseRequestModel;
import tv.pouyaam.zarinlab.network.model.base.BaseResponseModel;

import java.util.HashMap;
import java.util.List;

public abstract class StreamlabProtectedService<R extends BaseRequestModel, T extends BaseResponseModel> extends NetworkBase<R, T> {
    protected HashMap<String, String> mQuery = new HashMap<>();

    @Override
    public void initRetrofitCall(R request, HashMap<String, String> header, HashMap<String, String> query, RequestBody requestBody) {
        initHeader();
        if (query != null)
            mQuery.putAll(query);
    }

    private void initHeader() {
        TokenModel tokenModel = getAuthDao().findTokenByType(TokenModel.TOKEN_TYPE.STREAMLABS);
        if (tokenModel != null)
            mQuery.put("access_token", tokenModel.getAccessToken());
    }

    public abstract AuthDao getAuthDao();
}
