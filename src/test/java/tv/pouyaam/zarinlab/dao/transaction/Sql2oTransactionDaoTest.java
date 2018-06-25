package tv.pouyaam.zarinlab.dao.transaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import tv.pouyaam.zarinlab.dao.token.Sql2oAuthDao;
import tv.pouyaam.zarinlab.model.db.TransactionModel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by pouya on 6/22/18.
 */
public class Sql2oTransactionDaoTest {
    private Sql2oTransactionDao mSql2oTransactionDao;
    private Connection mConnection;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        mSql2oTransactionDao = new Sql2oTransactionDao(sql2o);

        mConnection = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        mConnection.close();
    }

    @Test
    public void addingATransactionToDatabase() throws Exception {
        TransactionModel transactionModel = newTransaction(1000, 2000, "2018-04-05 15:28:13", "TEST DESC");
        int originalId = transactionModel.getId();
        mSql2oTransactionDao.add(transactionModel);
        assertNotEquals(originalId, transactionModel.getId());
    }

    @Test
    public void addingMultipleTransaction() {
        List<TransactionModel> transactionModelList = new ArrayList<>();
        transactionModelList.add(newTransaction(120, 2001, "2018-04-05 15:28:13", "TEST DESC"));
        transactionModelList.add(newTransaction(150, 2002, "2018-04-06 15:28:13", "TEST DESC"));
        transactionModelList.add(newTransaction(190, 2003, "2018-04-07 15:28:13", "TEST DESC"));
        transactionModelList.add(newTransaction(120, 2004, "2018-04-08 15:28:13", "TEST DESC"));

        mSql2oTransactionDao.add(transactionModelList);
        List<TransactionModel> temp = mSql2oTransactionDao.findAll();
        assertEquals(true, (temp.size() > 1));
    }

    @Test
    public void addingTransactionFindNewOne() {
        List<TransactionModel> transactionModelList = new ArrayList<>();
        transactionModelList.add(newTransaction(100, 2005, "2018-04-05 15:28:13", "TEST DESC"));
        transactionModelList.add(newTransaction(200, 2006, "2018-04-06 15:28:13", "TEST DESC"));
        transactionModelList.add(newTransaction(300, 2007, "2018-04-07 15:28:13", "TEST DESC"));
        transactionModelList.add(newTransaction(400, 2008, "2018-04-08 15:28:13", "TEST DESC"));

        mSql2oTransactionDao.add(transactionModelList);
        List<TransactionModel> desc = mSql2oTransactionDao.findAllTransactionOrderByTimeDesc();

        assertEquals(true, (desc.get(0).getCreatedTimestamp() > desc.get(1).getCreatedTimestamp()));

        long newestTransaction = desc.get(0).getCreatedTimestamp();

        mSql2oTransactionDao.add(newTransaction(500, 2009, "2018-06-08 15:28:13", "TEST DESC"));
        mSql2oTransactionDao.add(newTransaction(600, 2010, "2018-06-08 15:48:13", "TEST DESC"));

        List<TransactionModel> newest = mSql2oTransactionDao.findTransactionAfterSpecificTime(newestTransaction);

        assertEquals(2, newest.size());
        assertEquals(true, (newest.get(0).getCreatedTimestamp() < newest.get(1).getCreatedTimestamp()));
    }

    private TransactionModel newTransaction(int amount, long publicId, String created, String description) {
        return new TransactionModel(amount, publicId, created, description, "Guest");
    }

    private TransactionModel newTransaction(int amount, long publicId, long createdTimeStamp, String created, String description) {
        return new TransactionModel(amount, publicId, createdTimeStamp, created, description);
    }
}