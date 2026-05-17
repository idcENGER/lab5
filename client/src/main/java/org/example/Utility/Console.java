package org.example.Utility;


import model.commands.Commands;
import org.example.Network.UDPClient;

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
        System.out.println("Welcome to Lab6APP. Enter help to get command list.");
        while (true){
            try {
                Thread.sleep(0);
                System.out.print("=>");
                String command = utility.XmlHandler.SpaceRemover(scanner.nextLine());
                if (command.isBlank()){continue;}
                command = command.replace("\t", " ");
                args = command.split(" ",2);
                String commandName = args[0];
                utility.XmlHandler.AllSpaceRemover(commandName);
                try {
                    Commands.valueOf(commandName);
                    System.out.print(client.request(command));
                } catch (IllegalArgumentException e) {
                    System.out.println("Неверное имя команды");
                }
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}


