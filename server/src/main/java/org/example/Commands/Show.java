package org.example.Commands;

import network.Response;
import org.example.Menegers.CollectionManager;


public class Show extends model.commands.AbstractCommand {

    CollectionManager collectionManager;

    public Show(CollectionManager collectionManager) {
        super("show", "показать информацию о коллекции");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String... args) {
        try {
            if(args.length != 0){
                throw new ArrayIndexOutOfBoundsException("Команда не поддерживает аргументы");
            }
            if (collectionManager.getSize() == 0){System.out.println("Коллекция пуста");}
            for (var i : collectionManager.getCollections()){
                System.out.println(i);
            }
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
