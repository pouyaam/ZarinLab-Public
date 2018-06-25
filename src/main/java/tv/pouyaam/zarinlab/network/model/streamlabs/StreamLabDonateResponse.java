package tv.pouyaam.zarinlab.network.model.streamlabs;

import com.google.gson.annotations.SerializedName;
import tv.pouyaam.zarinlab.network.model.base.BaseResponseModel;

public class StreamLabDonateResponse extends BaseResponseModel {
    @SerializedName("donation_id")
    private String mDonationId;

    public StreamLabDonateResponse(String donationId) {
        mDonationId = donationId;
    }

    public String getDonationId() {
        return mDonationId;
    }

    public void setDonationId(String donationId) {
        mDonationId = donationId;
    }
}
