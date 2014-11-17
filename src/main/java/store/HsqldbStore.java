package store;

import mydomain.model.hsqldb.HsqldbEntry;

import java.util.Properties;

public class HsqldbStore extends Store {
    protected String user;

    public void storeHsqldb(HsqldbEntry entry) {
        put(entry);
    }

    public HsqldbStore(String connectionUrl, String user) {
        this.connectionUrl = connectionUrl;
        this.user = user;
    }

    @Override
    public Properties configure() {
        Properties properties = new Properties();
        properties.setProperty("datanucleus.PersistenceUnitName",
                                getClass().getSimpleName());
        properties.setProperty("datanucleus.ConnectionURL", connectionUrl);
        properties.setProperty("datanucleus.ConnectionUserName", user);
        return properties;
    }
}
