package View;

import Controller.Controller;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try{
            Scanner sc = new Scanner(System.in);
            System.out.println("Digite a porta do servidor:");
            int port = Integer.parseInt(sc.nextLine());
            Controller c = new Controller(port);
            c.ligarServidor();
        } catch (NumberFormatException ex){
            System.out.println("porta invalida");
        } catch (IllegalArgumentException ex){
            System.out.println("Porta invalidas");
        }
    }
}
