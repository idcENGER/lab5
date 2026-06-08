package utility;

import model.MusicBands.MusicBand;



import com.thoughtworks.xstream.XStream;
import model.MusicBands.Person;
import model.commands.Command;
import network.Request;
import network.Response;

public class XmlHandler {

    public static String serialize(Object object){
        XStream xStream = new XStream();
        xStream.alias("Command", Command.class);
        xStream.alias("Frontman", Person.class);
        xStream.alias("MusicBand", MusicBand.class);
        xStream.alias("Request", Request.class);
        xStream.alias("Response", Response.class);
        return xStream.toXML(object);
    }

    public static Object deserialize(String string){
        XStream xStream = new XStream();
        xStream.allowTypes(new Class[] {MusicBand.class, Command.class, Request.class, Response.class});
        xStream.alias("Command", Command.class);
        xStream.alias("Frontman", Person.class);
        xStream.alias("MusicBand", MusicBand.class);
        xStream.alias("Request", Request.class);
        xStream.alias("Response", Response.class);
        return xStream.fromXML(string);
    }

    public static String SpaceRemover(String message){
        message = message.replace("\t"," ");
        return message.strip();
    }

    public static String AllSpaceRemover(String message){
        message = message.replace("\t"," ");
        while (message.contains(" ")){
            message = message.replaceFirst(" ","");
        }
        return message;
    }

}
