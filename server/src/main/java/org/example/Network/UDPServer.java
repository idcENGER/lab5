package org.example.Network;

import org.example.Menegers.CommandInvoker;
import utility.XmlHandler;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UDPServer {

    private final CommandInvoker commandInvoker;
    private final InetSocketAddress address;
    private final int PORT;
    private final int BUFFER_SIZE;

    public UDPServer(CommandInvoker commandInvoker,int port,int buff){
        this.commandInvoker = commandInvoker;
        this.PORT = port;
        this.BUFFER_SIZE = buff;
        try (DatagramChannel channel = DatagramChannel.open()){
            address = new InetSocketAddress(PORT);
            channel.bind(address);
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            channel.configureBlocking(false);
            while (true) {
                buffer.clear();
                SocketAddress sender = channel.receive(buffer);
                if (sender != null) {
                    buffer.flip();
                    byte[] data = new byte[buffer.remaining()];
                    buffer.get(data);
                    String message = new String(data);
                    System.out.println(sender + ":" + message);
                    buffer.clear();
                    buffer.flip();
                    channel.send(buffer, sender);
                }
                Thread.sleep(100);
            }

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String response(String require) throws IOException {
        return XmlHandler.serialize(commandInvoker.execute(require));
    }
}