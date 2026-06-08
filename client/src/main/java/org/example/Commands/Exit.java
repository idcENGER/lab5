package org.example.Commands;

import network.Response;

public class Exit extends model.commands.Command {

    public Exit() {
        super("exit","завершить программу");
    }

    @Override
    public Response execute(String... args) {
        try {
            if(args.length != 0){
                throw new ArrayIndexOutOfBoundsException("Команда не поддерживает аргументы");
            }
            System.out.print("exit");
            System.exit(0);
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
