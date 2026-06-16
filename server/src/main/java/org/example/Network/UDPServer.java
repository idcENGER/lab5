package org.example.Network;

import network.Request;
import org.example.Menegers.CommandInvoker;
import utility.MessageAssembler;
import utility.MessageFragmenter;
import utility.XmlHandler;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UDPServer {

    private static final Logger logger = LoggerFactory.getLogger(UDPServer.class);
    private final Random random = new Random();
    private final CommandInvoker commandInvoker;
    private final int PORT;


    public UDPServer(CommandInvoker commandInvoker, int port){
        this.commandInvoker = commandInvoker;
        this.PORT = port;
    }

    public void run() throws IOException {
        Selector selector = Selector.open();
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.bind(new InetSocketAddress(PORT));
        logger.info("Сервер запущен на порту {}", PORT);
        SelectionKey key = channel.register(selector, SelectionKey.OP_READ);
        key.attach(new ServerContext(channel));
        while (true) {
            int readyCount = selector.select();

            if (readyCount == 0) {
                continue;
            }

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey readyKey = iterator.next();
                iterator.remove();

                if (!readyKey.isValid()) {
                    continue;
                }

                if (readyKey.isReadable()) {
                    serve(readyKey);
                }

            }
        }
    }

    private void serve(SelectionKey key) throws IOException {

        ServerContext serverContext = (ServerContext)key.attachment();
        DatagramChannel channel = serverContext.channel;
        ByteBuffer buffer = ByteBuffer.allocate(MessageFragmenter.MTU);
        buffer.clear();

        SocketAddress sender = channel.receive(buffer);
        logger.info("пользователь {} отправил данные",sender);
        if (sender == null){
            return;
        }

        buffer.flip();

        MessageFragmenter.FragmentHeader header = MessageFragmenter.extractHeader(buffer);
        if (header == null){
            logger.warn("заголовок пуст");
            return;
        }

        byte[] fragmentData = MessageFragmenter.extractData(buffer);

        MessageAssembler assembler = serverContext.getMessageAssembler(header.messageId, header.totalFragments);

        try {
            boolean complete = assembler.addFragment(header.fragmentIndex,fragmentData);

            if(complete){
                byte[] fullMsg = assembler.assemble();
                String requestString = new String(fullMsg);
                Request request = (Request)XmlHandler.deserialize(requestString);
                String responseStr = response(request);
                byte[] response = responseStr.getBytes();
                int messageId = random.nextInt(1000);

                List<ByteBuffer> fragments = MessageFragmenter.fragment(response,messageId);
                for (ByteBuffer fragment : fragments) {
                    channel.send(fragment, sender);
                }
                logger.info("ответ отправлен пользователю {},содержание:{}",sender,responseStr);
            }
        }catch (IllegalStateException stateException){
            serverContext.removeAssembler(header.messageId);
        } catch (ClassNotFoundException | NullPointerException ignored) {
        }

    }

    private String response(Request request) throws IOException, ClassNotFoundException {
        if (request.getArguments() == null) {
            logger.info("выполняется запрос {}",request);
            return XmlHandler.serialize(this.commandInvoker.execute(request.getCommand().getName(), null));
        }
        logger.info("выполняется запрос {}",request);
        return XmlHandler.serialize(this.commandInvoker.execute(request.getCommand().getName(), request.getArguments()));
    }

    private static class ServerContext {


        final DatagramChannel channel;
        final Map<Integer,MessageAssembler> assemblers = new ConcurrentHashMap<>();

        ServerContext(DatagramChannel channel){
            this.channel = channel;
        }

        MessageAssembler getMessageAssembler(int msgId,int totalFragments){
            return assemblers.computeIfAbsent(msgId, id -> new MessageAssembler(msgId,totalFragments));
        }

        void removeAssembler(int msgId){
            assemblers.remove(msgId);
        }
    }
}
