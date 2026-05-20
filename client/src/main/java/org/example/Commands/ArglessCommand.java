package org.example.Commands;

import org.example.Utility.Console;

public class ArglessCommand {

    public static boolean valid(String commandName){
        if (Console.args.length > 1){
            System.out.println("команда не принимает аргументов");
            return false;
        }
        return true;
    }

}
