package utility;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import model.MusicBands.MusicBand;



import com.thoughtworks.xstream.XStream;
import model.commands.AbstractCommand;
import network.Request;
import network.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class XmlHandler {

    public static String serialize(Object object){
        XStream xStream = new XStream();
        xStream.alias("command", AbstractCommand.class);
        return xStream.toXML(object);
    }

    public static Object deserialize(String string){
        XStream xStream = new XStream();
        xStream.allowTypes(new Class[] {MusicBand.class, AbstractCommand.class, Request.class, Response.class});
        xStream.alias("command",AbstractCommand.class);
        return xStream.fromXML(string);
    }

    public static String SerializeXMLXStream(Object object) throws ClassNotFoundException {
        Class<?> musicBand = Class.forName("model.MusicBands.MusicBand");
        XStream xStream = new XStream();
        xStream.alias("Collection", Set.class);
        xStream.alias("MusicBand", musicBand);
        return xStream.toXML(object);
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
