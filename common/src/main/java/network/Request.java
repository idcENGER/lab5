package network;

import model.commands.Command;

public class Request {

    private final Command command;

    public Request(Command command){
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public String[] getArguments(){
        return command.getArguments();
    }


    @Override
    public String toString(){
        return this.command.toString();
    }

}
