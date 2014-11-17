package store;

import mydomain.model.xml.XmlEntry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class XmlStore extends Store {
    private static final String EMPTY_XML_STORE
        = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<entries></entries>\n";

    public XmlStore(String path) throws IOException {
        File xml = new File(path);
        if (!xml.exists()) {
            if (!xml.getParentFile().isDirectory()) {
                String parent = xml.getParentFile().getAbsolutePath();
                throw new FileNotFoundException(parent + " is not a directory");
            }

            try (FileWriter fw = new FileWriter(xml, false)) {
                fw.write(EMPTY_XML_STORE);
                fw.flush();
            }
        }
        connectionUrl = "xml:file:" + xml.getAbsolutePath();
    }

    public void storeXml(XmlEntry entry) {
        put(entry);
    }

    @Override
    public Properties configure() {
        Properties properties = new Properties();
        properties.setProperty("datanucleus.PersistenceUnitName",
                                getClass().getSimpleName());
        properties.setProperty("datanucleus.ConnectionURL", connectionUrl);
        return properties;
    }
}
