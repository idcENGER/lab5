package org.example.Commands;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import org.example.Menegers.CollectionManager;
import org.example.MusicBands.MusicBand;
import org.example.MusicBands.Person;
import org.example.Utility.MusicBandBuilder;
import org.example.Utility.XmlHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

public class Add extends AbstractCommand{
    CollectionManager collectionManager;

    public Add(CollectionManager collectionManager) {
        super("add","добавить новый элемент в коллекцию. Команда от одного аргумента - добавить по XML\n" +
                "от девяти аргументов - явно задать параметры, без аргументов - интерактивный режим");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String... args) throws IOException, CannotResolveClassException, ConversionException {
        try {
            if (args.length == 1){
                MusicBand musicBand = null;
                try {
                    musicBand = XmlHandler.DeserializeMusicBandXMLXStream(args[0],collectionManager);
                }catch (StreamException streamException){
                    System.out.println(streamException.getMessage());
                }
                if (musicBand != null){
                    boolean PassportIdIsUnique = collectionManager.getCollections().stream().map(MusicBand::getFrontMan).map(Person::getPassportID).allMatch(new HashSet<String>()::add);
                    boolean IdIsUnique = collectionManager.getCollections().stream().map(MusicBand::getId).allMatch(new HashSet<Integer>()::add);
                    if (collectionManager.inCollection(musicBand)){
                        System.out.println("Такая музыкальная группа уже есть");
                    }else if(!PassportIdIsUnique){
                        System.out.println("Неверные паспортные данные: такие данные уже есть");
                    }else if(!IdIsUnique){
                        System.out.println("ID");
                    }else{
                        collectionManager.add(musicBand);
                        System.out.println("Музыкальная группа успешно добавлена");
                    }
                }else {
                    throw new NullPointerException("Ошибка парсинга");
                }
            }else{
                if (args.length == 0){
                    MusicBand musicBand = MusicBandBuilder.buildMusicBandByNoArgs(collectionManager);
                    boolean IdIsUnique = collectionManager.getCollections().stream().map(MusicBand::getId).allMatch(new HashSet<Integer>()::add);
                    boolean PassportIdIsUnique = collectionManager.getCollections().stream().map(MusicBand::getFrontMan).map(Person::getPassportID).allMatch(new HashSet<String>()::add);
                    if (collectionManager.inCollection(musicBand)){
                        System.out.println("Такая музыкальная группа уже есть");
                    }else if(!PassportIdIsUnique){
                        System.out.println("Неверные паспортные данные: такие данные уже есть");
                    }else if (!IdIsUnique){
                        System.out.println("ID");
                    }else{
                        collectionManager.add(musicBand);
                        System.out.println("Музыкальная группа успешно добавлена");
                    }

                }else{
                    ArrayList<String> params = new ArrayList<>();
                    for (String argument: args){
                        String[] s = argument.split(";");
                        params.addAll(Arrays.asList(s));
                    }
                    if(params.size() != 9){
                        throw new ArrayIndexOutOfBoundsException("Неверное количество аргументов");
                    }
                    MusicBand musicBand = MusicBandBuilder.buildMusicBandByParams(collectionManager,params);
                    if (musicBand == null){
                        throw new NullPointerException("Неверный аргумент");
                    }
                    collectionManager.add(musicBand);
                    System.out.println("Музыкальная группа успешно добавлена");
                }
            }
        }catch (IllegalArgumentException | NullPointerException | StreamException ex){
            System.out.println("Музыкальная группа не добавлена: " + ex.getMessage());
        }
    }
}
