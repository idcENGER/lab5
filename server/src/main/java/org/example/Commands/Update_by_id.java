package org.example.Commands;

import network.Response;
import org.example.Menegers.CollectionManager;
import model.MusicBands.*;
import utility.MusicBandBuilder;
import utility.XmlHandler;

public class Update_by_id extends model.commands.Command {
    CollectionManager collectionManager;

    public Update_by_id(CollectionManager collectionManager) {
        super("ubi", "update_by_id: обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String... args) {
        try {
            int id = Integer.parseInt(args[0]);
            MusicBand newMusicBand  = (MusicBand) XmlHandler.deserialize(args[1]);
            MusicBand musicBand = collectionManager.getMusicBandByID(id);
            MusicBandBuilder.MusicBandUpdater(musicBand,newMusicBand);
            return new Response("Музыкальная группа обновлена");
        }catch (NullPointerException e){
            return new Response("Музыкальной группы с таким id нет");
        }
    }
}
