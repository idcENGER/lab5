package org.example.Commands;

import java.io.IOException;

/*
* Interface for ANY command of App application
* */
public interface Command {

    void execute(String ...args) throws IOException, ClassNotFoundException;

}

