package Controller;

import Model.Servidor;
import java.io.IOException;

public class Controller {

    private final Servidor server;

    public Controller(int port) throws IOException, ClassNotFoundException {
        this.server = new Servidor(port);
    }

    public void ligarServidor() throws IOException {
        server.ligarServidor();;
    }
}
