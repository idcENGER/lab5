package org.example.Network;

import model.commands.AbstractCommand;
import model.commands.Command;
import network.Request;
import network.Response;
import utility.XmlHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/*client app worked by UDP in nonblocked mode
* for sharing data used DatagramChannel
* All of network interaction described in this class*/

public class UDPClient {

    private final int BUFFER_SIZE = 1024;
    private final long TIME_OUT = 5000;
    private final DatagramChannel channel;
    private final SocketAddress address;

    public UDPClient(InetAddress addr, int port) throws IOException {
        this.address = new InetSocketAddress(addr, port);
        this.channel = DatagramChannel.open();
        this.channel.configureBlocking(false);
    }


    public Response sendRequest(Request request) throws IOException, InterruptedException {
        String command = request.getCommand();
        ByteBuffer sendBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        byte[] require = command.getBytes();
        sendBuffer.clear();
        sendBuffer.put(require);
        sendBuffer.flip();
        channel.send(sendBuffer,address);

        ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        long startTime = System.currentTimeMillis();
        StringBuilder response = new StringBuilder();
        boolean received = false;

        while (!received){
            readBuffer.clear();
            SocketAddress address = channel.receive(readBuffer);
            if (address != null){
                readBuffer.flip();
                byte[] data = new byte[readBuffer.remaining()];
                readBuffer.get(data);
                response.append(new String(data));
                if (data[data.length -1] == 1){
                    received = true;
                }
            }

            if (System.currentTimeMillis() - startTime > TIME_OUT){
                System.out.println("превышено время ожидания");
                return null;
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return (Response) XmlHandler.deserialize(response.toString());

    }
}