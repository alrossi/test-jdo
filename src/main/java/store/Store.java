package store;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

public abstract class Store {
    protected PersistenceManagerFactory pmf;

    protected Store(String persistenceUnit) {
        pmf = JDOHelper.getPersistenceManagerFactory(persistenceUnit);
    }

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
}
