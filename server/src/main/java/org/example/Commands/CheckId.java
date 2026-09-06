package org.example.Commands;

import model.commands.Command;
import network.Response;
import org.example.Menegers.CollectionManager;

public class CheckId extends Command {

    private final CollectionManager collectionManager;

    public CheckId(CollectionManager collectionManager) {
        super("cid","");
        this.collectionManager = collectionManager;
    }

    public Response execute(String... args){
        try {
            return collectionManager.getMusicBandByID(Integer.parseInt(args[0])) == null ? new Response("false") : new Response("true");
        }catch (NumberFormatException ex){
            return new Response("invalid value of id");
        }
    }
}
