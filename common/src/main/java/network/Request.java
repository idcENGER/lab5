package network;

import model.commands.AbstractCommand;

public class Request {

    AbstractCommand request;

    public Request(AbstractCommand request){
        this.request = request;
    }

    @Override
    public String toString(){
        return this.request.toString();
    }

}
