package store;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import org.datanucleus.api.jdo.JDOPersistenceManagerFactory;

import java.util.Properties;

public abstract class Store {
    protected JDOPersistenceManagerFactory pmf;

    protected Store(String connectionURL) {
        Properties properties = new Properties();
        properties.setProperty("javax.jdo.option.ConnectionURL",
                        connectionURL);
        configure(properties);
        pmf = new JDOPersistenceManagerFactory(properties);
    }

    public void close() {
        if (pmf != null) {
            pmf.close();
        }
    }

    protected abstract void configure(Properties properties);

    protected <T> void put(T data) {
        PersistenceManager insertManager = pmf.getPersistenceManager();
        Transaction tx = insertManager.currentTransaction();
        try {
            tx.begin();
            insertManager.makePersistent(data);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            insertManager.close();
        }
    }
}
