package org.example.Commands;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import network.Response;
import org.example.Menegers.CollectionManager;
import utility.XmlHandler;
import model.MusicBands.*;

import java.io.IOException;

public class Add_if_min extends model.commands.Command {

    CollectionManager collectionManager;

    public Add_if_min(CollectionManager collectionManager) {
        super("aifm", "add_if_min: добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String... args) throws CannotResolveClassException, ConversionException, IOException, ClassNotFoundException {
        MusicBand musicBand = (MusicBand) XmlHandler.deserialize(args[0]);
        String param = args[1];
        if (collectionManager.getMusicBandsByParam(musicBand,param).isEmpty()){
            musicBand.setId(collectionManager.getSize()+1);
            collectionManager.add(musicBand);
            collectionManager.save();
            return new Response("Музыкальная группа добавлена");
        }
        return new Response("Группа не добавлена: группы меньше нет");
    }
}
