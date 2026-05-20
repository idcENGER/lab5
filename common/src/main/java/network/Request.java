package network;

public class Request {

    private final String command;

    public Request(String command, String[] arguments){
        this.command = command;
    }

    public Request(String command){
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public String toString(){
        return this.command;
    }

}
