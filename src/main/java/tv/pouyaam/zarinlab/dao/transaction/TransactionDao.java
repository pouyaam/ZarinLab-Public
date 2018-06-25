package tv.pouyaam.zarinlab.dao.transaction;

import tv.pouyaam.zarinlab.model.db.TransactionModel;

import java.util.List;

public interface TransactionDao {
    void add(TransactionModel transactionModel);
    void add(List<TransactionModel> transactionModels);

    void delete();
    void deleteTransactionByPublicId(long publicId);

    TransactionModel updateTransaction(TransactionModel transactionModel);

    List<TransactionModel> findAll();
    TransactionModel findTransactionByPublicId(long publicId);
    List<TransactionModel> findTransactionAfterSpecificTime(long createdTimestamp);
    List<TransactionModel> findTransactionBeforeSpecificTime(long createdTimestamp);
    List<TransactionModel> findAllTransactionOrderByTimeDesc();
}
