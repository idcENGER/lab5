package org.example.Commands;

import network.Response;
import org.example.Menegers.CollectionManager;
import model.MusicBands.*;
import utility.XmlHandler;

import java.io.IOException;

public class Remove_lower extends model.commands.Command {

    CollectionManager collectionManager;

    public Remove_lower(CollectionManager collectionManager) {
        super("rml", "remove_lower: удалить из коллекции все элементы, меньшие, чем заданный");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String... args) throws IOException, ClassNotFoundException {
        MusicBand musicBand = (MusicBand) XmlHandler.deserialize(args[0]);
        String param = args[1];
        int size = collectionManager.getSize();
        collectionManager.getCollections().removeAll(collectionManager.getMusicBandsByParam(musicBand, param));
        collectionManager.save();
        return new Response("из коллекции было удалены музыкальные группы:" + (size - collectionManager.getSize()));
    }
}
