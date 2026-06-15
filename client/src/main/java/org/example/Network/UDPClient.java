package org.example.Network;

import network.Response;
import utility.MessageAssembler;
import utility.MessageFragmenter;
import utility.XmlHandler;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.List;
import java.util.Random;

public class UDPClient {

    private static final Random random = new Random();
    private final DatagramChannel channel;
    private final InetSocketAddress server;

    public UDPClient() throws IOException {
        this.channel = DatagramChannel.open();
        channel.configureBlocking(false);
        this.server = new InetSocketAddress(InetAddress.getLocalHost(),24868);

    }


    public Response sendRequest(String request) throws IOException, InterruptedException {
        int messageId = random.nextInt(1000);
        List<ByteBuffer> fragments = MessageFragmenter.fragment(request.getBytes(),messageId);
        for (ByteBuffer fragment : fragments) {
            channel.send(fragment, server);
        }
        ByteBuffer buffer = ByteBuffer.allocate(MessageFragmenter.MTU);
        MessageAssembler assembler = null;
        String response = null;

        while (true){
            buffer.clear();
            channel.receive(buffer);
            buffer.flip();
            MessageFragmenter.FragmentHeader header = MessageFragmenter.extractHeader(buffer);
            if(header == null){
                continue;
            }
            if(assembler == null){
                assembler = new MessageAssembler(header.messageId,header.totalFragments);
            }

            byte[] fragmentData = MessageFragmenter.extractData(buffer);
            boolean complete = assembler.addFragment(header.fragmentIndex,fragmentData);

            if(complete){
                byte[] msg = assembler.assemble();
                response = new String(msg);
                return (Response) XmlHandler.deserialize(response);
            }

        }
    }
}