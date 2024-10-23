package Data;

import Users.User;

import javax.xml.transform.Source;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database {

    public static <T> Map<String,T> readFromFile(String filename) throws ClassNotFoundException {
        try{
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);
            return (Map<String,T>) in.readObject();
        } catch (IOException e){
            System.out.println("🟥 DATABASE - IOException - "+e.getMessage()+" | Ignoring - creating new map for file "+filename+" 🟥");
            return new HashMap<>();
        }
    }

    public static <T> void writeToFile(String filename, Map<String,T> data) throws IOException {
        FileOutputStream file = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(data);
    }
}
