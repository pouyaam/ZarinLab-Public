package tv.pouyaam.zarinlab.dao.alert;

import tv.pouyaam.zarinlab.model.db.AlertModel;

import java.util.List;

public interface AlertDao {
    void add(AlertModel alertModel);
    void delete();
    void deleteAlertByAlertId(long alertId);
    void deleteAlertsByPublicId(long publicId);

    List<AlertModel> findAll();
    AlertModel findAlertByAlertId(long alertId);
    AlertModel findAlertByPublicId(long publicId);
}
