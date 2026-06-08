package org.example.Network;

import network.Response;
import utility.BufferHandler;
import utility.XmlHandler;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/*client app worked by UDP in nonblocked mode
* for sharing data used DatagramChannel
* All of network interaction described in this class*/

public class UDPClient {

    private final int BUFFER_SIZE = 1024;
    private final int SERVER_PORT = 24868;
    private final DatagramSocket socket;
    private final InetAddress serverAddress;

    public UDPClient(InetAddress addr) throws IOException {
        this.serverAddress = addr;
        this.socket = new DatagramSocket();
    }


    public Response sendRequest(String request) throws IOException, InterruptedException {

        byte[] respBuffer = new byte[BUFFER_SIZE];
        DatagramPacket respPacket = new DatagramPacket(respBuffer,BUFFER_SIZE);
        byte[] sendData = request.getBytes();
        List<byte[]> packets = BufferHandler.getPackets(sendData);
        for (byte[] packet : packets){
            DatagramPacket sendPacket = new DatagramPacket(
                    packet,
                    packet.length,
                    serverAddress,
                    SERVER_PORT
            );
            socket.send(sendPacket);
        }

        socket.setSoTimeout(500);
        StringBuilder response = new StringBuilder();
        long lastPacketTime = System.currentTimeMillis();
        while (true){
            try {
                socket.receive(respPacket);
                lastPacketTime = System.currentTimeMillis();
                String respStr = new String(
                        respPacket.getData(),
                        respPacket.getOffset(),
                        respPacket.getLength(),
                        StandardCharsets.UTF_8
                );
                response.append(respStr);

                respPacket.setLength(BUFFER_SIZE);
            } catch (SocketTimeoutException e) {
                if (System.currentTimeMillis() - lastPacketTime > 500){
                    break;
                }
            }
        };
        return (Response) XmlHandler.deserialize(String.valueOf(response));
    }
}