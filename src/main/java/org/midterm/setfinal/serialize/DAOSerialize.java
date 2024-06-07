package org.midterm.setfinal.serialize;

import java.io.*;

public class DAOSerialize {

    public static void writeSerialized(SerializeObject serializableObject) {
        try {
            FileOutputStream fileOut = new FileOutputStream("src/main/resources/serialize/serialize.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(serializableObject);
            out.close();
            fileOut.close();
            System.out.println("writeSerialized: File Saved");
        } catch (IOException e) {
            System.out.println("writeSerialized: FILE NOT SAVED");
            e.printStackTrace();
        }
    }

    public static SerializeObject readSerialized() {
        SerializeObject serializableObject = null;
        try {
            FileInputStream fileIn = new FileInputStream("src/main/resources/serialize/serialize.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            serializableObject = (SerializeObject) in.readObject();
            System.out.println("readSerialized: File Read");
            in.close();
            fileIn.close();
        } catch (IOException e) {
            System.out.println("readSerialized: FILE NOT FOUND, creating new file.");
            serializableObject = new SerializeObject();
            writeSerialized(serializableObject);
        } catch (ClassNotFoundException e) {
            System.out.println("readSerialized: NO CLASS FOUND EXCEPTION");
            e.printStackTrace();
        }
        return serializableObject;
    }
}
