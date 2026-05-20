package model.commands;

import network.Response;

import java.io.IOException;
import java.io.Serializable;

/*
* Interface for ANY command of console application
* */
public interface Command extends Serializable {

    Response execute(String ...args) throws IOException, ClassNotFoundException;

}

