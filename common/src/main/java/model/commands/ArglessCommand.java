package model.commands;

import java.util.Arrays;

public interface ArglessCommand {

    static boolean valid(String[] arguments){
        return arguments == null;
    }

    static boolean enumInclude(String name) {
        return Arrays.stream(ArglessCommands.class.getEnumConstants())
                .anyMatch(e -> e.name().equals(name));
    }

}
