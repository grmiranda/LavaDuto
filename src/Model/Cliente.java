package Model;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente implements Runnable {
    
    private final Socket cliente;
    private final Servidor server;
    
    public Cliente(Socket cliente, Servidor server) {
        this.cliente = cliente;
        this.server = server;
    }
    
    public String getIp() {
        return cliente.getInetAddress().getHostAddress();
    }

    //enviar msg para o cliente
    public void enviarMsg(String msg) throws IOException {
        PrintStream ps = new PrintStream(cliente.getOutputStream());
        ps.println(msg);
    }
    
    @Override
    public void run() {
        try {
            Scanner sc = new Scanner(cliente.getInputStream());
            while (sc.hasNextLine()) {
                server.tratarMsg(sc.nextLine(), cliente);
            }
        } catch (IOException ex) {
            server.desconectarCliente(this);
        }
    }
}
