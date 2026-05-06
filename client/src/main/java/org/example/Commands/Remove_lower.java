package org.example.Commands;

import org.example.Menegers.CollectionManager;
import org.example.MusicBands.MusicBand;
import org.example.Utility.MusicBandBuilder;
import org.example.Utility.XmlHandler;

import java.io.IOException;
import java.util.Scanner;

public class Remove_lower extends AbstractCommand{

    CollectionManager collectionManager;

    public Remove_lower(CollectionManager collectionManager) {
        super("rml", "remove_lower: удалить из коллекции все элементы, меньшие, чем заданный");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String... args) throws IOException, ClassNotFoundException {
        try {
            MusicBand element;
            if (args.length ==0){
                element = MusicBandBuilder.buildMusicBandByNoArgs(null);
            }else {
                element = XmlHandler.DeserializeMusicBandXMLXStream(args[0],collectionManager);
                if (element == null){
                    throw new NullPointerException("Ошибка парсинга");
                }
            }
            Scanner scanner = new Scanner(System.in);
            System.out.println(element);
            System.out.print("Введите критерий для rml(скопируйте параметр, написанный большими буквами): ");
            String param = scanner.nextLine();
            collectionManager.getCollections().removeAll(collectionManager.getMusicBandsByParam(element, param));
        }catch (ArrayIndexOutOfBoundsException | NullPointerException exception){
            System.out.println(exception.getMessage());
        }
    }
}
