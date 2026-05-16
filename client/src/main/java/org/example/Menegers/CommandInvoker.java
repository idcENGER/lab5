package org.example.Menegers;

import model.commands.AbstractCommand;
import org.example.Utility.Console;
import utility.XmlHandler;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.Map;

public class CommandInvoker {

    private static final Map<String, AbstractCommand> commandMap = new HashMap<>();

    public void register(AbstractCommand command){
        commandMap.put(command.getName(), command);
    }

    public void valid(String commandName) throws NullPointerException, NoSuchFileException {
        try {
            if (Console.args.length > 1) {
                commandMap.get(commandName).execute(XmlHandler.SpaceRemover(Console.args[1]));
            }else {
                commandMap.get(commandName).execute();
            }
        }catch (NullPointerException ex){
            System.out.println("Unsupported command");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
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
