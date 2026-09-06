package org.example.Network;

import network.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.ByteHandler;
import utility.MessageAssembler;
import utility.MessageFragmenter;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public final class UDPClient {

    private static final Logger logger = LoggerFactory.getLogger(UDPClient.class);

    private static DatagramSocket socket;
    private static InetSocketAddress server;
    private static final int TIME_OUT = 2000;
    private static final int ATTEMPTS = 5;

    public UDPClient() throws IOException {
        socket = new DatagramSocket();
        server = new InetSocketAddress(InetAddress.getLocalHost(),24868);
    }


    public Response sendRequest(String request) throws IOException, InterruptedException {
        socket.setSoTimeout(TIME_OUT);

        int requestId = ThreadLocalRandom.current().nextInt(1000);
        List<ByteBuffer> fragments = MessageFragmenter.fragment(request.getBytes(),requestId);

        sendFragments(fragments);

        Map<Integer, MessageAssembler> assemblers = new HashMap<>();
        byte[] buffer = new byte[MessageFragmenter.MTU];

        long deadline = System.currentTimeMillis() + (long) TIME_OUT * ATTEMPTS;
        int attempts = 1;

        while (System.currentTimeMillis() < deadline){
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            try {
                socket.receive(packet);
            } catch (SocketTimeoutException e) {
                if (attempts >= ATTEMPTS) {
                    break;
                }
                attempts++;
                sendFragments(fragments);
            }
            ByteBuffer bb = ByteBuffer.wrap(
                    packet.getData(),
                    packet.getOffset(),
                    packet.getLength()
            );

            if (bb.remaining() < Integer.BYTES * 3) {
                continue;
            }

            int messageId = bb.getInt();
            int fragmentIndex = bb.getInt();
            int totalFragments = bb.getInt();

            byte[] payload = new byte[bb.remaining()];
            bb.get(payload);

            MessageAssembler assembler = assemblers.computeIfAbsent(
                    messageId,
                    id -> new MessageAssembler(id, totalFragments)
            );

            try {
                boolean complete = assembler.addFragment(fragmentIndex, payload);

                if (complete) {
                    byte[] fullMessage = assembler.assemble();
                    Response response = (Response) ByteHandler.fromBytes(fullMessage);
                    logger.info("response received: {}, {} bytes",response,fullMessage.length);

                    return response;
                }
            } catch (IllegalStateException ex) {
                assemblers.remove(messageId);
            } catch (ClassNotFoundException e){
                logger.error("Class not found");
            }
        }
        return new Response("нет ответа от сервера");
    }

    private static void sendFragments(List<ByteBuffer> fragments) throws IOException {
        for (ByteBuffer fragment : fragments) {
            byte[] bytes = new byte[fragment.remaining()];
            fragment.duplicate().get(bytes);
            DatagramPacket dp = new DatagramPacket(
                    bytes,
                    bytes.length,
                    server
            );
            socket.send(dp);
        }
    }
}