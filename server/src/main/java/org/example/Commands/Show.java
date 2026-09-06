package org.example.Commands;

import model.MusicBands.MusicBand;
import network.Response;
import org.example.Menegers.CollectionManager;

import java.util.Comparator;
import java.util.TreeSet;


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
        TreeSet<MusicBand> collection = new TreeSet<>(
                Comparator.comparing((MusicBand band) -> band.getName(),String.CASE_INSENSITIVE_ORDER)
        );
        collection.addAll(collectionManager.getCollections());
        s.append(collection).append("\n");
        s.setLength(s.length() - 1);
        return new Response(s.toString());
    }
}
