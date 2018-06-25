package tv.pouyaam.zarinlab.dao.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import tv.pouyaam.zarinlab.dao.token.Sql2oAuthDao;
import tv.pouyaam.zarinlab.model.db.TokenModel;

import static org.junit.Assert.*;

public class Sql2oAuthDaoTest {
    private Sql2oAuthDao mSql2oAuthDao;
    private Connection mConnection;
    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        mSql2oAuthDao = new Sql2oAuthDao(sql2o);

        mConnection = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        mConnection.close();
    }

    @Test
    public void createATokenInDatabase() throws Exception {
        TokenModel tokenModel = newTokenModel(1000, "POUYA", "REFRESH_POUYA", TokenModel.TOKEN_TYPE.ZARINPAL);

        mSql2oAuthDao.addToken(tokenModel);

        TokenModel temp = mSql2oAuthDao.findTokenByType(TokenModel.TOKEN_TYPE.ZARINPAL);

        assertNotNull(temp);
    }

    @Test
    public void createTwiTokenInDatabase() throws Exception {
        TokenModel tokenModel = newTokenModel(1000, "POUYA", "REFRESH_POUYA", TokenModel.TOKEN_TYPE.ZARINPAL);
        TokenModel steramlabModel = newTokenModel(2000, "POUYAA", "REFRESH_POUYAA", TokenModel.TOKEN_TYPE.STREAMLABS);

        mSql2oAuthDao.addToken(tokenModel);
        mSql2oAuthDao.addToken(steramlabModel);

        TokenModel temp = mSql2oAuthDao.findTokenByType(TokenModel.TOKEN_TYPE.ZARINPAL);
        TokenModel temp2 = mSql2oAuthDao.findTokenByType(TokenModel.TOKEN_TYPE.STREAMLABS);

        assertNotEquals(temp, temp2);
    }

    @Test
    public void updateToken() throws Exception {
        TokenModel tokenModel = newTokenModel(1000, "POUYA", "REFRESH_POUYA", TokenModel.TOKEN_TYPE.ZARINPAL);
        mSql2oAuthDao.addToken(tokenModel);

        TokenModel newTokenModel = newTokenModel(1002, "POUYA2", "REFRESH_POUYA2", TokenModel.TOKEN_TYPE.ZARINPAL);
        mSql2oAuthDao.addToken(newTokenModel);

        TokenModel temp = mSql2oAuthDao.findTokenByType(tokenModel.getTokenType());

        assertNotNull(temp);
        assertNotEquals(temp.getAccessToken(), "POUYA");
        assertNotEquals(tokenModel, newTokenModel);

    }

    @Test
    public void deleteTokenFromDatabase() throws Exception {
        TokenModel tokenModel = newTokenModel(1000, "POUYA", "REFRESH_POUYA", TokenModel.TOKEN_TYPE.ZARINPAL);

        mSql2oAuthDao.addToken(tokenModel);
        mSql2oAuthDao.deleteTokenByTokenType(TokenModel.TOKEN_TYPE.ZARINPAL);
        TokenModel temp = mSql2oAuthDao.findTokenByType(TokenModel.TOKEN_TYPE.ZARINPAL);

        assertNull(temp);

    }

    private TokenModel newTokenModel(int expiresIn, String token, String refreshToken, TokenModel.TOKEN_TYPE tokenType) {
        return new TokenModel(expiresIn, token, refreshToken, tokenType);
    }
}