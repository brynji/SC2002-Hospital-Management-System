package Data;

import java.io.*;
import java.util.ArrayList;

public class Database {

    public static <T> T readFromFile(String filename) throws IOException,ClassNotFoundException {
        FileInputStream file = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(file);
        return (T) in.readObject();
    }

    public static <T> void writeToFile(String filename, ArrayList<T> data) throws IOException {
        FileOutputStream file = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(data);
    }
}
