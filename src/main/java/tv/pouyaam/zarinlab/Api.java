package tv.pouyaam.zarinlab;

import com.google.gson.Gson;
import org.sql2o.Sql2o;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import spark.template.handlebars.HandlebarsTemplateEngine;
import tv.pouyaam.zarinlab.controller.LoginController;
import tv.pouyaam.zarinlab.controller.TransactionController;
import tv.pouyaam.zarinlab.dao.alert.Sql2oAlertDao;
import tv.pouyaam.zarinlab.dao.token.AuthDao;
import tv.pouyaam.zarinlab.dao.token.Sql2oAuthDao;
import tv.pouyaam.zarinlab.dao.transaction.Sql2oTransactionDao;
import tv.pouyaam.zarinlab.excepiton.ApiError;
import tv.pouyaam.zarinlab.manager.ConfigurationManager;
import tv.pouyaam.zarinlab.model.constants.Constant;
import tv.pouyaam.zarinlab.model.db.TokenModel;
import tv.pouyaam.zarinlab.network.services.local.UpdateTransactionService;
import tv.pouyaam.zarinlab.network.services.login.VerifyPhoneService;
import tv.pouyaam.zarinlab.network.services.login.ZarinpalRequestCodeService;
import tv.pouyaam.zarinlab.network.services.transaction.TransactionService;
import tv.pouyaam.zarinlab.network.streamlab.donate.StreamLabDonateService;
import tv.pouyaam.zarinlab.network.streamlab.token.StreamLabTokenService;

import java.util.HashMap;

import static spark.Spark.*;

public class Api {

    private static ZarinpalRequestCodeService sZarinpalRequestCodeService;
    private static VerifyPhoneService sVerifyPhoneService;
    private static TransactionService sTransactionService;
    private static StreamLabTokenService sStreamLabTokenService;
    private static StreamLabDonateService sStreamLabDonateService;
    private static UpdateTransactionService sUpdateTransactionService;
    private static Sql2oAlertDao sAlertDao;
    private static Sql2oTransactionDao sTransactionDao;
    private static Sql2oAuthDao sAuthDao;
    private static ConfigurationManager sConfigurationManager;

    public static void main(String[] args) {

        staticFileLocation("/public");
        sConfigurationManager = ConfigurationManager.with();

        initDatasource(args);
        Gson gson = initServices();

        initBeforeRoutes();

        initRoutes(gson);

        exception(ApiError.class, (exc, req, res) -> {
            ApiError err = (ApiError) exc;
            HashMap<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatus());
            jsonMap.put("errorMessage", err.getMessage());

            res.type("application/json");
            res.status(err.getStatus());

            res.body(gson.toJson(jsonMap));
        });
    }

    private static void initRoutes(Gson gson) {
        get("/", (req, res) -> {
            res.redirect("/login");
            return "redirecting";
        });

        get("/logout", (req, res) -> new LoginController(req, res, sAuthDao, sConfigurationManager).logout());

        get("/login",
                (req, res) -> new LoginController(req, res, sAuthDao, sConfigurationManager).index(),
                new HandlebarsTemplateEngine());

        get("/oauth",
                (req, res) -> new LoginController(req, res, sAuthDao,
                        sConfigurationManager).streamlabOAuth(sStreamLabTokenService));

        post("/login/streamlabs/authorization",
                (req, res) -> new LoginController(req, res, sAuthDao, sConfigurationManager).streamlabAuthorization());

        post("/login/otp/request",
                (req, res) -> new LoginController(req, res, sAuthDao, sConfigurationManager)
                        .zarinpalRequestCode(sZarinpalRequestCodeService));

        post("/login/otp/verify",
                (req, res) -> new LoginController(req, res, sAuthDao, sConfigurationManager)
                        .zarinpalVerifyOtp(sVerifyPhoneService));

        get("/transactions/update", "application/json", (req, res) ->
                new TransactionController(req, res, sAuthDao, sTransactionDao, sAlertDao, sConfigurationManager)
                        .update(sTransactionService, sStreamLabDonateService),
                gson::toJson);

        get("/transactions",
                (req, res) -> new TransactionController(req, res, sUpdateTransactionService, sConfigurationManager)
                        .index(),
                new HandlebarsTemplateEngine());

        get("/alerts", "application/json", (req, res) -> {
            res.type("application/json");
            return sAlertDao.findAll();
        }, gson::toJson);
    }

    private static void initBeforeRoutes() {
        before("/login", (req, res) -> beforeLoginDo(sAuthDao, res));

        before("/transactions", (req, res) -> beforeTransactionDo(sAuthDao, res));

        before("/streamlabs/*", (req, res) -> beforeStreamLabsDo(sAuthDao, res));
    }

    private static void initDatasource(String[] args) {
        String datasource = "jdbc:h2:./" + sConfigurationManager.readZarinpalDbName();
        port(sConfigurationManager.readPort());

        Sql2o sql2o = new Sql2o(String.format("%s;INIT=RUNSCRIPT from 'classpath:db/init.sql'", datasource), "", "");

        sAuthDao = new Sql2oAuthDao(sql2o);
        sTransactionDao = new Sql2oTransactionDao(sql2o);
        sAlertDao = new Sql2oAlertDao(sql2o);
    }

    private static Gson initServices() {
        Gson gson = new Gson();
        Retrofit retrofit = provideRetrofit(Constant.BASE_ZARINPAL_URL, gson);
        Retrofit retrofitStreamlab = provideRetrofit(Constant.BASE_STREAMLAB_URL, gson);
        Retrofit local = provideRetrofit("http://localhost:" + sConfigurationManager.readPort(), gson);

        sZarinpalRequestCodeService
                = new ZarinpalRequestCodeService.Builder().retrofit(retrofit).build();
        sVerifyPhoneService
                = new VerifyPhoneService.Builder().retrofit(retrofit).build();
        sTransactionService
                = new TransactionService.Builder().retrofit(retrofit).authDao(sAuthDao).build();
        sStreamLabTokenService
                = new StreamLabTokenService.Builder().retrofit(retrofitStreamlab).authDao(sAuthDao).build();
        sStreamLabDonateService
                = new StreamLabDonateService.Builder().retrofit(retrofitStreamlab).authDao(sAuthDao).build();
        sUpdateTransactionService
                = new UpdateTransactionService.Builder().retrofit(local).build();

        return gson;
    }


    private static void beforeStreamLabsDo(AuthDao authDao, spark.Response res) {
        if (authDao.findTokenByType(TokenModel.TOKEN_TYPE.STREAMLABS) == null)
            res.redirect("/login");
    }

    private static void beforeTransactionDo(AuthDao authDao, spark.Response res) {
        if (authDao.findTokenByType(TokenModel.TOKEN_TYPE.ZARINPAL) == null)
            res.redirect("/login");
    }

    private static void beforeLoginDo(AuthDao authDao, spark.Response res) {
        if (authDao.findTokenByType(TokenModel.TOKEN_TYPE.ZARINPAL) != null
                && authDao.findTokenByType(TokenModel.TOKEN_TYPE.STREAMLABS) != null)
            res.redirect("/transactions");
    }

    private static Retrofit provideRetrofit(String baseUrl, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }



}
