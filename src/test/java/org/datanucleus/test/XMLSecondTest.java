package org.datanucleus.test;

import store.HsqldbStore;
import store.XmlStore;

import mydomain.model.hsqldb.HsqldbEntry;
import mydomain.model.xml.XmlEntry;
import org.datanucleus.util.NucleusLogger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.fail;

public class XMLSecondTest {
    private XmlStore xmlStore;
    private HsqldbStore hsqldbStore;

    @Before
    public void setUp() throws IOException {
        xmlStore = new XmlStore("/tmp/store.xml");
        xmlStore.initialize();
        hsqldbStore = new HsqldbStore("jdbc:hsqldb:mem:nucleus1", "test");
        hsqldbStore.initialize();
    }

    @After
    public void tearDown() {
        if (xmlStore != null) {
            xmlStore.close();
        }

        if (hsqldbStore != null) {
            hsqldbStore.close();
        }
    }

    @Test
    public void testXmlSecond() {
        NucleusLogger.GENERAL.info(">> test START");

        try {
            hsqldbStore.storeHsqldb(new HsqldbEntry("testXmlSecond"));
            xmlStore.storeXml(new XmlEntry("testXmlSecond"));
        } catch (Throwable t) {
            NucleusLogger.GENERAL.error(">> Exception thrown persisting data",
                            t);
            Throwable cause = t.getCause();
            fail("Running xml database in same JVM with "
                            + "another database using datastore "
                            + "identity failed: " + t + "; cause " + cause);
        }

        NucleusLogger.GENERAL.info(">> test END");
    }
}
