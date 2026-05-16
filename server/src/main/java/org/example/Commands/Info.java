package org.example.Commands;

import network.Response;
import org.example.Menegers.CollectionManager;

public class Info extends model.commands.AbstractCommand {

    CollectionManager collectionManager;

    public Info(CollectionManager collectionManager) {
        super("info", "вывести в стандартный поток вывода информацию о коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String... args) {
        try {
            if(args.length != 0){
                throw new ArrayIndexOutOfBoundsException(getName() +":" + "Команда не поддерживает аргументы");
            }
            System.out.println(collectionManager.toString());
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
