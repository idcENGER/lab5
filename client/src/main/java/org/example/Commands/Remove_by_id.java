package org.example.Commands;

import org.example.Menegers.CollectionManager;
import org.example.Utility.XmlHandler;

public class Remove_by_id extends AbstractCommand{
    CollectionManager collectionManager;

    public Remove_by_id(CollectionManager collectionManager) {
        super("rmi", "remove_by_id :удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute(String... args) {
        try {
            boolean Finalresult = false;
            StringBuilder message = new StringBuilder("Удалены музыкальные группы с id: ");
            for (var i : args) {
                if (i.isBlank()){continue;}
                int id = Integer.parseInt(XmlHandler.SpaceRemover(i));
                boolean result = collectionManager.getCollections().removeIf(musicBand -> musicBand.getId() == id);
                if (result){
                    message.append(id).append(" ");
                    Finalresult = true;
                }
            }
            if (Finalresult) {
                System.out.println(message);
            } else {
                System.out.println("Такой музыкальной группы нет");
            }
        }catch (IllegalArgumentException | IndexOutOfBoundsException ex){
            System.out.println("Неверный аргумент: " + ex.getMessage());
        }
    }
}
