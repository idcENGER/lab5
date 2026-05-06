package org.example.MusicBands;

public class Coordinates implements Comparable<Coordinates> {
    private int x;
    private Double y; //Значение поля должно быть больше -147, Поле не может быть null

    public Coordinates(int x, Double y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o){
        if (this == o)return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates c = (Coordinates) o;
        return x == c.getX() && y.equals(c.getY());
    }

    @Override
    public String toString(){
        return getClass().getSimpleName() + "{" + x + ", " + y + "}";
    }


    @Override
    public int compareTo(Coordinates coordinates) {
        return (int) (((x*x)+(y*y)) - ((coordinates.x*coordinates.x)
                +(coordinates.y*coordinates.y)));
    }
}
