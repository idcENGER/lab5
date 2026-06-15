package org.example.Commands;

import network.Response;
import org.example.Menegers.CollectionManager;
import utility.XmlHandler;

import java.io.IOException;

public class Remove_by_id extends model.commands.Command {
    CollectionManager collectionManager;

    public Remove_by_id(CollectionManager collectionManager) {
        super("rmi", "remove_by_id :удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String... args) {
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
                collectionManager.save();
                return new Response(message.toString());
            } else {
                return new Response("Такой музыкальной группы нет");
            }
        }catch (IllegalArgumentException | IndexOutOfBoundsException ex){
            return new Response("Неверный аргумент: " + ex.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
