package org.example.Menegers;

import org.example.Commands.AbstractCommand;
import org.example.Utility.Console;
import org.example.Utility.XmlHandler;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.Map;

public class CommandInvoker {

    private static final Map<String, AbstractCommand> commandMap = new HashMap<>();

    public void register(AbstractCommand command){
        commandMap.put(command.getName(), command);
    }

    public void execute(String commandName) throws NullPointerException, NoSuchFileException {
        try {
            if (Console.args.length > 1) {
                commandMap.get(commandName).execute(XmlHandler.SpaceRemover(Console.args[1]));
            }else {
                commandMap.get(commandName).execute();
            }
        }catch (NullPointerException ex){
            System.out.println("Unsupported command");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void executeScriptCommand(String commandName,String[] args) throws NullPointerException{
        try {
            if (args != null) {
                commandMap.get(commandName).execute(args);
            }else {
                commandMap.get(commandName).execute();
            }
        }catch (NullPointerException ex){
            System.out.println("Unsupported command: "+commandName);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public Map<String, AbstractCommand> getCommandMap(){
        return commandMap;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
