package Model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

    private final ArrayList<Cliente> clientes;
    private int port;

    public Servidor(int port) {
        this.port = port;
        clientes = new ArrayList<>();
    }

    //Ligando o servidor
    public void ligarServidor() throws IOException {
        ServerSocket server = new ServerSocket(port);
        System.out.println("Servidor Iniciado com sucesso");

        while (true) {
            Socket cliente = server.accept();
            Cliente c = new Cliente(cliente, this);
            clientes.add(c);
            System.out.println("Cliente " + c.getIp());
            new Thread(c).start();
        }
    }

    public void desconectarCliente(Cliente c) {
        if (!clientes.isEmpty()) {
            clientes.remove(c);
            System.out.println("Cliente " + c.getIp() + " desconectou.");
        }
    }

    //Tratando todas as msgs em que o cliente manda para o servidor
    public void tratarMsg(String msg, Socket cliente) {

    }
}
