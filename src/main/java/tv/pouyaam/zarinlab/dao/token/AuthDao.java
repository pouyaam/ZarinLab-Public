package tv.pouyaam.zarinlab.dao.token;

import tv.pouyaam.zarinlab.model.db.TokenModel;

import java.util.List;

/**
 * Created by pouya on 6/22/18.
 */
public interface AuthDao {
    void addToken(TokenModel tokenModel);
    void updateToken(TokenModel tokenModel);
    void deleteTokenByTokenType(TokenModel.TOKEN_TYPE token_type);
    void deleteAllTokens();
    TokenModel findTokenByType(TokenModel.TOKEN_TYPE token_type);
    List<TokenModel> findAll();
}
