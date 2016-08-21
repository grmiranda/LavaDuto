package Model;

import Model.Util.Sistema;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

    private ArrayList<Usuario> usuarios;
    private final ArrayList<Arquivo> arquivos;
    private final int port;

    public Servidor(int port) throws IOException, ClassNotFoundException {
        this.port = port;
        this.arquivos = new ArrayList<>();

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
        //aceitando novas conexões
        while (true) {
            Socket cliente = server.accept();
            Cliente c = new Cliente(cliente, this);
            //iniciando um novo cliente
            System.out.println("Cliente " + c.getIp() + " conectou");
            new Thread(c).start();
        }
    }

    //desconectando o cliente
    synchronized public void desconectarCliente(Cliente c) {
        for (Usuario u : usuarios) {
            if (u.getName().equals(c.getUserName())) {
                System.out.println(c.getUserName() + " desconectou.");
                u.setOnline(false);
                return;
            }
        }
    }

    //cadastrando um novo usuario
    synchronized private void cadastrar(String nome, String senha, Cliente c) throws IOException {
        //verificando se já existe um usuario com o mesmo nome
        for (Usuario u : usuarios) {
            if (u.getName().equals(nome)) {
                //avisando ao cliente que ja existe um usuario com o mesmo nome
                c.enviarMsg("#03");
                System.out.println("Cliente " + c.getIp() + " falhou ao cadastrar um novo usuario");
                return;
            }
        }
        //criando novo usuario
        Usuario u = new Usuario(nome, senha);
        usuarios.add(u);
        //avisando ao cliente que o cadastro foi efetuado com sucesso
        c.enviarMsg("#02");
        Sistema.SalvarSistema(usuarios);
        System.out.println("Cliente " + c.getIp() + " cadastrou o usuario " + nome);
    }

    synchronized private void logarUsuario(String nome, Cliente c) throws IOException {
        //buscando os usuarios
        for (Usuario u : usuarios) {
            //procurando o usuario com o nome do cliente
            if (u.getName().equals(nome)) {
                if (u.isOnline()) {
                    //avisando ao cliente que o usuario ja esta online
                    c.enviarMsg("#06:1");
                    System.out.println("Cliente " + c.getIp() + " falhou ao logar");

                } else {
                    //logando o cliente
                    u.setOnline(true);
                    c.setUserName(nome);
                    System.out.println("Cliente " + c.getIp() + " logou como " + nome);
                }
                return;
            }
        }
    }

    synchronized private void login(String nome, String senha, Cliente c) throws IOException {
        Usuario aux = new Usuario(nome, senha);
        for (Usuario u : usuarios) {
            //buscando o usuario
            if (u.equals(aux)) {
                //verificando se o usuario ja esta online
                if (u.isOnline()) {
                    //avisando ao cliente que o usuario esta online
                    c.enviarMsg("#06:1");
                    System.out.println("Cliente " + c.getIp() + " falhou ao logar");
                } else {
                    //logando o cliente
                    u.setOnline(true);
                    c.enviarMsg("#05");
                    c.setUserName(nome);
                    System.out.println("Cliente " + c.getIp() + " logou como " + nome);
                }
                return;
            }
        }
        //dados invalidos
        c.enviarMsg("#06:0");
        System.out.println("Cliente " + c.getIp() + " falhou ao logar");
    }

    //atualizando a lista de arq
    synchronized private void atualizarArq(Cliente c) throws IOException {
        String[] arq = new String[arquivos.size()];
        String[] ips = new String[arquivos.size()];

        int i = 0;
        //obtendo o nome e o endereço de origem do arquivo
        for (Arquivo a : arquivos) {
            if (!a.getIpOrigem().equals(c.getIp())) {
                arq[i] = a.getName();
                ips[i] = a.getIpOrigem();
                i++;
            }
        }

        String msg = "#14:" + i;
        //formando a string
        for (int j = 0; j < i; j++) {
            msg = msg + ":" + arq[j] + ":" + ips[j];
        }
        //enviando ao cliente
        c.enviarMsg(msg);
    }

    //listas de todos os arquivos
    synchronized private void listaArq(String[] arq, Cliente c) {
        for (String aux : arq) {
            Arquivo a = new Arquivo(aux, c.getIp());
            arquivos.add(a);
        }
    }

    //removendo os arquivos do cliente
    synchronized public void remArq(Cliente c) {
        ArrayList<Arquivo> aux = new ArrayList<>();
        if (!arquivos.isEmpty()) {
            for (Arquivo a : arquivos) {
                if (a.getIpOrigem().equals(c.getIp())) {
                    aux.add(a);
                }
            }
        }
        arquivos.removeAll(aux);
    }

    //Tratando todas as msgs em que o cliente manda para o servidor
    public void tratarMsg(String msg, Cliente cliente) throws IOException {
        String[] info = msg.split(":");
        String codigo = info[0];
        /*
         #01 - cadastro (nome, senha)
         #04 - login normal(nome, senha)
         #07 - logar usuario(nome)
         #08 - atualizar arq
         #09 - deslogar(nome)
         #15 - lista de arquivos
         */
        switch (codigo) {
            case ("#01"):
                cadastrar(info[1], info[2], cliente);
                break;
            case ("#04"):
                login(info[1], info[2], cliente);
                break;
            case ("#07"):
                logarUsuario(info[1], cliente);
                break;
            case ("#08"):
                atualizarArq(cliente);
                break;
            case ("#09"):
                desconectarCliente(cliente);
                break;
            case ("#15"):
                int tam = Integer.parseInt(info[1]);
                String aux[] = new String[tam];
                for (int i = 0; i < tam; i++) {
                    aux[i] = info[2 + i];
                }
                remArq(cliente);
                listaArq(aux, cliente);
                System.out.println(cliente.getIp() + " atualizou a lista de arquivos");
                break;
        }
    }
}
