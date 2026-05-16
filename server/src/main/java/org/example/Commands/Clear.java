package org.example.Commands;

import network.Response;
import org.example.Menegers.CollectionManager;

public class Clear extends model.commands.AbstractCommand {

    CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String... args) {
        try {
            if(args.length != 0){
                throw new ArrayIndexOutOfBoundsException("Команда не поддерживает аргументы");
            }
            collectionManager.clear();
            System.out.println("Коллекция очищена");
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
