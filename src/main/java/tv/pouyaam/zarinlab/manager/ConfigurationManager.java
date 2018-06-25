package tv.pouyaam.zarinlab.manager;


import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

public class ConfigurationManager {
    private static ConfigurationManager sInstance;
    private Configuration mConfiguration;


    private ConfigurationManager() {
        Configurations configurations = new Configurations();

        try {
            mConfiguration = configurations.properties(new File("./zarinlab.properties"));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static ConfigurationManager with() {
        if (sInstance == null)
            sInstance = new ConfigurationManager();

        return sInstance;
    }


    public int readRefreshTime() {
        if (mConfiguration != null) {
            return mConfiguration.getInt("zarinlab.refresh.time");
        } else {
            return 20;
        }
    }

    public int readPort() {
        if (mConfiguration != null) {
            return mConfiguration.getInt("zarinlab.port");
        } else {
            return 4567;
        }
    }

    public boolean isNotify() {
        if (mConfiguration != null) {
            return mConfiguration.getBoolean("zarinlab.notify");
        } else {
            return true;
        }
    }

    public String readZarinpalDbName() {
        if (mConfiguration != null) {
            return mConfiguration.getString("zarinlab.db.name");
        } else {
            return "zarinlab.db";
        }
    }
}
