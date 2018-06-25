package tv.pouyaam.zarinlab.model.db;

import tv.pouyaam.zarinlab.helper.TimeHelper;
import tv.pouyaam.zarinlab.network.model.base.BaseResponseModel;

import java.text.ParseException;

public class TransactionModel extends BaseResponseModel {
    private int id;
    private int amount;
    private long publicId;
    private long createdTimestamp;
    private String created;
    private String description;
    private String fromUser;

    public TransactionModel(int amount, long publicId, String created, String description, String fromUser) {
        init(amount, publicId, created, description);
        this.fromUser = fromUser;

    }

    public TransactionModel(int amount, long publicId, String created, String description) {
        init(amount, publicId, created, description);
    }

    public TransactionModel(int amount, long publicId, long createdTimestamp, String created, String description) {
        this.amount = amount;
        this.publicId = publicId;
        this.createdTimestamp = createdTimestamp;
        this.created = created;
        this.description = description;
        this.fromUser = "Guest";
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionModel that = (TransactionModel) o;

        if (id != that.id) return false;
        if (amount != that.amount) return false;
        if (publicId != that.publicId) return false;
        return createdTimestamp == that.createdTimestamp && created.equals(that.created) && (description != null ? description.equals(that.description) : that.description == null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + amount;
        result = 31 * result + (int) (publicId ^ (publicId >>> 32));
        result = 31 * result + (int) (createdTimestamp ^ (createdTimestamp >>> 32));
        result = 31 * result + created.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public int getId() {
        return id;
    }

    public long getPublicId() {
        return publicId;
    }

    public int getAmount() {
        return amount;
    }

    public String getCreated() {
        return created;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPublicId(long publicId) {
        this.publicId = publicId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    private void init(int amount, long publicId, String created, String description) {
        this.amount = amount;
        this.publicId = publicId;
        this.created = created;
        this.description = description;
        try {
            this.createdTimestamp = TimeHelper.convertDateToMsec(created, "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            this.createdTimestamp = 0;
        }
    }

}
