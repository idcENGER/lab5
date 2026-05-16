package org.example.Network;

import network.Response;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/*client app worked by UDP in nonblocked mode
* for sharing data used DatagramChannel
* All of network interaction described in this class*/

public class UDPClient {

    private ByteBuffer sendBuffer;
    private ByteBuffer receiveBuffer;
    private final int BUFFER_SIZE = 1024;
    private final DatagramChannel channel;
    private final InetSocketAddress serverAddress;

    public UDPClient(InetAddress addr, int port) throws IOException {
        this.serverAddress = new InetSocketAddress(addr, port);
        this.channel = DatagramChannel.open().bind(null).connect(serverAddress);
        this.channel.configureBlocking(false);
        sendBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        receiveBuffer = ByteBuffer.allocate(BUFFER_SIZE);
    }


    public Response request(String command) throws IOException {
        byte[] require = command.getBytes();
        sendBuffer.clear();
        sendBuffer.put(require);
        sendBuffer.flip();
        channel.send(sendBuffer,serverAddress);
        receiveBuffer.clear();
        InetSocketAddress serverSocket = (InetSocketAddress) channel.receive(receiveBuffer);
        if (serverSocket != null){
            receiveBuffer.flip();
            byte[] data = new byte[receiveBuffer.remaining()];
            receiveBuffer.get(data);
            String response = new String(data);
            return new Response(response);
        }else return null;
    }

    public ByteBuffer getSendBuffer() {
        return sendBuffer;
    }

    public void setSendBuffer(ByteBuffer sendBuffer) {
        this.sendBuffer = sendBuffer;
    }

    public ByteBuffer getReceiveBuffer() {
        return receiveBuffer;
    }

    public void setReceiveBuffer(ByteBuffer receiveBuffer) {
        this.receiveBuffer = receiveBuffer;
    }
}