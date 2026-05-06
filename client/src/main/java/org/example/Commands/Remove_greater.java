package org.example.Commands;

import org.example.Menegers.CollectionManager;
import org.example.MusicBands.MusicBand;
import org.example.Utility.MusicBandBuilder;
import org.example.Utility.XmlHandler;

import java.util.Scanner;

public class Remove_greater extends AbstractCommand{

    CollectionManager collectionManager;

    public Remove_greater(CollectionManager collectionManager) {
        super("rmg", "remove_greater: удалить из коллекции все элементы, превышающие заданный");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String... args){
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
            System.out.print("Введите критерий для rmg(скопируйте параметр, написанный большими буквами): ");
            String param = scanner.nextLine();
            collectionManager.getCollections().retainAll(collectionManager.getMusicBandsByParam(element,param));
        }catch (ArrayIndexOutOfBoundsException | NullPointerException e){
            System.out.println(e.getMessage());
        }
    }
}
