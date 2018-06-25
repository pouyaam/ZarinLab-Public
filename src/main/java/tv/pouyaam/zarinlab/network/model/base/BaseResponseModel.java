package tv.pouyaam.zarinlab.network.model.base;

import com.google.gson.annotations.SerializedName;

public abstract class BaseResponseModel {
    @SerializedName("meta")

    private MetaData mMetaData;

    public MetaData getMetaData() {
        return mMetaData;
    }
}
