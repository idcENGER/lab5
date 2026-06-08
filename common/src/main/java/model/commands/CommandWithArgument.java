package model.commands;

import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import model.MusicBands.MusicBand;
import utility.MusicBandBuilder;
import utility.XmlHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface CommandWithArgument {

    static boolean valid(String commandName, String[] arguments)  {
        switch (commandName){
            case "add" ->{
                if (arguments == null){
                    return true;
                }
                if (arguments.length == 1){
                    try {
                        XmlHandler.deserialize(arguments[0]);
                        return true;
                    }catch (CannotResolveClassException e){
                        System.out.println("Ошибка парсинга: "+e.getMessage());
                        return false;
                    }
                }
                if (arguments.length == 9){
                    try {
                        ArrayList<String> params = new ArrayList<>(List.of(arguments));
                        MusicBandBuilder.buildMusicBandByParams(params);
                        return true;
                    }catch (Exception e){
                        return false;
                    }
                }
                System.out.println("неверное количество аргументов");
                return false;
            }
            case "aifm" ->{
                if (arguments == null){
                return false;
            }
                if (arguments.length == 1){
                    try {
                        XmlHandler.deserialize(arguments[0]);
                        return true;
                    }catch (CannotResolveClassException e){
                        System.out.println("неверные данные");
                        return false;
                    }
                }
                if (arguments.length == 9){
                    try {
                        ArrayList<String> params = new ArrayList<>(List.of(arguments));
                        MusicBandBuilder.buildMusicBandByParams(params);
                        return true;
                    }catch (Exception e){
                        return false;
                    }
                }
                System.out.println("неверное количество аргументов");
                return false;
            }
            case "ubi", "fsn", "fcn" ->{
                if (arguments.length == 1){
                    return true;
                }
            }
            case "rml" ->{}
            case "rmg" ->{}
            case "rmi" ->{}
            case "execute" ->{}
        }
        return false;
    }

    static boolean enumInclude(String name) {
        return Arrays.stream(CommandsWithArguments.class.getEnumConstants())
                .anyMatch(e -> e.name().equals(name));
    }

    static boolean isInteract(String commandName){
        return commandName.equals("add") || commandName.equals("aifm") || commandName.equals("rml")
                || commandName.equals("rmg");
    }

}
