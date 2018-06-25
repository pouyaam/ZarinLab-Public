package tv.pouyaam.zarinlab.network.model.transaction;

import com.google.gson.annotations.SerializedName;

public class TransactionData {
    @SerializedName("public_id")
    private long mPublicId;

    @SerializedName("amount")
    private int mAmount;

    @SerializedName("balance")
    private int mBalance;

    @SerializedName("created")
    private String mCreated;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("confirmed")
    private String mConfirmed;

    @SerializedName("from_user")
    private FromUserModel mFromUser;

    public TransactionData(long publicId, int amount, int balance, String created, String description, String confirmed, FromUserModel fromUser) {
        mPublicId = publicId;
        mAmount = amount;
        mBalance = balance;
        mCreated = created;
        mDescription = description;
        mConfirmed = confirmed;
        mFromUser = fromUser;
    }

    public TransactionData() {
    }

    public long getPublicId() {
        return mPublicId;
    }

    public int getAmount() {
        return mAmount;
    }

    public int getBalance() {
        return mBalance;
    }

    public String getCreated() {
        return mCreated;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getConfirmed() {
        return mConfirmed;
    }

    public FromUserModel getFromUser() {
        return mFromUser;
    }
}
