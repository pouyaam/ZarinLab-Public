package tv.pouyaam.zarinlab.controller;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import tv.pouyaam.zarinlab.dao.token.AuthDao;
import tv.pouyaam.zarinlab.excepiton.ApiError;
import tv.pouyaam.zarinlab.helper.StringHelper;
import tv.pouyaam.zarinlab.manager.ConfigurationManager;
import tv.pouyaam.zarinlab.model.db.TokenModel;
import tv.pouyaam.zarinlab.network.model.login.requestsms.SmsCodeRequest;
import tv.pouyaam.zarinlab.network.model.login.requestsms.ZarinpalOtpResponse;
import tv.pouyaam.zarinlab.network.model.login.verifyphone.VerifyPhoneData;
import tv.pouyaam.zarinlab.network.model.login.verifyphone.ZarinpalOtpVerifyRequest;
import tv.pouyaam.zarinlab.network.model.login.verifyphone.ZarinpalOtpVerifyResponse;
import tv.pouyaam.zarinlab.network.model.streamlabs.StreamLabTokenRequest;
import tv.pouyaam.zarinlab.network.model.streamlabs.StreamLabTokenResponse;
import tv.pouyaam.zarinlab.network.services.login.ZarinpalRequestCodeService;
import tv.pouyaam.zarinlab.network.services.login.VerifyPhoneService;
import tv.pouyaam.zarinlab.network.streamlab.token.StreamLabTokenService;

import java.util.HashMap;
import java.util.Map;


/**
 * LoginController
 */
public class LoginController extends BaseController{

    /**
     *
     * @param request - Spark request
     * @param response - Spark response
     * @param authDao - Authencation Dao Implementation
     */
    public LoginController(Request request, Response response, AuthDao authDao, ConfigurationManager configurationManager) {
        super(request, response, authDao, configurationManager);
    }

    /**
     * @return - Login page
     */
    public ModelAndView index() {
        Map<String, String> model = new HashMap<>();
        String username = mRequest.queryParams("username");

        if (mAuthDao.findTokenByType(TokenModel.TOKEN_TYPE.ZARINPAL) == null) {
            model.put("isNotZarinPalConnected", "isNotZarinPalConnected");
            if (username == null || username.length() == 0)
                model.put("isNotSmsSent", "isNotStreamlabConnected");
            else {
                model.put("username", username);
            }
        }

        if (mAuthDao.findTokenByType(TokenModel.TOKEN_TYPE.STREAMLABS) == null)
            model.put("isNotStreamlabConnected", "isNotStreamlabConnected");

        return new ModelAndView(model, "login.hbs");
    }

    /**
     * Redirecting to Streamlab authorization page
     * @return - A useless string
     */
    public String streamlabAuthorization() {
        redirect("https://www.streamlabs.com/api/v1.0/authorize?client_id=client_id&redirect_uri=redirect_uri&response_type=code&scope=scope");
        return "Connecting";
    }

    /**
     *  Connects to Streamlab access token api generation after user has authorized ZarinLab Application. Then Save
     *  Streamlab access token in database with STREAMLAB key
     *
     * @param streamLabTokenService - Streamlab's Access token API Generation Service
     * @return - A Simple String
     */
    public String streamlabOAuth(StreamLabTokenService streamLabTokenService) {
        String code = mRequest.queryParams("code");
        HashMap<String, String> params = new HashMap<>();

        params.put("grant_type", "authorization_code");
        params.put("client_id", "CLIENT ID");
        params.put("client_secret", "CLIENT SECRET");
        params.put("redirect_uri", "REDIRECT URI");
        params.put("code", code);


        streamLabTokenService.initRetrofitCall(new StreamLabTokenRequest(), null, params, null);
        retrofit2.Response<StreamLabTokenResponse> response = streamLabTokenService.execute();
        mResponse.type("application/json");
        if (response.code() == 200) {
            StreamLabTokenResponse streamLabTokenResponse = response.body();
            TokenModel tokenModel = new TokenModel(
                    streamLabTokenResponse.getExpiresIn(),
                    streamLabTokenResponse.getAccessToken(),
                    streamLabTokenResponse.getRefreshToken(),
                    TokenModel.TOKEN_TYPE.STREAMLABS
            );
            mAuthDao.addToken(tokenModel);
            mResponse.redirect("/login");
            return "Connecting";
        } else {
            throw new ApiError(response.code(), response.message());
        }
    }

    /**
     * @param zarinpalRequestCodeService - Zarinpal OTP code generation service
     * @return - redirect to login page with phonenumber if
     */
    public String zarinpalRequestCode(ZarinpalRequestCodeService zarinpalRequestCodeService) {
        String username = mRequest.queryParams("username");
        String channel = StringHelper.isItEmail(username) ? "CHANNEL" : "CHANNEL";

        SmsCodeRequest smsCodeRequest = new SmsCodeRequest.Builder().username(username).channel(channel).build();
        zarinpalRequestCodeService.initRetrofitCall(smsCodeRequest, null, null, null);
        retrofit2.Response<ZarinpalOtpResponse> response = zarinpalRequestCodeService.execute();

        if (response.code() == 200) {
            redirect("/login?username=" + username);
        } else {
            throw new ApiError(response.code(), response.message());
        }

        return null;
    }

    /**
     *  If OTP is valid, the given zarinpal access_token is saved in database with ZARINPAL type
     * @param verifyPhoneService - ZarinPal OTP Verification Service
     * @return - redirect to login page if it's successful
     */
    public ZarinpalOtpVerifyResponse zarinpalVerifyOtp(VerifyPhoneService verifyPhoneService) {
        String otp = mRequest.queryParams("otp");
        String username = mRequest.queryParams("username");

        ZarinpalOtpVerifyRequest zarinpalOtpVerifyRequest
                = new ZarinpalOtpVerifyRequest.Builder().otp(otp).username(username).build();

        verifyPhoneService.initRetrofitCall(zarinpalOtpVerifyRequest, null, null, null);
        retrofit2.Response<ZarinpalOtpVerifyResponse> response = verifyPhoneService.execute();

        if (response.code() == 200 && response.body() != null) {
            VerifyPhoneData verifyPhoneData = response.body().getVerifyPhoneData();
            TokenModel tokenModel = new TokenModel((int) verifyPhoneData.getExpiresIn(),
                    verifyPhoneData.getAccessToken(),
                    verifyPhoneData.getRefreshToken(), TokenModel.TOKEN_TYPE.ZARINPAL);

            mAuthDao.addToken(tokenModel);


            redirect("/login");
        } else {
            throw new ApiError(response.code(), response.message());
        }

        return response.body();
    }

    /**
     * delete all tokens and redirect to login page
     * @return - a useless string
     */
    public String logout() {
        mAuthDao.deleteAllTokens();
        redirect("/login");
        return "logout";
    }

    protected void redirect(String url) {
        mResponse.redirect(url);
    }
}
