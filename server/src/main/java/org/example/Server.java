package org.example;
//421790
import org.example.Commands.*;
import org.example.Menegers.*;
import org.example.Network.UDPServer;

import java.io.IOException;
import java.nio.file.Path;

public class Server {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Ошибка: Имя файла должно передаваться через аргумент командной строки.");
            System.exit(0);
        }
        CollectionManager collectionManager = new CollectionManager();
        CommandInvoker commandInvoker = new CommandInvoker();
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
        //commandInvoker.register(new Execute_script(commandInvoker));
        collectionManager.recoverCollection("/home/enger/Projects/lab6/server/src/main/resources/data.xml");
        UDPServer server = new UDPServer(commandInvoker,24868,1024);
    }
}

