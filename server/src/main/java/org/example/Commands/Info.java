package org.example.Commands;

import network.Response;
import org.example.Menegers.CollectionManager;

public class Info extends model.commands.Command {

    CollectionManager collectionManager;

    public Info(CollectionManager collectionManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String... args) {
        return new Response(collectionManager.toString());
    }

}
