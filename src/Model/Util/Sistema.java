package Model.Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Sistema {
    
    /**
     * Metodo que salva os dados de um objeto em um arquivo de texto
     * @param obj objeto a ser salvo no HD
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void SalvarSistema(Object obj) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream("dados.ld");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(obj);
        oos.close();
        fos.close();
    }

    /**
     * Metodos que carrega os dados de um objeto que foram salvos em um arquivo de texto
     * @return o objeto carregado pelo arquivo 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object CarregarSistema() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("dados.ld");
        ObjectInputStream ois = new ObjectInputStream(fis);
        return ois.readObject();
    }
}
