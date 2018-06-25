package tv.pouyaam.zarinlab.network.base;

import tv.pouyaam.zarinlab.network.model.base.BaseRequestModel;
import tv.pouyaam.zarinlab.network.model.base.BaseResponseModel;

import java.util.HashMap;

public abstract class ZarinLabPublicService<R extends BaseRequestModel, T extends BaseResponseModel> extends NetworkBase<R, T> {

}
