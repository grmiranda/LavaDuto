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
    
    /**
     * Metodo para retornar o IP do cliente
     * @return IP do cliente
     */
    public String getIp() {
        return cliente.getInetAddress().getHostAddress();
    }

    /**
     * Metodo que envia uma mensagem para o cliente
     * @param msg mensagem a ser enviada
     * @throws IOException
     */
    public void enviarMsg(String msg) throws IOException {
        PrintStream ps = new PrintStream(cliente.getOutputStream());
        ps.println(msg);
    }
    
    @Override
    public void run() {
        try {
            Scanner sc = new Scanner(cliente.getInputStream());
            while (sc.hasNextLine()) {
                server.tratarMsg(sc.nextLine(), this);
            }
        } catch (IOException ex) {
            server.desconectarCliente(this);
        }
    }
}
