package org.example.Commands;

public class Exit extends AbstractCommand{

    public Exit() {
        super("exit","завершить программу");
    }

    @Override
    public void execute(String... args) {
        try {
            if(args.length != 0){
                throw new ArrayIndexOutOfBoundsException("Команда не поддерживает аргументы");
            }
            System.out.print("exit");
            System.exit(0);
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }

    }
}
