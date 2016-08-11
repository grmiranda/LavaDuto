package Model;

import java.util.ArrayList;

public class Arquivo {
    private final String name;
    private ArrayList<String> ipOrigem = new ArrayList<>();

    public Arquivo(String name, String ipOrigem) {
        this.name = name;
        this.ipOrigem.add(ipOrigem);
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getIpOrigem() {
        return ipOrigem;
    }
    
    public void adicionarIpOrigem(String ipOrigem){
        this.ipOrigem.add(ipOrigem);
    }
    
    public void removerIpOrigem(String ipOrigem){
        this.ipOrigem.remove(ipOrigem);
    }
}
