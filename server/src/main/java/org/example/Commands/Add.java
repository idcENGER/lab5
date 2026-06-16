package org.example.Commands;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import model.MusicBands.MusicBand;
import model.MusicBands.Person;
import model.commands.Command;
import network.Response;
import org.example.Menegers.CollectionManager;
import utility.XmlHandler;

import java.io.IOException;
import java.util.HashSet;

public class Add extends Command {
    CollectionManager collectionManager;

    public Add(CollectionManager collectionManager) {
        super("add","добавить новый элемент в коллекцию. Команда от одного аргумента - добавить по XML\n" +
                "от девяти аргументов - явно задать параметры, без аргументов - интерактивный режим");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String... args) throws CannotResolveClassException, ConversionException, IOException, ClassNotFoundException {

        MusicBand musicBand = (MusicBand) XmlHandler.deserialize(args[0]);

        boolean PassportIdIsUnique = collectionManager.getCollections().stream().map(MusicBand::getFrontMan).map(Person::getPassportID).allMatch(new HashSet<String>()::add);
        boolean IdIsUnique = collectionManager.getCollections().stream().map(MusicBand::getId).allMatch(new HashSet<Integer>()::add);
        if (collectionManager.inCollection(musicBand)){
            return new Response("такая группа уже есть");
        }else if(!PassportIdIsUnique){
            return new Response("неверные паспортные данные: такие данные уже есть");
        }else if(!IdIsUnique){
            return new Response("группа с таким ID уже есть");
        }else{
            musicBand.setId(collectionManager.getSize()+1);
            collectionManager.add(musicBand);
            collectionManager.save();
            return new Response("Музыкальная группа успешно добавлена");
        }
    }
}
