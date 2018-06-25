package tv.pouyaam.zarinlab.network.endpoint;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import tv.pouyaam.zarinlab.network.model.streamlabs.StreamLabDonateResponse;
import tv.pouyaam.zarinlab.network.model.streamlabs.StreamLabTokenResponse;

import java.util.Map;

public interface StreamLabEndPoint {
    @POST("api/v1.0/token")
    @FormUrlEncoded
    Call<StreamLabTokenResponse> getAccessToken(@FieldMap Map<String, String> params);

    @POST("api/v1.0/donations")
    @FormUrlEncoded
    Call<StreamLabDonateResponse> donate(@FieldMap Map<String, String> query);
}
