package tv.pouyaam.zarinlab.network.model.local;

import com.google.gson.annotations.SerializedName;
import tv.pouyaam.zarinlab.model.db.TransactionModel;
import tv.pouyaam.zarinlab.network.model.base.BaseResponseModel;

import java.util.List;

public class UpdateTransactionResponse extends BaseResponseModel {

    @SerializedName("transactions")
    private List<TransactionModel> mTransactionModels;

    public UpdateTransactionResponse(List<TransactionModel> transactionModels) {
        mTransactionModels = transactionModels;
    }

    public List<TransactionModel> getTransactionModels() {
        return mTransactionModels;
    }

    public void setTransactionModels(List<TransactionModel> transactionModels) {
        mTransactionModels = transactionModels;
    }
}
