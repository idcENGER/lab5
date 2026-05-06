package org.example.MusicBands;


import java.time.LocalDate;
import java.time.ZonedDateTime;

public class MusicBand {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long numberOfParticipants; //Поле не может быть null, Значение поля должно быть больше 0
    private java.time.LocalDate establishmentDate; //Поле не может быть null
    private MusicGenre genre; //Поле может быть null
    private Person frontMan; //Поле не может быть null

    public MusicBand(Integer id, String name,Coordinates coordinates,java.time.ZonedDateTime creationDate,
                     Long numberOfParticipants,java.time.LocalDate establishmentDate,MusicGenre genre,Person frontMan){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.numberOfParticipants = numberOfParticipants;
        this.establishmentDate = establishmentDate;
        this.genre = genre;
        this.frontMan = frontMan;
    }

    public int getId(){
        return this.id;
    }

    public Person getFrontMan() {
        return frontMan;
    }

    public String getName() {
        return name;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getEstablishmentDate() {
        return establishmentDate;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public Long getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFrontMan(Person frontMan) {
        this.frontMan = frontMan;
    }

    public void setEstablishmentDate(LocalDate establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setNumberOfParticipants(Long numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object o){
        if (this == o)return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicBand band = (MusicBand) o;
        return name.equals(band.getName()) && numberOfParticipants.equals(band.getNumberOfParticipants()) && genre.equals(band.getGenre())
                && frontMan.equals(band.getFrontMan());
    }

    @Override
    public String toString(){
        return getClass().getSimpleName()+"{\nID: " + id + ", NAME: " + name + ", COORDINATES: " + coordinates.toString() +
                ", CREATION DATE: " + creationDate + ",\nNUMBER OF PARTICIPANTS: " +
                 numberOfParticipants + ", ESTABLISHMENT DATE: " + establishmentDate + ", MUSIC GENRE: " + genre +
                ",\nFRONT MAN: " + frontMan.toString() + "\n}";
    }
}
