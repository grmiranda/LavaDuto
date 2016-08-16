package View;

import Controller.ServerController;
import java.io.IOException;
import java.net.BindException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int port = 0;
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Informe a porta do servidor");
            port = Integer.parseInt(sc.nextLine());
            ServerController c = new ServerController(port);
            c.ligarServidor();
        } catch (NumberFormatException ex) {
            System.out.println("Porta invalida");
        } catch (IllegalArgumentException ex) {
            System.out.println("Porta invalida");
        } catch (BindException ex) {
            System.out.println("Porta " + port + " já está sendo utilizada.");
        }
    }
}
