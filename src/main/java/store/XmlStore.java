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

    private static String getURL() throws IOException {
        File xml = new File("./store.xml");
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

        return "xml:file:" + xml.getAbsolutePath();
    }

    public XmlStore() throws IOException {
        super(getURL());
    }

    public void storeXml(XmlEntry entry) {
        put(entry);
    }

    @Override
    protected void configure(Properties properties) {
        // NOOP
    }
}
