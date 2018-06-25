package tv.pouyaam.zarinlab.dao.transaction;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import tv.pouyaam.zarinlab.model.db.TransactionModel;

import java.util.List;

public class Sql2oTransactionDao implements TransactionDao {
    private Sql2o mSql2o;

    public Sql2oTransactionDao(Sql2o sql2o) {
        mSql2o = sql2o;
    }

    @Override
    public void add(TransactionModel transactionModel) {
        String sql = "INSERT INTO TRANSACTION(amount, public_id, created_timestamp, created, description, from_user) VALUES(:amount, :publicId, :createdTimestamp, :created, :description, :fromUser)";
        try {
            if (findTransactionByPublicId(transactionModel.getPublicId()) == null && !transactionModel.getFromUser().contains("تعهد کيف پول های زرين پال")) {
                Connection connection = mSql2o.open();
                Object id = connection.createQuery(sql)
                        .bind(transactionModel)
                        .executeUpdate().getKey();

                transactionModel.setId((Integer) id);
                connection.close();
            }

        } catch (Sql2oException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(List<TransactionModel> transactionModels) {
        for (TransactionModel transactionModel : transactionModels)
            add(transactionModel);
    }

    @Override
    public void delete() {
        try {
            Connection connection = mSql2o.open();
            connection.createQuery("DELETE TRANSACTION").executeUpdate();
            connection.close();
        } catch (Sql2oException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTransactionByPublicId(long publicId) {
        try {
            Connection connection = mSql2o.open();
            connection.createQuery("DELETE TRANSACTION WHERE public_id=:publidId")
                    .addColumnMapping("PUBLIC_ID", "publidId")
                    .addColumnMapping("CREATED_TIMESTAMP", "createdTimestamp")
                    .addColumnMapping("FROM_USER", "fromUser")
                    .addParameter("publicId", publicId)
                    .executeUpdate();
            connection.close();
        } catch (Sql2oException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TransactionModel updateTransaction(TransactionModel transactionModel) {
        return null;
    }

    @Override
    public List<TransactionModel> findAll() {
        try {
            Connection connection = mSql2o.open();
            return connection.createQuery("SELECT * FROM TRANSACTION ORDER BY created_timestamp DESC")
                    .addColumnMapping("PUBLIC_ID", "publicId")
                    .addColumnMapping("CREATED_TIMESTAMP", "createdTimestamp")
                    .addColumnMapping("FROM_USER", "fromUser")
                    .executeAndFetch(TransactionModel.class);
        } catch (Sql2oException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public TransactionModel findTransactionByPublicId(long publicId) {
        try {
            Connection connection = mSql2o.open();
            return connection.createQuery("SELECT * FROM TRANSACTION WHERE public_id=:publicId")
                    .addColumnMapping("PUBLIC_ID", "publicId")
                    .addColumnMapping("CREATED_TIMESTAMP", "createdTimestamp")
                    .addColumnMapping("FROM_USER", "fromUser")
                    .addParameter("publicId", publicId)
                    .executeAndFetchFirst(TransactionModel.class);
        } catch (Sql2oException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<TransactionModel> findTransactionAfterSpecificTime(long createdTimestamp) {
        try {
            Connection connection = mSql2o.open();
            return connection.createQuery("SELECT * FROM TRANSACTION WHERE created_timestamp>:createdTimestamp ORDER BY created_timestamp ASC")
                    .addColumnMapping("PUBLIC_ID", "publicId")
                    .addColumnMapping("CREATED_TIMESTAMP", "createdTimestamp")
                    .addColumnMapping("FROM_USER", "fromUser")
                    .addParameter("createdTimestamp", createdTimestamp)
                    .executeAndFetch(TransactionModel.class);
        } catch (Sql2oException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<TransactionModel> findTransactionBeforeSpecificTime(long createdTimestamp) {
        try {
            Connection connection = mSql2o.open();
            return connection.createQuery("SELECT * FROM TRANSACTION WHERE created_timestamp<=:createdTimestamp ORDER BY created_timestamp DESC")
                    .addColumnMapping("PUBLIC_ID", "publicId")
                    .addColumnMapping("CREATED_TIMESTAMP", "createdTimestamp")
                    .addColumnMapping("FROM_USER", "fromUser")
                    .addParameter("createdTimestamp", createdTimestamp)
                    .executeAndFetch(TransactionModel.class);
        } catch (Sql2oException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<TransactionModel> findAllTransactionOrderByTimeDesc() {
        try {
            Connection connection = mSql2o.open();
            return connection.createQuery("SELECT * FROM TRANSACTION ORDER BY created_timestamp DESC")
                    .addColumnMapping("PUBLIC_ID", "publicId")
                    .addColumnMapping("CREATED_TIMESTAMP", "createdTimestamp")
                    .addColumnMapping("FROM_USER", "fromUser")
                    .executeAndFetch(TransactionModel.class);
        } catch (Sql2oException e) {
            e.printStackTrace();
        }

        return null;
    }
}
