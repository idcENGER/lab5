package org.example.Menegers;

import model.MusicBands.Coordinates;
import model.MusicBands.MusicBand;
import model.MusicBands.MusicGenre;
import model.MusicBands.Person;
import org.example.Exceptions.WrongArgumentException;

import org.example.Utility.ScannerParser;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashSet;


public class CollectionManager {

    private CommandInvoker commandInvoker;

    private final java.time.ZonedDateTime date = ZonedDateTime.now();
    private final HashSet<MusicBand> collections = new HashSet<>();

    public CollectionManager(CommandInvoker commandInvoker){
        this.commandInvoker = commandInvoker;
    }


    public void add(MusicBand musicBand){
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
    }

    public void recoverCollection(String path) throws IOException,NullPointerException {
        HashSet<MusicBand> data = ScannerParser.DeserializeCollectionXML(path);
        boolean IdIsUnique = data.stream().map(MusicBand::getId).allMatch(new HashSet<Integer>()::add);
        boolean PassportIdIsUnique = data.stream().map(MusicBand::getFrontMan).map(Person::getPassportID).allMatch(new HashSet<String>()::add);
        if (IdIsUnique && PassportIdIsUnique) {
            collections.addAll(data);
        } else if (!PassportIdIsUnique & !IdIsUnique) {
            System.out.println("Паспортные данные в коллекции не уникальны");
            System.out.println("ID в коллекции не уникальны");
            System.exit(0);
        } else if (!PassportIdIsUnique){
            System.out.println("Паспортные данные в коллекции не уникальны");
            System.exit(0);
        }else {
            System.out.println("ID в коллекции не уникальны");
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
            System.out.println("Collection is empty");
        }
        System.out.println("Музыкальной группы с таким id нет");
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
            System.out.println("Collection is empty");
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
            System.out.println("Collection is empty");
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
            System.out.println("Collection is empty");
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
            System.out.println("Collection is empty");
            return null;
        }
    }

    /*
    * @params MusicGenre
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
        }catch (NullPointerException ex){
            System.out.println("Collection is empty");
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
        }catch (NullPointerException exception){
            System.out.println("Музыкальной группы с таким именем нет");
        }
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
            System.out.println(ex.getMessage());
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