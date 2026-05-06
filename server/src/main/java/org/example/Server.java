package org.example;
import org.example.Utility.Console;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException{
        if (args.length == 0) {
            System.out.println("Ошибка: Имя файла должно передаваться через аргумент командной строки.");
            System.exit(0);
        }
        Console.run(args[0]);
    }
}

