package tv.pouyaam.zarinlab.dao.alert;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import tv.pouyaam.zarinlab.model.db.AlertModel;

import java.util.List;

public class Sql2oAlertDao implements AlertDao {
    private final Sql2o mSql2o;

    public Sql2oAlertDao(Sql2o sql2o) {
        mSql2o = sql2o;
    }

    @Override
    public void add(AlertModel alertModel) {
        String sql = "INSERT INTO ALERT(alert_id, public_id, created_timestamp) VALUES(:alertId, :publicId, :createdTimestamp)";

        Connection connection = mSql2o.open();
        connection.createQuery(sql)
                .bind(alertModel)
                .executeUpdate();
        connection.close();

    }

    @Override
    public void delete() {
        try {
            Connection connection = mSql2o.open();
            connection.createQuery("DELETE ALERT").executeUpdate();
            connection.close();
        } catch (Sql2oException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAlertByAlertId(long alertId) {
        try {
            Connection connection = mSql2o.open();
            connection.createQuery("DELETE ALERT WHERE alert_id = :alertId")
                    .addColumnMapping("alert_id", "alertId")
                    .addColumnMapping("public_id", "publicId")
                    .addColumnMapping("created_timestamp", "createdTimestamp")
                    .addParameter("alertId", alertId)
                    .executeUpdate();
            connection.close();
        } catch (Sql2oException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAlertsByPublicId(long publicId) {
        try {
            Connection connection = mSql2o.open();
            connection.createQuery("DELETE ALERT WHERE alert_id = :alertId")
                    .addColumnMapping("alert_id", "alertId")
                    .addColumnMapping("public_id", "publicId")
                    .addColumnMapping("created_timestamp", "createdTimestamp")
                    .addParameter("publicId", publicId)
                    .executeUpdate();
            connection.close();
        } catch (Sql2oException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<AlertModel> findAll() {
        List<AlertModel> alertModels = null;
        try {
            Connection connection = mSql2o.open();
            alertModels = connection.createQuery("SELECT * FROM ALERT ORDER BY created_timestamp DESC")
                    .addColumnMapping("alert_id", "alertId")
                    .addColumnMapping("public_id", "publicId")
                    .addColumnMapping("created_timestamp", "createdTimestamp")
                    .executeAndFetch(AlertModel.class);
            connection.close();

            return alertModels;
        } catch (Sql2oException e) {
            e.printStackTrace();
        }
        return alertModels;
    }

    @Override
    public AlertModel findAlertByAlertId(long alertId) {
        AlertModel alertModels = null;
        try {
            Connection connection = mSql2o.open();
            alertModels = connection.createQuery("SELECT * FROM ALERT WHERE alert_id = :alertId")
                    .addColumnMapping("alert_id", "alertId")
                    .addColumnMapping("public_id", "publicId")
                    .addColumnMapping("created_timestamp", "createdTimestamp")
                    .addParameter("alertId", alertId)
                    .executeAndFetchFirst(AlertModel.class);
            connection.close();

            return alertModels;
        } catch (Sql2oException e) {
            e.printStackTrace();
        }
        return alertModels;
    }

    @Override
    public AlertModel findAlertByPublicId(long publicId) {
        AlertModel alertModels = null;
        try {
            Connection connection = mSql2o.open();
            alertModels = connection.createQuery("SELECT * FROM ALERT WHERE public_id = :publicId")
                    .addColumnMapping("alert_id", "alertId")
                    .addColumnMapping("public_id", "publicId")
                    .addColumnMapping("created_timestamp", "createdTimestamp")
                    .addParameter("publicId", publicId)
                    .executeAndFetchFirst(AlertModel.class);
            connection.close();

            return alertModels;
        } catch (Sql2oException e) {
            e.printStackTrace();
        }
        return alertModels;
    }
}
