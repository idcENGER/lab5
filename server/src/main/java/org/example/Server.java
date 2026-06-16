package org.example;
//421790
import org.example.Commands.*;
import org.example.Menegers.*;
import org.example.Network.UDPServer;

import java.io.IOException;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws IOException{
        if (args.length == 0) {
            logger.error("Ошибка: Имя файла должно передаваться через аргумент командной строки.");
            System.exit(0);
        }
        Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("сервер прекратил работу");
            mainThread.interrupt();
        }));
        CommandInvoker commandInvoker = new CommandInvoker();
        CollectionManager collectionManager = new CollectionManager(commandInvoker);
        commandInvoker.register(new Help(commandInvoker));
        commandInvoker.register(new Info(collectionManager));
        commandInvoker.register(new Clear(collectionManager));
        commandInvoker.register(new Show(collectionManager));
        commandInvoker.register(new Add(collectionManager));
        commandInvoker.register(new Save(collectionManager, Path.of(args[0])));
        commandInvoker.register(new Remove_by_id(collectionManager));
        commandInvoker.register(new Update_by_id(collectionManager));
        commandInvoker.register(new Filter_contains_name(collectionManager));
        commandInvoker.register(new Filter_starts_with_name(collectionManager));
        commandInvoker.register(new Group_counting_by_creation_date(collectionManager));
        commandInvoker.register(new Add_if_min(collectionManager));
        commandInvoker.register(new Remove_greater(collectionManager));
        commandInvoker.register(new Remove_lower(collectionManager));
        commandInvoker.register(new Execute_script(commandInvoker));
        collectionManager.recoverCollection(args[0]);
        UDPServer server = new UDPServer(commandInvoker,24868);
        server.run();
    }
}

