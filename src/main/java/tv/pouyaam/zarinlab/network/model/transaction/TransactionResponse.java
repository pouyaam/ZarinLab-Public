package tv.pouyaam.zarinlab.network.model.transaction;

import com.google.gson.annotations.SerializedName;
import tv.pouyaam.zarinlab.network.model.base.BaseResponseModel;

import java.util.List;

public class TransactionResponse extends BaseResponseModel {
    @SerializedName("data")
    public List<TransactionData> mData;

    public List<TransactionData> getData() {
        return mData;
    }
}
