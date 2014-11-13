package mydomain.model.xml;

import java.util.UUID;

public class XmlEntry {
    private String key;
    private String info;

    public XmlEntry(String info) {
        this.key = UUID.randomUUID().toString();
        this.info = info;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
