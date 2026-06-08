package org.example.Utility;

import com.thoughtworks.xstream.io.StreamException;
import model.MusicBands.MusicBand;
import model.commands.Command;
import model.commands.Commands;
import network.Request;
import model.commands.ArglessCommand;
import model.commands.CommandWithArgument;
import network.Response;
import org.example.Commands.Exit;
import org.example.Network.UDPClient;
import utility.MusicBandBuilder;
import utility.XmlHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class Console {

    public static String[] args;

    public static void run() throws IOException {
        UDPClient client = new UDPClient(InetAddress.getLocalHost());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nВыход");
        }));
        Scanner scanner = new Scanner(System.in);
        Exit exit = new Exit();
        System.out.println("Welcome to Lab6APP. Enter help to get command list.");
        while (true){
            try {
                System.out.print("=>");
                String cmd = utility.XmlHandler.SpaceRemover(scanner.nextLine());
                if (cmd.equals(exit.getName())){
                    exit.execute();
                }
                if (cmd.isBlank()){continue;}
                cmd = cmd.replace("\t", " ");
                args = cmd.split(" ",2);
                String commandName = args[0];
                if (args.length >1) {
                    try {
                        if (args[1].contains(";")){
                            args = args[1].split(";");
                        }
                        else {
                            args = new String[]{args[1]};
                        }
                    }catch (StreamException e){
                        System.out.println("Неверный формат ввода аргументов: введите через \";\"");
                    }
                }else {
                    args = null;
                }
                try {
                    Request request = null;
                    Commands.valueOf(commandName);
                    if(ArglessCommand.enumInclude(commandName)){
                        if(ArglessCommand.valid(commandName,args)){
                            Command command = new Command(commandName,(String) null);
                            request = new Request(command);
                        } else{
                            System.out.println("команда не поддерживает аргументы");
                        }
                    }else {
                        if(CommandWithArgument.valid(commandName,args)){
                            if (CommandWithArgument.isInteract(commandName) && args == null){
                                MusicBand musicBand = MusicBandBuilder.buildMusicBandByNoArgs();
                                args = new String[]{XmlHandler.serialize(musicBand)};

                            }
                            Command command = new Command(commandName,args);
                            request = new Request(command);
                        }
                    }
                    if(request != null){
                        Response response = client.sendRequest(XmlHandler.serialize(request));
                        System.out.println(response);
                    }else {
                        System.out.println("Неверный запрос");
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


