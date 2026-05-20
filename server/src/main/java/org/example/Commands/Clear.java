package org.example.Commands;

import network.Response;
import org.example.Menegers.CollectionManager;

public class Clear extends model.commands.AbstractCommand {

    CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String... args) {
        collectionManager.clear();
        return new Response("коллекция очищена");
    }
}
