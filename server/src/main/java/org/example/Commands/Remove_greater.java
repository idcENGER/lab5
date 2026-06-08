package org.example.Commands;

import network.Response;
import org.example.Menegers.CollectionManager;
import model.MusicBands.*;
import utility.XmlHandler;
import utility.MusicBandBuilder;

import java.util.Scanner;

public class Remove_greater extends model.commands.Command {

    CollectionManager collectionManager;

    public Remove_greater(CollectionManager collectionManager) {
        super("rmg", "remove_greater: удалить из коллекции все элементы, превышающие заданный");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String... args){
        try {
            MusicBand element;
            if (args.length ==0){
                element = MusicBandBuilder.buildMusicBandByNoArgs();
            }else {
                /*element = XmlHandler.deserialize();
                if (element == null){
                    throw new NullPointerException("Ошибка парсинга");
                }*/
            }
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите критерий для rmg(скопируйте параметр, написанный большими буквами): ");
            String param = scanner.nextLine();
            //collectionManager.getCollections().retainAll(collectionManager.getMusicBandsByParam(element,param));
        }catch (ArrayIndexOutOfBoundsException | NullPointerException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
