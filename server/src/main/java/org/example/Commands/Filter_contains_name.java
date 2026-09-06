package org.example.Commands;

import network.Response;
import org.example.Menegers.CollectionManager;
import model.MusicBands.*;
import utility.XmlHandler;

import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;

public class Filter_contains_name extends model.commands.Command {
    CollectionManager collectionManager;
    public Filter_contains_name(CollectionManager collectionManager) {
        super("fcn", "filter_contains_name: вывести элементы," +
                " значение поля name которых содержит заданную подстроку");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String... args) {
        HashSet<MusicBand> set = collectionManager.filterMusicBandByName(XmlHandler.SpaceRemover(args[0]), false);
        TreeSet<MusicBand> collection = new TreeSet<>(
                Comparator.comparing((MusicBand band) -> band.getName(),String.CASE_INSENSITIVE_ORDER)
        );
        collection.addAll(set);
        if (!collection.isEmpty()) {
            StringBuilder s = new StringBuilder();
            for (MusicBand band : collection){
                s.append(band).append("\n");
            }
            s.setLength(s.length() - 1);
            return new Response(s.toString());
        } else {
            return new Response("Группы с таким именем не нашлось");
        }
    }
}
