package Model;

public class Arquivo {

    private final String name;
    private final String ipOrigem;

    public Arquivo(String name, String ipOrigem) {
        this.name = name;
        this.ipOrigem = ipOrigem;
    }

    public String getName() {
        return name;
    }

    public String getIpOrigem() {
        return ipOrigem;
    }
}
