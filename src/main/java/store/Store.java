package store;

import org.datanucleus.api.jdo.JDOPersistenceManagerFactory;

import java.util.Properties;

public abstract class Store {
    protected JDOPersistenceManagerFactory pmf;

    public void close() {
        if (pmf != null) {
            pmf.close();
        }
    }

    protected Store(String connectionURL) {
        Properties properties = new Properties();
        properties.setProperty("javax.jdo.option.ConnectionURL",
                        connectionURL);
        configure(properties);
        pmf = new JDOPersistenceManagerFactory(properties);
    }

    protected abstract void configure(Properties properties);
}
