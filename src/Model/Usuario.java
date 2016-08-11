package Model;

import java.io.Serializable;

public class Usuario implements Serializable{
    
    private final String name;
    private final String password;
    private boolean online;

    public Usuario(String name, String password, boolean online) {
        this.name = name;
        this.password = password;
        this.online = online;
    }
    
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
    
    @Override
    public boolean equals(Object obj){
        if (obj instanceof Usuario){
            Usuario u = (Usuario) obj;
            return (u.getName().equals(this.name) &&
                    u.getPassword().equals(this.password));
        }
        else
            return false;
    }
}
