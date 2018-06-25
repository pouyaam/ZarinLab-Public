package tv.pouyaam.zarinlab.network.services.login;

import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tv.pouyaam.zarinlab.model.constants.Constant;

public class ZarinpalRequestCodeServiceTest {
    private Retrofit mRetrofit;
    private Gson mGson;
    @Before
    public void setUp() throws Exception {
        mGson = new Gson();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_ZARINPAL_URL)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void requestSmsForSpecificNumber() throws Exception {

    }
}