package org.example.Commands;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import org.example.Menegers.CollectionManager;
import org.example.MusicBands.MusicBand;
import org.example.Utility.MusicBandBuilder;
import org.example.Utility.XmlHandler;

import java.util.Scanner;

public class Add_if_min extends AbstractCommand{

    CollectionManager collectionManager;

    public Add_if_min(CollectionManager collectionManager) {
        super("aifm", "add_if_min: добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String... args) throws CannotResolveClassException, ConversionException {
        try {
            if (args.length > 1){
                throw new ArrayIndexOutOfBoundsException("Неверное количество аргументов");
            }
            MusicBand newMusicBand;
            if (args.length == 0){
                newMusicBand = MusicBandBuilder.buildMusicBandByNoArgs(null);
            } else {
                newMusicBand = XmlHandler.DeserializeMusicBandXMLXStream(args[0],collectionManager);
            }
            if (newMusicBand != null) {
                newMusicBand.setId(collectionManager.getCollections().size()+1);
                Scanner scanner = new Scanner(System.in);
                System.out.println(newMusicBand);
                System.out.print("Введите критерий для aifm(скопируйте параметр, написанный большими буквами): ");
                String param = scanner.nextLine();
                if (collectionManager.getMusicBandsByParam(newMusicBand,param).isEmpty()){
                    collectionManager.add(newMusicBand);
                    System.out.println("Элемент добавлен.");
                }
            }else {
                throw new NullPointerException("Ошибка парсинга");
            }
        }catch (ArrayIndexOutOfBoundsException | NullPointerException ex){
            System.out.println(ex.getMessage());
        }
    }
}
