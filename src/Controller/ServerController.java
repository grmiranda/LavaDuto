package Controller;

import Model.Servidor;
import java.io.IOException;

public class ServerController {

    private final Servidor server;

    public ServerController(int port) throws IOException, ClassNotFoundException {
        this.server = new Servidor(port);
    }

    public void ligarServidor() throws IOException {
        server.ligarServidor();;
    }
}
