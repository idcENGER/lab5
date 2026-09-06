package org.example.Commands;

import network.Response;
import org.example.Menegers.CollectionManager;
import utility.XmlHandler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;

public class Save extends model.commands.Command {

    Path path;
    CollectionManager collectionManager;

    public Save(CollectionManager collectionManager, Path path){
        super("save", "сохранить коллекцию в файл");
        this.path = path;
        this.collectionManager = collectionManager;
    }

    @Override
    public Response execute(String... args) {
        String data = XmlHandler.serialize(collectionManager.getCollections());
        try{
            if(args.length != 0){
                throw new ArrayIndexOutOfBoundsException("Команда не поддерживает аргументы");
            }
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(path.toString()));
            writer.write(data);
            writer.flush();
            System.out.println("Коллеция сохранена");
        } catch (IOException | ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
