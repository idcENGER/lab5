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
            String[] arguments = args[0].split(" ",2);
            int id = Integer.parseInt(arguments[0]);
            MusicBand musicBand = collectionManager.getMusicBandByID(id);
            if (arguments.length == 1){
                if(musicBand != null){
                    System.out.println("Обновление музыкальной группы...");
                    MusicBandBuilder.MusicBandUpdater(musicBand);
                }
            }else {
                //MusicBand newMusicBand = XmlHandler.DeserializeMusicBandXMLXStream(arguments[1],collectionManager);
                if(musicBand != null){
                    System.out.println("Обновление музыкальной группы");
                  //  MusicBandBuilder.RawMusicBandUpdater(musicBand,newMusicBand);
                }else {
                    throw new NullPointerException("Ошибка парсинга");
                }
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
