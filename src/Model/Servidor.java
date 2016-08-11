package Model;

import Model.Util.Sistema;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {
    
    private final ArrayList<Cliente> clientes;
    private ArrayList<Usuario> usuarios;
    private final int port;
    
    public Servidor(int port) throws IOException, ClassNotFoundException {
        this.port = port;
        clientes = new ArrayList<>();

        //Carregando os usuarios do sistema
        try {
            usuarios = (ArrayList<Usuario>) Sistema.CarregarSistema();
        } catch (FileNotFoundException ex) {
            //caso o arquivo não exista cria um novo.
            usuarios = new ArrayList<>();
            Sistema.SalvarSistema(usuarios);
        }
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

    //desconectando o cliente
    public void desconectarCliente(Cliente c) {
        if (!clientes.isEmpty()) {
            clientes.remove(c);
            System.out.println("Cliente " + c.getIp() + " desconectou.");
        }
    }
    
    private void cadastrar(String nome, String senha, Cliente c) throws IOException {
        for (Usuario u : usuarios) {
            if (u.getName().equals(nome)) {
                //enviar a msg de erro de acordo com o protocolo
                return;
            }
        }
        Usuario u = new Usuario(nome, senha);
        usuarios.add(u);
        //enviar msg para o cliente informando que o usuario foi cadastrado
        Sistema.SalvarSistema(usuarios);
    }

    //Tratando todas as msgs em que o cliente manda para o servidor
    public void tratarMsg(String msg, Cliente cliente) {
        
    }
}
