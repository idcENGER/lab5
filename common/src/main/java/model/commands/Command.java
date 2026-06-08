package model.commands;

import model.MusicBands.MusicBand;
import network.Response;

import java.io.IOException;
import java.util.Objects;

public class Command implements Executable {

    private final String name;
    private String description;
    private Object arguments;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Command(String name,String[] arguments){
        this.name = name;
        this.arguments = arguments;
    }

    public Command(String name, MusicBand musicBand){
        this.name = name;
        this.arguments = musicBand;
    }

    /**
     * @return Название и использование команды.
     */
    public String getName () {
        return name;
    }

    public String[] getArguments(){
        if (arguments == null){
            return null;
        }
        return (String[]) arguments;
    }

    /**
     * @return Описание команды.
     */
    public String getDescription () {
        return name + ": " + description;
    }

    @Override
    public Response execute(String...args) throws IOException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean equals (Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return Objects.equals(name, command.name) && Objects.equals(description, command.description);
    }

    @Override
    public int hashCode () {
        return Objects.hash(name, description);
    }

    @Override
    public String toString () {
        return "Command{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", arguments='" + arguments + '\'' +
                '}';
    }
}