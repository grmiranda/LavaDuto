package Model.Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Sistema {

    public static void SalvarSistema(Object obj) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream("dados.ld");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(obj);
        oos.close();
        fos.close();
    }

    public static Object CarregarSistema() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("dados.ld");
        ObjectInputStream ois = new ObjectInputStream(fis);
        return ois.readObject();
    }
}
