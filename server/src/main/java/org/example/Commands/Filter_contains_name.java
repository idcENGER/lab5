package org.example.Commands;

import network.Response;
import org.example.Menegers.CollectionManager;
import model.MusicBands.*;
import utility.XmlHandler;

import java.util.HashSet;

public class Filter_contains_name extends model.commands.Command {
    CollectionManager collectionManager;
    public Filter_contains_name(CollectionManager collectionManager) {
        super("fcn", "filter_contains_name: вывести элементы," +
                " значение поля name которых содержит заданную подстроку");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String... args){
        try{
            if(args.length == 0){
                throw new ArrayIndexOutOfBoundsException("Аргумент не может быть равен нулю");
            }
            HashSet<MusicBand> set = collectionManager.filterMusicBandByName(XmlHandler.SpaceRemover(args[0]),false);
            if (!set.isEmpty()){
                for (MusicBand band : set){
                    System.out.println(band);
                }
            }else{
                System.out.println("Группы с таким именем не нашлось");
            }
        }catch (ArrayIndexOutOfBoundsException ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
