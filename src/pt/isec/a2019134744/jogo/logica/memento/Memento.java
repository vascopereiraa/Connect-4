package pt.isec.a2019134744.jogo.logica.memento;

import java.io.*;

public class Memento {
    private byte[] snapshot = null;

    public Memento(Object obj) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            oos.writeObject(obj);
            snapshot = baos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getSnapshot() {

        if(snapshot == null)
            return null;

        try (ByteArrayInputStream bais = new  ByteArrayInputStream(snapshot);
             ObjectInputStream ois = new ObjectInputStream(bais)) {

            return ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
