package org.example.Utility;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import org.example.Menegers.CollectionManager;
import org.example.MusicBands.MusicBand;

import com.thoughtworks.xstream.XStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class XmlHandler {
    
    public static MusicBand DeserializeMusicBandXMLXStream(String data,CollectionManager collectionManager) {
        try {
            XStream xStream = new XStream();
            xStream.allowTypes(new Class[] {MusicBand.class});
            xStream.alias("MusicBand",MusicBand.class);
            MusicBand band = (MusicBand) xStream.fromXML(data);
            band.setId(collectionManager.getSize()+1);
            return band;
        }catch (CannotResolveClassException | ConversionException exception){
            return null;
        }
    }

    public static HashSet<MusicBand> DeserializeCollectionXMLXStream(String dataPath) throws IOException {
        XStream xStream = new XStream();
        xStream.allowTypes(new Class[] {CollectionManager.class,MusicBand.class});
        String content = Files.readString(Path.of(dataPath));
        xStream.alias("MusicBand",MusicBand.class);
        xStream.alias("Collection",HashSet.class);
        try {
            return (HashSet<MusicBand>) xStream.fromXML(content);
        }catch (StreamException exception) {
            if (!content.isBlank()){System.out.println("Cant cast file data to HashSet<MusicBand> or file is empty");}
            return null;
        }
    }

    public static String SerializeXMLXStream(Object object) throws ClassNotFoundException {
        Class<?> musicBand = Class.forName("org.example.MusicBands.MusicBand");
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
