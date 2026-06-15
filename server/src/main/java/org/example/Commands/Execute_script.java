package org.example.Commands;

import model.commands.Command;
import network.Response;
import org.example.Menegers.CommandInvoker;
import utility.XmlHandler;

import java.io.IOException;

public class Execute_script extends model.commands.Command {

    CommandInvoker commandInvoker;
    public Execute_script(CommandInvoker commandInvoker) {
        super("execute", "считать и исполнить скрипт из указанного файла. " +
                "В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        this.commandInvoker = commandInvoker;
    }

    @Override
    public Response execute(String... args) throws IOException, ClassNotFoundException {
        StringBuilder answer;
        answer = new StringBuilder();
        for (String i: args){
            Response resp;
            Command command = (Command)XmlHandler.deserialize(i);
            if (command.getArguments() == null){
                resp = commandInvoker.execute(command.getName(),null);
            }else{
                resp = commandInvoker.execute(command.getName(),command.getArguments());
            }
            if (resp != null)answer.append(command.getName()).append(":\n").append(resp).append('\n');
        }
        answer.setLength(answer.length() - 1);
        return new Response(answer.toString());
    }
}

