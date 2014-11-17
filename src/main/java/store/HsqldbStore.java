package store;

import mydomain.model.hsqldb.HsqldbEntry;

import java.io.IOException;

public class HsqldbStore extends Store {
    public HsqldbStore() throws IOException {
        super("HsqldbStore");
    }

    public void storeHsqldb(HsqldbEntry entry) {
        put(entry);
    }
}
