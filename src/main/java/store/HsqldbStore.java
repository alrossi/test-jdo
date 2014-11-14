package store;

import mydomain.model.hsqldb.HsqldbEntry;

import java.io.IOException;
import java.util.Properties;

public class HsqldbStore extends Store {
    public HsqldbStore() throws IOException {
        super("jdbc:hsqldb:mem:nucleus1");
    }

    public void storeHsqldb(HsqldbEntry entry) {
        put(entry);
    }

    protected void configure(Properties properties) {
        properties.setProperty("javax.jdo.option.ConnectionDriverName",
                               "org.hsqldb.jdbcDriver");
        properties.setProperty("javax.jdo.option.ConnectionUserName", "sa");
        properties.setProperty("javax.jdo.option.DetachAllOnCommit", "true");
        properties.setProperty("javax.jdo.option.Optimistic", "true");
        properties.setProperty("javax.jdo.option.NontransactionalRead", "true");
        properties.setProperty("javax.jdo.option.RetainValues", "true");
        properties.setProperty("javax.jdo.option.Multithreaded", "true");
        properties.setProperty("datanucleus.autoCreateSchema", "true");
        properties.setProperty("datanucleus.validateTables", "false");
        properties.setProperty("datanucleus.validateConstraints", "false");
        properties.setProperty("datanucleus.autoCreateColumns", "true");
        properties.setProperty("datanucleus.connectionPoolingType", "None");
    }
}
