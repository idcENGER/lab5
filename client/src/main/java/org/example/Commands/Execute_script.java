package org.example.Commands;

import org.example.Menegers.CommandInvoker;
import org.example.Utility.XmlHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

public class Execute_script extends AbstractCommand{

    CommandInvoker commandInvoker;
    private final HashSet<Path> scripts = new HashSet<>();
    public Execute_script(CommandInvoker commandInvoker) {
        super("execute", "считать и исполнить скрипт из указанного файла. " +
                "В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        this.commandInvoker = commandInvoker;
    }

    @Override
    public void execute(String... args) throws IOException {
        try {
            if (args.length == 0){
                throw new ArrayIndexOutOfBoundsException("Аргумент не может быть равен нулю");
            }
            Path path = Path.of(args[0]);
            if (scripts.contains(path)){
                System.out.println("Рекурсия заблокирована");
                scripts.clear();
                return;
            }
            scripts.add(path);
            String content = Files.readString(path);
            String[] commands = content.split("\n");
            for (String command : commands){
                XmlHandler.SpaceRemover(command);
                String[] cmd = command.replace("\t"," ").split(" ",2);
                String commandName = XmlHandler.AllSpaceRemover(cmd[0]);
                if (cmd.length > 1){
                    String[] arguments = cmd[1].split(";");
                    commandInvoker.executeScriptCommand(commandName,arguments);
                }else {
                    commandInvoker.executeScriptCommand(commandName, null);
                }
            }
        scripts.clear();
        }catch (ArrayIndexOutOfBoundsException ex){
            System.out.println(ex.getMessage());
        }catch (IOException exception){
            System.out.println("Не получилось открыть файл: "+exception.getMessage());
        }
    }
}
