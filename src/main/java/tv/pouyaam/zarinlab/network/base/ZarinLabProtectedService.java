package tv.pouyaam.zarinlab.network.base;

import okhttp3.RequestBody;
import tv.pouyaam.zarinlab.dao.token.AuthDao;
import tv.pouyaam.zarinlab.model.db.TokenModel;
import tv.pouyaam.zarinlab.network.model.base.BaseRequestModel;
import tv.pouyaam.zarinlab.network.model.base.BaseResponseModel;

import java.util.HashMap;
import java.util.List;

public abstract class ZarinLabProtectedService<R extends BaseRequestModel, T extends BaseResponseModel>
        extends ZarinLabPublicService<R, T> {

    @Override
    public void initRetrofitCall(R request, HashMap<String, String> header, HashMap<String, String> query, RequestBody requestBody) {
        initCookieToken();
        header = mNetworkHeaders;
    }

    private void initCookieToken() {
        TokenModel tokenModel = getAuthDao().findTokenByType(TokenModel.TOKEN_TYPE.ZARINPAL);
        if (tokenModel != null)
            addHeaderItem("Cookie", "access_token=" + tokenModel.getAccessToken());
    }

    public abstract AuthDao getAuthDao();
}
