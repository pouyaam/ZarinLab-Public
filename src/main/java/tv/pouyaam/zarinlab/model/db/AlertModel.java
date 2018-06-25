package tv.pouyaam.zarinlab.model.db;

import com.google.gson.annotations.SerializedName;

public class AlertModel {
    private long id;
    private long alertId;
    private long publicId;
    private long createdTimestamp;

    public AlertModel(long alertId, long publicId, long createdTimestamp) {
        this.alertId = alertId;
        this.publicId = publicId;
        this.createdTimestamp = createdTimestamp;
    }

    public long getAlertId() {
        return alertId;
    }

    public void setAlertId(long alertId) {
        this.alertId = alertId;
    }

    public long getPublicId() {
        return publicId;
    }

    public void setPublicId(long publicId) {
        this.publicId = publicId;
    }

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
