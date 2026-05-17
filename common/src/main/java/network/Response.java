package network;

public class Response {

    private final String content;

    public Response(String response) {
        this.content = response;
    }

    @Override
    public String toString(){
        return this.content;
    }
}
