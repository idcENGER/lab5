package org.example.Menegers;

import model.commands.Command;
import network.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandInvoker {

    private static final Map<String, Command> commandMap = new HashMap<>();

    public void register(Command command){
        commandMap.put(command.getName(), command);
    }

    public Response execute(String commandName,String[] args) throws IOException, ClassNotFoundException {
        if (args == null){return commandMap.get(commandName).execute();}
        return commandMap.get(commandName).execute(args);
    }


    public Map<String, Command> getCommandMap(){
        return commandMap;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
