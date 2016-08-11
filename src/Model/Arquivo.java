package Model;

public class Arquivo {
    private final String name;
    private final String ipOrigin;

    public Arquivo(String name, String ipOrigin) {
        this.name = name;
        this.ipOrigin = ipOrigin;
    }

    public String getName() {
        return name;
    }

    public String getIpOrigin() {
        return ipOrigin;
    }
}
