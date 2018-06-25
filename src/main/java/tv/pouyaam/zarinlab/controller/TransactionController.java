package tv.pouyaam.zarinlab.controller;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import tv.pouyaam.zarinlab.dao.alert.AlertDao;
import tv.pouyaam.zarinlab.dao.token.AuthDao;
import tv.pouyaam.zarinlab.dao.transaction.TransactionDao;
import tv.pouyaam.zarinlab.excepiton.ApiError;
import tv.pouyaam.zarinlab.helper.StringHelper;
import tv.pouyaam.zarinlab.manager.ConfigurationManager;
import tv.pouyaam.zarinlab.model.constants.Constant;
import tv.pouyaam.zarinlab.model.db.AlertModel;
import tv.pouyaam.zarinlab.model.db.TokenModel;
import tv.pouyaam.zarinlab.model.db.TransactionModel;
import tv.pouyaam.zarinlab.network.model.local.UpdateTransactionRequest;
import tv.pouyaam.zarinlab.network.model.local.UpdateTransactionResponse;
import tv.pouyaam.zarinlab.network.model.streamlabs.StreamLabDonateRequest;
import tv.pouyaam.zarinlab.network.model.streamlabs.StreamLabDonateResponse;
import tv.pouyaam.zarinlab.network.model.transaction.TransactionRequest;
import tv.pouyaam.zarinlab.network.model.transaction.TransactionResponse;
import tv.pouyaam.zarinlab.network.services.local.UpdateTransactionService;
import tv.pouyaam.zarinlab.network.services.transaction.TransactionService;
import tv.pouyaam.zarinlab.network.streamlab.donate.StreamLabDonateService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * TransactionController
 */
public class TransactionController extends BaseController {

    private UpdateTransactionService mUpdateTransactionService;

    public TransactionController(Request request, Response response,
                                 UpdateTransactionService updateTransactionService,
                                 ConfigurationManager configurationManager) {

        super(request, response, configurationManager);
        mUpdateTransactionService = updateTransactionService;
    }

    public TransactionController(Request request, Response response,
                                 AuthDao authDao, TransactionDao transactionDao,
                                 AlertDao alertDao, ConfigurationManager configurationManager) {
        super(request, response, authDao, transactionDao, alertDao, configurationManager);
    }

    /**
     * Create a table of transactions
     * Also updates all transactions
     * @return - Table.hbs view
     */
    @Override
    public ModelAndView index() {
        int refreshTime = mConfigurationManager.readRefreshTime();

        mUpdateTransactionService.initRetrofitCall(new UpdateTransactionRequest(), null, null, null);
        retrofit2.Response<UpdateTransactionResponse> response = mUpdateTransactionService.execute();

        if (response.code() == 200) {
            Map<String, Object> model = new HashMap<>();
            model.put("transactions", response.body().getTransactionModels());
            model.put("refreshTime", refreshTime);
            return new ModelAndView(model, "table.hbs");
        } else {
            throw new ApiError(response.code(), response.message());
        }
    }

    /**
     * Redirect to any given url
     * @param url - url
     */
    @Override
    protected void redirect(String url) {
        mResponse.redirect(url);
    }

    /**
     * Update transaction list from zarinpal and notify streamlab
     * @param transactionService - Trnasaction Service
     * @param streamLabDonateService - Streamlab donate notification
     * @return - A list of all transactions
     */
    public HashMap<String, List<TransactionModel>> update(TransactionService transactionService, StreamLabDonateService streamLabDonateService) {
        retrofit2.Response<TransactionResponse> response = getTransactionsFromZarinPal(transactionService);
        mResponse.type("application/json");
        switch (response.code()) {
            case 200:
                HashMap<String, List<TransactionModel>> transactionModelHashMap = new HashMap<>();
                transactionModelHashMap.put(
                        "transactions",
                        updateAndNotify(mAuthDao, mTransactionDao, mAlertDao, streamLabDonateService, response)
                );
                return transactionModelHashMap;

            case 401:
                redirect("/login");
                break;

            case 400:
                throw new ApiError(response.code(), response.message());
        }
        return null;
    }

    /**
     * Notify streamlab and save the alert id
     * @param authDao - Authentication Dao Impl
     * @param transactionDao - Transaction Dao Impl
     * @param alertDao - Alert Dao Impl
     * @param streamLabDonateService - Streamlab donate notification
     * @param response - Spark response
     * @return - List of transactions
     */
    private List<TransactionModel> updateAndNotify(AuthDao authDao,
                                                          TransactionDao transactionDao,
                                                          AlertDao alertDao,
                                                          StreamLabDonateService streamLabDonateService,
                                                          retrofit2.Response<TransactionResponse> response) {
        response.body().getData().stream().filter(transactionData -> transactionData.getConfirmed().equals("confirmed"))
                .forEach(transactionData -> {
                    TransactionModel transactionModel = new TransactionModel(
                            transactionData.getAmount(),
                            transactionData.getPublicId(),
                            transactionData.getCreated(),
                            transactionData.getDescription(),
                            transactionData.getFromUser().getName()

                    );
                    transactionDao.add(transactionModel);
                });


        if (authDao.findTokenByType(TokenModel.TOKEN_TYPE.STREAMLABS) != null && mConfigurationManager.isNotify()) {
            List<AlertModel> alerts = alertDao.findAll();
            if (alerts != null && alerts.size() > 0) {
                List<TransactionModel> transactions = transactionDao.findTransactionAfterSpecificTime(alerts.get(0).getCreatedTimestamp());
                if (transactions != null && transactions.size() > 0) {
                    sendDonationAlertToStreamlab(transactions, streamLabDonateService, alertDao);
                }
            } else {
                AlertModel alertModel = new AlertModel(0, 0, System.currentTimeMillis());
                alertDao.add(alertModel);
            }
        }

        return transactionDao.findAll();
    }

    /**
     * Get list of transactions from Zarimpal
     * @param transactionService - Transaction service
     * @return - Transaction Response
     */
    private retrofit2.Response<TransactionResponse> getTransactionsFromZarinPal(TransactionService transactionService) {

        TransactionRequest transactionRequest = new TransactionRequest.Builder().build();
        transactionService.initRetrofitCall(transactionRequest, null, null, null);
        retrofit2.Response<TransactionResponse> response = transactionService.execute();
        return response;
    }

    /**
     * Send an donation alert to streamlab and save it
     *
     * @param transactions - List of transactions
     * @param streamLabDonateService -  Streamlab donation alert service
     * @param alertDao - Alert Dao impl
     */
    private static void sendDonationAlertToStreamlab(List<TransactionModel> transactions,
                                                     StreamLabDonateService streamLabDonateService,
                                                     AlertDao alertDao) {
        transactions.stream().filter(transactionModel ->
                alertDao.findAlertByPublicId(transactionModel.getPublicId()) == null).forEach(transactionModel -> {
            retrofit2.Response<StreamLabDonateResponse> response = notifyStreamlabs(transactionModel, streamLabDonateService);
            if (response.code() == 200) {
                AlertModel alertModel
                        = new AlertModel(Integer.valueOf(response.body().getDonationId()),
                        transactionModel.getPublicId(),
                        System.currentTimeMillis());
                alertDao.add(alertModel);
            }
        });

    }

    /**
     * Create alert for notification
     * @param transactionModel - transaction
     * @param streamLabDonateService - service
     * @return - response
     */
    private static retrofit2.Response<StreamLabDonateResponse> notifyStreamlabs(TransactionModel transactionModel,
                                                                                StreamLabDonateService streamLabDonateService) {

        String name = "Guest";
        String description = transactionModel.getDescription();
        String id = String.format("%04d", new Random().nextInt(10000));

        HashMap<String, String> descriptionsPart = StringHelper.extractDataFromZarinpalDesc(transactionModel.getDescription());

        if (descriptionsPart.containsKey(Constant.DESC_KEY))
            description = descriptionsPart.get(Constant.DESC_KEY);

        if (transactionModel.getFromUser().length() > 0)
            name = transactionModel.getFromUser().split(" ")[0];


        if (!StringHelper.isValidISOLatin1(name) &&
                StringHelper.isValidISOLatin1(descriptionsPart.get(Constant.PAYER_KEY))) {
            name = descriptionsPart.get(Constant.PAYER_KEY).split(" ")[0];
            id = name;
        } else {
            name = "Guest";
            id = String.format("%s%s", name, id);
        }


        HashMap<String, String> query = new HashMap<>();
        query.put("name", name);
        query.put("identifier", id);
        query.put("amount", String.valueOf(transactionModel.getAmount()));
        query.put("message", description);
        query.put("currency", "TRY");
        streamLabDonateService.initRetrofitCall(new StreamLabDonateRequest(), null, query, null);
        return streamLabDonateService.execute();
    }
}
