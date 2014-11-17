package store;

import org.datanucleus.api.jdo.JDOPersistenceManagerFactory;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import java.util.Properties;

public abstract class Store {
    protected PersistenceManagerFactory pmf;

    protected String connectionUrl;

    public void close() {
        if (pmf != null) {
            pmf.close();
        }
    }

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

    public void initialize() {
        pmf = new JDOPersistenceManagerFactory(configure());
    }

    protected abstract Properties configure();
}
