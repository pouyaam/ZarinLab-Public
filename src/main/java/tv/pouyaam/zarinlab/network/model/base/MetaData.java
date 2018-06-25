package tv.pouyaam.zarinlab.network.model.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pouya on 6/22/18.
 */
public class MetaData {
    @SerializedName("code")
    private int mCode;

    public MetaData(int code) {
        mCode = code;
    }

    public MetaData() {
    }

    public int getCode() {
        return mCode;
    }

    public void setCode(int code) {
        mCode = code;
    }
}
