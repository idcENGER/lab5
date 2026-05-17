package utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BufferHandler {

    public static List<byte[]> getPackets(byte[] data,int chunkSize){
        if (chunkSize <= 0){
            throw new IllegalArgumentException("Размер должен быть положительным");
        }

        List<byte[]> chunks = new ArrayList<>();

        for (int i = 0; i < data.length; i+=chunkSize){
            int end = Math.min(i+chunkSize,data.length);
            chunks.add(Arrays.copyOfRange(data,i,end));
        }
        return chunks;
    }
}
