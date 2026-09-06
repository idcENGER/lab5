package network;

import java.io.Serial;
import java.io.Serializable;

public record Response(String content) implements Serializable {

    @Serial
    private static final long serialVersionUID = -3733091571138451960L;

    @Override
    public String toString(){
        return this.content;
    }
}
