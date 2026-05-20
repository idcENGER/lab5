package org.example.Utility;


import model.commands.ArglessCommands;
import model.commands.Commands;
import model.commands.CommandsWithArguments;
import network.Request;
import network.Response;
import org.example.Commands.ArglessCommand;
import org.example.Commands.Exit;
import org.example.Network.UDPClient;
import utility.Check;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Scanner;

public class Console {

    public static String[] args;

    public static void run() throws IOException {
        UDPClient client = new UDPClient(InetAddress.getLocalHost(),24868);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nВыход");
        }));
        Scanner scanner = new Scanner(System.in);
        Exit exit = new Exit();
        System.out.println("Welcome to Lab6APP. Enter help to get command list.");
        while (true){
            try {
                Thread.sleep(0);
                System.out.print("=>");
                String command = utility.XmlHandler.SpaceRemover(scanner.nextLine());
                if (command.equals(exit.getName())){
                    exit.execute();
                }
                if (command.isBlank()){continue;}
                command = command.replace("\t", " ");
                args = command.split(" ",2);
                String commandName = args[0];
                utility.XmlHandler.AllSpaceRemover(commandName);
                try {
                    Commands.valueOf(commandName);
                    if(Check.enumInclude(ArglessCommands.class,commandName)){
                        if(ArglessCommand.valid(commandName)){
                            Request request = new Request(commandName);
                            System.out.println(client.sendRequest(request));
                        }
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Неверное имя команды");
                }
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}


