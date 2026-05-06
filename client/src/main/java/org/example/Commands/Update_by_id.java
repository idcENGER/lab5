package org.example.Commands;

import org.example.Menegers.CollectionManager;
import org.example.MusicBands.MusicBand;
import org.example.Utility.MusicBandBuilder;
import org.example.Utility.XmlHandler;

public class Update_by_id extends AbstractCommand{
    CollectionManager collectionManager;

    public Update_by_id(CollectionManager collectionManager) {
        super("ubi", "update_by_id: обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String... args) {
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
                MusicBand newMusicBand = XmlHandler.DeserializeMusicBandXMLXStream(arguments[1],collectionManager);
                if(musicBand != null){
                    System.out.println("Обновление музыкальной группы");
                    MusicBandBuilder.RawMusicBandUpdater(musicBand,newMusicBand);
                }else {
                    throw new NullPointerException("Ошибка парсинга");
                }
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}
