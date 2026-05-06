package org.example.MusicBands;

public class Person implements Comparable<Person>{
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Float height; //Поле может быть null, Значение поля должно быть больше 0
    private String passportID; //Строка не может быть пустой, Значение этого поля должно быть уникальным, Длина строки не должна быть больше 22, Поле может быть null
    private Color hairColor; //Поле может быть null
    private Location location; //Поле может быть null

    public Person(String name,Float height,String passportID,Color hairColor,Location location){
        this.name = name;
        this.hairColor = hairColor;
        this.height = height;
        this.passportID = passportID;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public Color getHairColor() {
        return hairColor;
    }

    public Float getHeight() {
        return height;
    }

    public String getPassportID() {
        return passportID;
    }

    public Location getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    @Override
    public boolean equals(Object o){
        if (this == o)return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.getName()) && height.equals(person.getHeight()) && passportID.equals(person.getPassportID()) && hairColor.equals(person.getHairColor()) && location.equals(person.getLocation());
    }

    @Override
    public String toString(){
        return getClass().getSimpleName()+"{ NAME: " + name + ", HEIGHT: " + height + ", PASSPORT ID: " + passportID +
                ", HAIR COLOR: " + hairColor + ", " + location.toString() + "}";
    }

    @Override
    public int compareTo(Person person) {
        return name.compareTo(person.name);
    }
}
