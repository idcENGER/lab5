package org.example.Commands;

import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import org.example.Menegers.CollectionManager;
import org.example.MusicBands.MusicBand;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Group_counting_by_creation_date extends AbstractCommand{

    CollectionManager collectionManager;

    public Group_counting_by_creation_date(CollectionManager collectionManager) {
        super("group", "сгруппировать элементы коллекции по значению поля creationDate," +
                " вывести количество элементов в каждой группе");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String... args) {
        try {
            if(args.length != 0){
                throw new ArrayIndexOutOfBoundsException("Команда не поддерживает аргументы");
            }
            HashSet<MusicBand> collection = (HashSet<MusicBand>) collectionManager.getCollections().clone();
        Iterator<MusicBand> iterator = collection.iterator();
        ArrayList<ZonedDateTime> date = new ArrayList<>();
        while (iterator.hasNext()){
            MusicBand musicBand = iterator.next();
            int counter = 0;
            ZonedDateTime creationDate = musicBand.getCreationDate();
            if(!date.contains(creationDate)){
                for (MusicBand i : collection){
                    if (creationDate.equals(i.getCreationDate())){
                        counter+=1;
                    }
                }
                date.add(creationDate);
                System.out.println(creationDate + "->" + counter);
            }
            }
        }catch (CannotResolveClassException ex){
            System.out.println(ex.getMessage());
        }
    }
}
