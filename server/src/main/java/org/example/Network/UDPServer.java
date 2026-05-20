package org.example.Network;

import org.example.Menegers.CommandInvoker;
import utility.BufferHandler;
import utility.XmlHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.List;

public class UDPServer {

    private final CommandInvoker commandInvoker;

    public UDPServer(CommandInvoker commandInvoker,int port,int buff){
        this.commandInvoker = commandInvoker;
        try (DatagramChannel channel = DatagramChannel.open()){
            InetSocketAddress address = new InetSocketAddress(port);
            channel.bind(address);
            ByteBuffer buffer = ByteBuffer.allocate(buff);
            channel.configureBlocking(false);
            while (true) {
                buffer.clear();
                SocketAddress sender = channel.receive(buffer);
                if (sender != null) {
                    buffer.flip();
                    byte[] data = new byte[buffer.remaining()];
                    buffer.get(data);
                    String message = new String(data);
                    buffer.clear();
                    byte[] resp = response(message).getBytes();
                    List<byte[]> chunks = BufferHandler.getPackets(resp, buff);
                    for (int i = 0; i< chunks.size(); i++){
                        if(i == chunks.size() -1){
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            baos.write(chunks.get(i));
                            baos.write(1);
                            byte[] chunk = baos.toByteArray();
                            buffer.put(chunk);
                        }else{
                        byte[] chunk = chunks.get(i);
                        buffer.put(chunk);
                        }
                        buffer.flip();
                        channel.send(buffer, sender);
                        buffer.clear();
                    }
                }
                Thread.sleep(100);
            }

        } catch (IOException | InterruptedException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String response(String require) throws IOException, ClassNotFoundException {
        return XmlHandler.serialize(this.commandInvoker.execute(require));
    }
}