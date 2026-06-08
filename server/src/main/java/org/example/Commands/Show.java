package org.example.Commands;

import network.Response;
import org.example.Menegers.CollectionManager;


public class Show extends model.commands.Command {

    CollectionManager collectionManager;

    public Show(CollectionManager collectionManager) {
        super("show", "показать информацию о коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String... args) {
        if (collectionManager.getSize() == 0){return new Response("Коллекция пуста");}
        StringBuilder s = new StringBuilder();
        for (var i : collectionManager.getCollections()) {
            s.append(i).append("\n");
        }
        s.setLength(s.length() - 1);
        return new Response(s.toString());
    }
}
