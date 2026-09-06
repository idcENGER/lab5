package network;

import model.commands.Command;

import java.io.Serial;
import java.io.Serializable;

public class Request implements Serializable {

    private final Command command;

    @Serial
    private static final long serialVersionUID = 962517436447139297L;

    public Request(Command command) {
        this.command = command;
    }

    public String[] getArguments() {
        return command.getArguments();
    }

    public Command command() {
        return command;
    }

    @Override
    public String toString() {
        return this.command.toString();
    }

}
