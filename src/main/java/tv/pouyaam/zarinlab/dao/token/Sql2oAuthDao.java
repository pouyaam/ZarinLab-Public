package tv.pouyaam.zarinlab.dao.token;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import tv.pouyaam.zarinlab.model.db.TokenModel;

import java.util.List;

public class Sql2oAuthDao implements AuthDao {

    private final Sql2o mSql2o;

    public Sql2oAuthDao(Sql2o sql2o) {
        mSql2o = sql2o;
    }

    @Override
    public void addToken(TokenModel tokenModel) {
        String sql = "INSERT INTO TOKEN(expires_in, access_token, refresh_token, token_type) VALUES(:expiresIn, :accessToken, :refreshToken, :tokenType)";
        TokenModel temp = findTokenByType(tokenModel.getTokenType());
        if (temp == null) {
            Connection connection = mSql2o.open();
            connection.createQuery(sql)
                    .bind(tokenModel)
                    .executeUpdate();
            connection.close();

        } else {
            updateToken(tokenModel);
        }
    }

    @Override
    public void updateToken(TokenModel tokenModel) {
        deleteTokenByTokenType(tokenModel.getTokenType());
        addToken(tokenModel);
    }

    @Override
    public void deleteTokenByTokenType(TokenModel.TOKEN_TYPE tokenType) {
        try {
            Connection connection = mSql2o.open();
            connection.createQuery("DELETE TOKEN WHERE token_type = :tokenType")
                    .addColumnMapping("TOKEN_TYPE", "tokenType")
                    .addParameter("tokenType", tokenType)
                    .executeUpdate();
            connection.close();
        } catch (Sql2oException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAllTokens() {
        try {
            Connection connection = mSql2o.open();
            connection.createQuery("DELETE TOKEN").executeUpdate();
            connection.close();
        } catch (Sql2oException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<TokenModel> findAll() {
        try {
            Connection connection = mSql2o.open();
            return connection.createQuery("SELECT * FROM TOKEN")
                    .addColumnMapping("EXPIRES_IN", "expiresIn")
                    .addColumnMapping("ACCESS_TOKEN", "accessToken")
                    .addColumnMapping("REFRESH_TOKEN", "refreshToken")
                    .addColumnMapping("TOKEN_TYPE", "tokenType")
                    .executeAndFetch(TokenModel.class);
        } catch (Sql2oException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TokenModel findTokenByType(TokenModel.TOKEN_TYPE tokenType) {
        try {
            Connection connection = mSql2o.open();
            return connection.createQuery("SELECT * FROM TOKEN WHERE token_type = :tokenType")
                    .addColumnMapping("EXPIRES_IN", "expiresIn")
                    .addColumnMapping("ACCESS_TOKEN", "accessToken")
                    .addColumnMapping("REFRESH_TOKEN", "refreshToken")
                    .addColumnMapping("TOKEN_TYPE", "tokenType")
                    .addParameter("tokenType", tokenType.toString())
                    .executeAndFetchFirst(TokenModel.class);
        } catch (Sql2oException e) {
            e.printStackTrace();
        }
        return null;
    }


}
