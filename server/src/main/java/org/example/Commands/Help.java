package org.example.Commands;

import model.commands.AbstractCommand;
import network.Response;
import org.example.Menegers.CommandInvoker;


public class Help extends AbstractCommand {

    CommandInvoker commandInvoker;

    public Help(CommandInvoker commandInvoker) {
        super("help", "вывести справку по доступным командам");
        this.commandInvoker = commandInvoker;
    }

    @Override
    public Response execute(String... args) {
        try {
            if(args.length != 0){
                throw new ArrayIndexOutOfBoundsException("Команда не поддерживает аргументы");
            }
            StringBuilder s = new StringBuilder();
            for (var value : this.commandInvoker.getCommandMap().values()) {
                s.append(value.getDescription()+"\n");

            }
            return new Response(s.toString());
        }catch (ArrayIndexOutOfBoundsException exception){
            System.out.println(exception.getMessage());
        }
        return null;
    }
}
