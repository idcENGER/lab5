package org.example.Commands;

import org.example.Menegers.CollectionManager;
import org.example.MusicBands.MusicBand;
import org.example.Utility.XmlHandler;

import java.util.HashSet;

public class Filter_starts_with_name extends AbstractCommand{

    CollectionManager collectionManager;

    public Filter_starts_with_name(CollectionManager collectionManager) {
        super("fsn", "fstarts_with_name: вывести элементы, значение поля name которых начинается с заданной подстроки");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String... args){
        try{
            if(args.length == 0){
                throw new ArrayIndexOutOfBoundsException("Аргумент не может быть равен нулю");
            }
            HashSet<MusicBand> set = collectionManager.filterMusicBandByName(XmlHandler.SpaceRemover(args[0]),true);
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
    }
}
