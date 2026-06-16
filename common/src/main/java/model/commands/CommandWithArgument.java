package model.commands;

import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import model.MusicBands.MusicBand;
import utility.MusicBandBuilder;
import utility.ScriptTracker;
import utility.XmlHandler;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public interface CommandWithArgument {

    static Optional<String[]> valid(String commandName, String[] arguments) {
        switch (commandName){
            case "add" ->{
                if (arguments == null){
                    MusicBand musicBand = MusicBandBuilder.buildMusicBandByNoArgs();
                    arguments = new String[]{XmlHandler.serialize(musicBand)};
                    return Optional.of(arguments);
                }
                if (arguments.length == 1){
                    try {
                        MusicBand musicBand = (MusicBand) XmlHandler.deserialize(arguments[0]);
                        arguments = new String[]{XmlHandler.serialize(musicBand)};
                        return Optional.of(arguments);
                    }catch (CannotResolveClassException e){
                        System.out.println("Ошибка парсинга: "+e.getMessage());
                        return Optional.empty();
                    }
                }
                if (arguments.length == 9){
                    try {
                        ArrayList<String> params = new ArrayList<>(List.of(arguments));
                        MusicBand musicBand = MusicBandBuilder.buildMusicBandByParams(params);
                        arguments = new String[]{XmlHandler.serialize(musicBand)};
                        return Optional.of(arguments);
                    }catch (Exception e){
                        return Optional.empty();
                    }
                }
                System.out.println("неверное количество аргументов");
                return Optional.empty();
            }
            case "aifm","rmg","rml" ->{
                MusicBand musicBand = null;
                if (arguments == null){
                    musicBand = MusicBandBuilder.buildMusicBandByNoArgs();
                }else{
                    if (arguments.length == 1){
                        try {
                            musicBand = (MusicBand) XmlHandler.deserialize(arguments[0]);
                        }catch (CannotResolveClassException e){
                            System.out.println("Ошибка парсинга: "+e.getMessage());
                        }
                    }
                    if (arguments.length == 9){
                        try {
                            ArrayList<String> params = new ArrayList<>(List.of(arguments));
                            musicBand = MusicBandBuilder.buildMusicBandByParams(params);
                        }catch (Exception e){
                            System.out.println("Неверные аргументы");
                        }
                    }
                }
                boolean validParam = false;
                String param = "";
                while (!validParam){
                    String[] PARAMS = {"NAME","ID","COORDINATES", "NUMBER OF PARTICIPANTS","GENRE","FRONT MAN"};
                    System.out.println(Arrays.toString(PARAMS));
                    System.out.print("введите критерий: ");
                    Scanner scanner = new Scanner(System.in);
                    param = scanner.nextLine();
                    for (String i: PARAMS){
                        if (param.equals(i)){
                            validParam = true;
                            break;
                        }
                    }
                    if(!validParam){System.out.println("У музыкальной группы нет такого параметра");}
                }
                if(musicBand != null) {
                    arguments = new String[]{XmlHandler.serialize(musicBand), param};
                    return Optional.of(arguments);
                }
                return Optional.empty();
            }
            case  "fsn", "fcn" ->{
                if (arguments.length == 1){
                    return Optional.of(arguments);
                }
                return Optional.empty();
            }

            case "ubi" ->{
                if (arguments.length == 1){
                    MusicBand musicBand = MusicBandBuilder.buildMusicBandByNoArgs();
                    arguments = new String[]{arguments[0],XmlHandler.serialize(musicBand)};
                    return Optional.of(arguments);
                }
                return Optional.empty();
            }

            case "rmi" ->{
                if (arguments.length == 0){
                    return Optional.empty();
                }
                for (String i : arguments) {
                    try {
                        Integer.parseInt(i);
                    } catch (NumberFormatException e) {
                        System.out.println("ID целые числа");
                        break;
                    }
                }
                return Optional.of(arguments);
            }
            case "execute" ->{
                List<String> list = new ArrayList<>(Arrays.asList(arguments));
                list.remove(0);
                try {
                    Path path = Path.of(arguments[0]);
                    if (ScriptTracker.isExecuting(path)){
                        System.out.println("Рекурсия заблокирована");
                        return Optional.empty();
                    }
                    ScriptTracker.markExecuting(path);
                    String content = Files.readString(path);
                    String[] commands = content.split("\n");
                    String[] args;
                    for (String i : commands){
                        String cmd = i.replace("\t", " ");
                        args = cmd.split(" ",2);
                        String name = args[0];
                        if (name.equals("execute")){
                            CommandWithArgument.valid(name,args);
                            continue;
                        }
                        if (args.length >1) {
                            try {
                                if (args[1].contains(";")){
                                    args = args[1].split(";");
                                }
                                else {
                                    args = new String[]{args[1]};
                                }
                            }catch (StreamException e){
                                System.out.println("Неверный формат ввода аргументов: введите через \";\"");
                            }
                        }else {
                            args = null;
                        }
                        Command command = null;
                        if(ArglessCommand.enumInclude(name)){
                            if(ArglessCommand.valid(args)){
                                command = new Command(name,(String) null);
                            } else{
                                System.out.println("команда не поддерживает аргументы");
                            }
                        }else {
                            Optional<String[]> check = CommandWithArgument.valid(name,args);
                            if(check.isPresent()){
                                args = check.get();
                                command = new Command(name,args);
                            }
                        }
                        list.add(XmlHandler.serialize(command));
                    }
                    ScriptTracker.markFinished(path);
                    arguments = list.toArray(list.toArray(new String[0]));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                return Optional.of(arguments);
            }
        }
        return Optional.empty();
    }
}
