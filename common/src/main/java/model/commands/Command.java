package model.commands;

import network.Response;

import java.io.IOException;

/*
* Interface for ANY command of console application
* */
public interface Command {

    Response execute(String ...args) throws IOException, ClassNotFoundException;

}

