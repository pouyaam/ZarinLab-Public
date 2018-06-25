package tv.pouyaam.zarinlab.dao.alert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import tv.pouyaam.zarinlab.dao.token.Sql2oAuthDao;
import tv.pouyaam.zarinlab.model.db.AlertModel;

import java.util.List;

import static org.junit.Assert.*;

public class Sql2oAlertDaoTest {
    private Sql2oAlertDao mSql2oAlertDao;
    private Connection mConnection;
    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        mSql2oAlertDao = new Sql2oAlertDao(sql2o);

        mConnection = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        mConnection.close();
    }

    @Test
    public void createANewAlertModelAndSaveIt() throws Exception {
        AlertModel alertModel = newAlertModel(100, 200, 3000);
        mSql2oAlertDao.add(alertModel);

        AlertModel temp1 = mSql2oAlertDao.findAlertByAlertId(100);
        AlertModel temp2 = mSql2oAlertDao.findAlertByPublicId(200);

        assertNotEquals(null, temp1);
        assertNotEquals(null, temp2);

    }

    @Test
    public void createMultipleAlertModelAndSaveIt() throws Exception {
        mSql2oAlertDao.add(newAlertModel(200, 400, 5000));
        mSql2oAlertDao.add(newAlertModel(201, 401, 7000));
        mSql2oAlertDao.add(newAlertModel(202, 402, 10000));

        List<AlertModel> alertModels = mSql2oAlertDao.findAll();

        assertEquals(true, (alertModels.size() > 1));
        assertEquals(true, (alertModels.get(0).getCreatedTimestamp() > alertModels.get(1).getCreatedTimestamp()));
    }

    private AlertModel newAlertModel(long alertId, long publicId, long createdTimestamp) {
        return new AlertModel(alertId, publicId, createdTimestamp);
    }

}