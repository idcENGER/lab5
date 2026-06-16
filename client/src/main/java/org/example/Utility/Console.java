package org.example.Utility;

import com.thoughtworks.xstream.io.StreamException;
import model.commands.Command;
import model.commands.Commands;
import network.Request;
import model.commands.ArglessCommand;
import model.commands.CommandWithArgument;
import network.Response;
import org.example.Commands.Exit;
import org.example.Network.UDPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.XmlHandler;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;

public class Console {

    public static String[] args;
    private static final Logger logger = LoggerFactory.getLogger(Console.class);

    public static void run() throws IOException {
        Thread mainThread = Thread.currentThread();
        UDPClient client = new UDPClient();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\nВыход");
            mainThread.interrupt();
        }));
        Scanner scanner = new Scanner(System.in);
        Exit exit = new Exit();
        System.out.println("Welcome to Lab6APP. Enter help to get command list.");
        logger.info("клиент запущен");
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
                //execute /home/enger/Projects/lab6/client/src/main/resources/sc1
                logger.info("имя команды {}, аргументы:{}",commandName,args);
                try {
                    Request request = null;
                    Commands.valueOf(commandName);
                    if (commandName.equals("execute")){
                        CommandWithArgument.valid(commandName,args);
                    }
                    if(ArglessCommand.enumInclude(commandName)){
                        if(ArglessCommand.valid(args)){
                            Command command = new Command(commandName,(String) null);
                            request = new Request(command);
                        } else{
                            System.out.println("команда не поддерживает аргументы");
                        }
                    }else {
                        Optional<String[]> check = CommandWithArgument.valid(commandName,args);
                        if(check.isPresent()){
                            args = check.get();
                            Command command = new Command(commandName,args);
                            request = new Request(command);
                        }
                    }
                    if(request != null){
                        try {
                            logger.info("попытка отправки запроса:{}",request);
                            Response response = client.sendRequest(XmlHandler.serialize(request));
                            logger.info("запрос успешно отправлен. Ответ сервера:{}",response);
                            System.out.println(response);
                        } catch (IOException e) {
                            logger.warn("Ошибка связи с сервером:{}",e.getMessage());
                            System.err.println("Ошибка связи с сервером:"+e.getMessage());
                            System.out.println("=>");
                        }
                    }else {
                        System.out.println("Неверный запрос");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Неверное имя команды");
                }
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }catch (StreamException streamException){
                System.out.println(streamException.getMessage());
            }
        }
    }
}


