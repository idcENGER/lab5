package org.example.Commands;

import model.commands.Command;
import network.Response;
import org.example.Menegers.CommandInvoker;


public class Help extends Command {

    CommandInvoker commandInvoker;

    public Help(CommandInvoker commandInvoker) {
        super("help", "вывести справку по доступным командам");
        this.commandInvoker = commandInvoker;
    }

    @Override
    public Response execute(String... args) {
        StringBuilder s = new StringBuilder();
        for (var value : this.commandInvoker.getCommandMap().values()) {
            s.append(value.getDescription()).append("\n");
        }
        s.setLength(s.length() - 1);
        return new Response(s.toString());
    }
}
