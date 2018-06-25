package tv.pouyaam.zarinlab.controller;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import tv.pouyaam.zarinlab.dao.alert.AlertDao;
import tv.pouyaam.zarinlab.dao.token.AuthDao;
import tv.pouyaam.zarinlab.dao.transaction.TransactionDao;
import tv.pouyaam.zarinlab.manager.ConfigurationManager;

abstract class BaseController {
    protected Request mRequest;
    protected Response mResponse;
    protected AuthDao mAuthDao;
    protected TransactionDao mTransactionDao;
    protected AlertDao mAlertDao;
    protected ConfigurationManager mConfigurationManager;

    public BaseController(Request request, Response response, ConfigurationManager configurationManager) {
        initCommons(request, response, configurationManager);
    }

    BaseController(Request request, Response response, AuthDao authDao, ConfigurationManager configurationManager) {
        initCommons(request, response, configurationManager);
        mAuthDao = authDao;
    }

    BaseController(Request request, Response response, AuthDao authDao, TransactionDao transactionDao, AlertDao alertDao, ConfigurationManager configurationManager) {
        initCommons(request, response, configurationManager);
        mAuthDao = authDao;
        mTransactionDao = transactionDao;
        mAlertDao = alertDao;
    }

    private void initCommons(Request request, Response response, ConfigurationManager configurationManager) {
        mRequest = request;
        mResponse = response;
        mConfigurationManager = configurationManager;
    }

    protected abstract ModelAndView index();
    protected abstract void redirect(String url);
}
