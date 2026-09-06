package org.example.Menegers;

import com.thoughtworks.xstream.io.StreamException;
import model.MusicBands.Coordinates;
import model.MusicBands.MusicBand;
import model.MusicBands.MusicGenre;
import model.MusicBands.Person;
import org.example.Exceptions.WrongArgumentException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.XmlHandler;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Scanner;


public class CollectionManager implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(CollectionManager.class);
    @Serial
    private static final long serialVersionUID = 3468168139201341661L;
    private final CommandInvoker commandInvoker;

    private final java.time.ZonedDateTime date = ZonedDateTime.now();
    private final HashSet<MusicBand> collections = new HashSet<>();

    public CollectionManager(CommandInvoker commandInvoker){
        this.commandInvoker = commandInvoker;
    }

    public void add(MusicBand musicBand){
        logger.info("добавлена музыкальная группа:{}",musicBand);
        collections.add(musicBand);
    }

    public void clear(){
        collections.clear();
    }

    public HashSet<MusicBand> getCollections(){
        return collections;
    }

    public int getSize(){
        return collections.size();
    }

    public void save() throws IOException, ClassNotFoundException {
        commandInvoker.execute("save",null);
        logger.info("коллекция сохранена");
    }

    public void recoverCollection(String path) throws IOException,NullPointerException {
        File file = new File(path);
        if (!file.exists() || !file.canRead()) {
            logger.error("collection not found");
            throw new FileNotFoundException("collection not found");
        }

        StringBuilder contentBuilder = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                contentBuilder.append(scanner.nextLine().strip());
            }
        }
        String content = contentBuilder.toString();
        try {
            HashSet<MusicBand> data = (HashSet<MusicBand>) XmlHandler.deserialize(content);
            boolean IdIsUnique = data.stream().map((musicBand -> musicBand.getId())).allMatch(new HashSet<Integer>()::add);
            boolean PassportIdIsUnique = data.stream().map(musicBand -> musicBand.getFrontMan()).map(person -> person.getPassportID()).allMatch(new HashSet<String>()::add);
            if (IdIsUnique && PassportIdIsUnique) {
                collections.addAll(data);
            } else if (!PassportIdIsUnique & !IdIsUnique) {
                logger.error("Паспортные данные в коллекции не уникальны");
                logger.error("ID в коллекции не уникальны");
                System.exit(0);
            } else if (!PassportIdIsUnique){
                logger.error("Паспортные данные в коллекции не уникальны");
                System.exit(0);
            }else {
                logger.error("ID в коллекции не уникальны");
                System.exit(0);
            }
        }catch (StreamException e){
            logger.error("Invalid file");
            System.exit(0);
        }
    }

    public boolean inCollection(MusicBand musicBand){
        for (MusicBand band: collections){
            if (musicBand.equals(band)){
                return true;
            }
        }
        return false;
    }

    public MusicBand getMusicBandByID(int id){
        try {
            for (MusicBand musicBand : collections) {
               if (musicBand.getId() == id){
                   return musicBand;
               }
            }
        }catch (NullPointerException ex){
            logger.warn("Collection is empty");
        }
        return null;
    }

    public HashSet<MusicBand> getGreaterMusicBandByName(String name){
        HashSet<MusicBand> set = new HashSet<>();
        try {
            for (MusicBand musicBand : collections) {
                if (name.compareTo(musicBand.getName()) > 0){
                    set.add(musicBand);
                }
            }
            return set;
        }catch (NullPointerException ex){
            logger.warn("Collection is empty");
            return null;
        }
    }

    public HashSet<MusicBand> getGreaterMusicBandByCoordinates(Coordinates coordinates){
        HashSet<MusicBand> set = new HashSet<>();
        try {
            for (MusicBand musicBand : collections) {
                if (coordinates.compareTo(musicBand.getCoordinates()) > 0){
                    set.add(musicBand);
                }
            }
            return set;
        }catch (NullPointerException ex){
            logger.info("Collection is empty");
            return null;
        }
    }

    public HashSet<MusicBand> getGreaterMusicBandByFrontMan(Person person){
        HashSet<MusicBand> set = new HashSet<>();
        try {
            for (MusicBand musicBand : collections) {
                int compResult = person.compareTo(musicBand.getFrontMan());
                if (compResult > 0){
                    set.add(musicBand);
                }
            }
            return set;
        }catch (NullPointerException ex){
            logger.warn("Collection is empty");
            return null;
        }
    }

    public HashSet<MusicBand> getMusicBandByNumberOfParticipants(long num){
        HashSet<MusicBand> set = new HashSet<>();
        try {
            for (MusicBand musicBand : collections) {
                if (num - musicBand.getNumberOfParticipants() > 0){
                    set.add(musicBand);
                }
            }
            return set;
        }catch (NullPointerException ex){
            logger.warn("Collection is empty");
            return null;
        }
    }

    /**
    * @params {@link  MusicGenre}
    * this method compare Enum elements by there order
    * if order our genre greater than genre current MusicBand then current MusicBand append to set
    * if method found such genres then @return HashSet<MusicBand>
    * if collection is empty then @return null
    */
    public HashSet<MusicBand> getGreaterMusicBandByGenre(MusicGenre genre){
        HashSet<MusicBand> set = new HashSet<>();
        try {
            for (MusicBand musicBand : collections) {
                if (genre.compareTo(musicBand.getGenre()) > 0){
                    set.add(musicBand);
                }
            }
            return set;
        }catch (NullPointerException ex) {
            logger.warn("Collection is empty");
            return null;
        }
    }

    public HashSet<MusicBand> getMusicBandsByParam(MusicBand element,String param) throws WrongArgumentException {
        try {
            switch (param){
                case "NAME" -> {
                    return getGreaterMusicBandByName(element.getName());
                }
                case  "ID" -> {
                    HashSet<MusicBand> set = new HashSet<>();
                    set.add(getMusicBandByID(Integer.parseInt("1")));
                    return set;
                }
                case "COORDINATES" -> {
                    Coordinates value = element.getCoordinates();
                    return getGreaterMusicBandByCoordinates(value);
                }
                case "NUMBER OF PARTICIPANTS" -> {
                    return getMusicBandByNumberOfParticipants(element.getNumberOfParticipants());
                }
                case "GENRE" -> {
                    return getGreaterMusicBandByGenre(element.getGenre());
                }
                case "FRONT MAN" ->{
                    return getGreaterMusicBandByFrontMan(element.getFrontMan());
                }
            }
        }catch (NullPointerException ignored){}
        return null;
    }

    public HashSet<MusicBand> filterMusicBandByName(String name,boolean start_with){
        HashSet<MusicBand> set = new HashSet<>();
        try {
            if (!start_with){
                for (MusicBand musicBand : collections) {
                    if (musicBand.getName().contains(name)) {
                        set.add(musicBand);
                    }
                }
            }else {
                for (MusicBand musicBand : collections) {
                    if (musicBand.getName().startsWith(name)) {
                        set.add(musicBand);
                    }
                }
            }
        }catch (NullPointerException ex){
            return null;
        }
        return set;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{size:" + collections.size() + ", дата инициализации: " +
                date + "}";
    }
}