package org.example;

import org.example.Utility.Console;

import java.io.IOException;

public class Client {

    public static void main(String[] args) {
        try {Console.run();}catch (IOException ex){
            System.out.print("connection failed");
        }
    }
}

