package org.example.Commands;

import network.Response;
import org.example.Menegers.CollectionManager;
import model.MusicBands.*;
import utility.XmlHandler;
import utility.MusicBandBuilder;

import java.io.IOException;
import java.util.Scanner;

public class Remove_lower extends model.commands.Command {

    CollectionManager collectionManager;

    public Remove_lower(CollectionManager collectionManager) {
        super("rml", "remove_lower: удалить из коллекции все элементы, меньшие, чем заданный");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String... args) throws IOException, ClassNotFoundException {
        /*try {
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
        }*/
        return null;
    }
}
