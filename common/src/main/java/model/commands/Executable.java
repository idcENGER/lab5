package model.commands;

import network.Response;

import java.io.IOException;
import java.io.Serializable;

public interface Executable extends Serializable {

    Response execute(String...args) throws IOException, ClassNotFoundException;

}
