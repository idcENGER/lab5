package org.example.Network;

import network.Request;
import org.example.Menegers.CommandInvoker;
import utility.BufferHandler;
import utility.XmlHandler;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;


public class UDPServer {

    private final CommandInvoker commandInvoker;
    private final int BUFFER_SIZE = 1024;

    public UDPServer(CommandInvoker commandInvoker,int port){
        this.commandInvoker = commandInvoker;
        try (DatagramSocket socket = new DatagramSocket(port)){
            socket.setSoTimeout(1000);
            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket requestPacket = new DatagramPacket(buffer,BUFFER_SIZE);
            while (true) {
                try {
                    socket.receive(requestPacket);
                    String message = new String(
                            requestPacket.getData(),
                            requestPacket.getOffset(),
                            requestPacket.getLength(),
                            StandardCharsets.UTF_8
                    );
                    Request request = (Request) XmlHandler.deserialize(message);
                    String response = response(request);
                    List<byte[]> packets = BufferHandler.getPackets(response.getBytes());
                    for (byte[] packet : packets){
                        DatagramPacket sendPacket = new DatagramPacket(
                                packet,
                                packet.length,
                                requestPacket.getAddress(),
                                requestPacket.getPort()
                        );
                        socket.send(sendPacket);
                    }
                }catch (SocketTimeoutException ignore){
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } finally {
                    requestPacket.setLength(BUFFER_SIZE);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String response(Request request) throws IOException, ClassNotFoundException {
        if (request.getArguments() == null){return XmlHandler.serialize(this.commandInvoker.execute(request.getCommand().getName(),null));}
        return XmlHandler.serialize(this.commandInvoker.execute(request.getCommand().getName(), request.getArguments()));
    }
}