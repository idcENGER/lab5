package org.example.Commands;

import network.Response;
import org.example.Menegers.CollectionManager;
import model.MusicBands.*;
import utility.XmlHandler;
import java.io.IOException;

public class Remove_greater extends model.commands.Command {

    CollectionManager collectionManager;

    public Remove_greater(CollectionManager collectionManager) {
        super("rmg", "remove_greater: удалить из коллекции все элементы, превышающие заданный");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String... args) throws IOException, ClassNotFoundException {
        MusicBand musicBand = (MusicBand) XmlHandler.deserialize(args[0]);
        String param = args[1];
        int size = collectionManager.getSize();
        collectionManager.getCollections().retainAll(collectionManager.getMusicBandsByParam(musicBand,param));
        collectionManager.save();
        return new Response("из коллекции было удалены музыкальные группы:" + (size - collectionManager.getSize()));
    }
}
