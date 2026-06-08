package org.example.Commands;

import network.Response;
import org.example.Menegers.CollectionManager;

import java.io.IOException;

public class Clear extends model.commands.Command {

    CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String... args) throws IOException, ClassNotFoundException {
        collectionManager.clear();
        collectionManager.save();
        return new Response("коллекция очищена");
    }
}
