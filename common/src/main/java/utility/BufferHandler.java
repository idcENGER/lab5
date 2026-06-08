package utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BufferHandler {

    private static final int MAX_SIZE = 1017;


    public static List<byte[]> getPackets(byte[] data){

        List<byte[]> packets = new ArrayList<>();

        int totalLength = data.length;
        int offset = 0;

        while (offset < totalLength){
            int length = Math.min(MAX_SIZE,totalLength - offset);
            byte[] chunk = new byte[length];
            System.arraycopy(data,offset,chunk,0,length);
            packets.add(chunk);
            offset+=length;
        }
        return packets;
    }
}
