package utility;

import java.io.*;

public class ByteHandler {

    public static Object fromBytes(byte[] bytes) throws IOException, ClassNotFoundException{
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return ois.readObject();
        }
    }

    public static byte[] toBytes(Object o) throws IOException{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(o);
        }

        return bos.toByteArray();
    }

}
