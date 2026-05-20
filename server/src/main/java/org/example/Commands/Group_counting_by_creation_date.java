package org.example.Commands;

import network.Response;
import org.example.Menegers.CollectionManager;
import model.MusicBands.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class Group_counting_by_creation_date extends model.commands.AbstractCommand {

    CollectionManager collectionManager;

    public Group_counting_by_creation_date(CollectionManager collectionManager) {
        super("group", "сгруппировать элементы коллекции по значению поля creationDate," +
                " вывести количество элементов в каждой группе");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String... args) {
        StringBuilder s = new StringBuilder();
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
                s.append(creationDate).append("->").append(counter).append("\n");
            }
        }
        s.setLength(s.length() - 1);
        return new Response(s.toString());
    }
}
