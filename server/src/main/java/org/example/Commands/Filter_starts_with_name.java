package org.example.Commands;

import network.Response;
import org.example.Menegers.CollectionManager;
import model.MusicBands.*;
import utility.XmlHandler;

import java.util.HashSet;

public class Filter_starts_with_name extends model.commands.Command {

    CollectionManager collectionManager;

    public Filter_starts_with_name(CollectionManager collectionManager) {
        super("fsn", "fstarts_with_name: вывести элементы, значение поля name которых начинается с заданной подстроки");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String... args){
        try{
            HashSet<MusicBand> set = collectionManager.filterMusicBandByName(XmlHandler.SpaceRemover(args[0]),true);
            if (!set.isEmpty()){
                StringBuilder s = new StringBuilder();
                for (MusicBand band : set){
                    s.append(band).append("\n");
                }
                s.setLength(s.length() - 1);
                return new Response(s.toString());
            }else{
                return new Response("Группы с таким именем не нашлось");
            }
        }catch (ArrayIndexOutOfBoundsException ex){
            System.out.println(ex.getMessage());
            return new Response("Группы с таким именем не нашлось");
        }
    }
}
