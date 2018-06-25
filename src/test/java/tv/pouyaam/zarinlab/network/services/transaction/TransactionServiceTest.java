package tv.pouyaam.zarinlab.network.services.transaction;

import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tv.pouyaam.zarinlab.dao.token.Sql2oAuthDao;
import tv.pouyaam.zarinlab.model.constants.Constant;
import tv.pouyaam.zarinlab.model.db.TokenModel;
import tv.pouyaam.zarinlab.network.model.transaction.TransactionRequest;
import tv.pouyaam.zarinlab.network.model.transaction.TransactionResponse;

import static org.junit.Assert.*;

public class TransactionServiceTest {
    private Gson mGson;
    private Retrofit mRetrofit;
    private Sql2oAuthDao mSql2oAuthDao;
    private Connection mConnection;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        mSql2oAuthDao = new Sql2oAuthDao(sql2o);

        mGson = new Gson();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_ZARINPAL_URL)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();

        mConnection = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        mConnection.close();
    }

    @Test
    public void transactionMustBeRetrievedSuccessfully() throws Exception {
        TokenModel tokenModel
                = new TokenModel(1000, "   ", "", TokenModel.TOKEN_TYPE.ZARINPAL);
        mSql2oAuthDao.addToken(tokenModel);

        TransactionService transactionService =
                new TransactionService.Builder()
                .authDao(mSql2oAuthDao)
                .retrofit(mRetrofit).build();

        transactionService.initRetrofitCall(new TransactionRequest.Builder().build(), null, null, null);

        Response<TransactionResponse> response = transactionService.execute();

        assertEquals(200, response.body().getMetaData().getCode());
        assertNotEquals(0, response.body().mData.size());
    }

    @Test
    public void transactionAccessTokenInvalid() throws Exception {

        TokenModel tokenModel
                = new TokenModel(1000, "    ", "", TokenModel.TOKEN_TYPE.ZARINPAL);
        mSql2oAuthDao.addToken(tokenModel);

        TransactionService transactionService =
                new TransactionService.Builder()
                        .authDao(mSql2oAuthDao)
                        .retrofit(mRetrofit).build();

        transactionService.initRetrofitCall(new TransactionRequest.Builder().build(), null, null, null);

        Response<TransactionResponse> response = transactionService.execute();

        assertEquals(400, response.code());
    }
}