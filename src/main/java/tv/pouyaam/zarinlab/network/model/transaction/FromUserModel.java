package tv.pouyaam.zarinlab.network.model.transaction;

import com.google.gson.annotations.SerializedName;

public class FromUserModel {
    @SerializedName("name")
    private String mName;

    public FromUserModel(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
