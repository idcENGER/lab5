package model.MusicBands;

import java.io.Serial;
import java.io.Serializable;

public class Coordinates implements Comparable<Coordinates>, Serializable {
    @Serial
    private static final long serialVersionUID = -771032205396106619L;

    private final int x;
    private final double y;

    public Coordinates(int x, double y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates c = (Coordinates) o;
        return x == c.x() && y == c.y();
    }

    public int x() {
        return x;
    }

    public double y() {
        return y;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + x + ", " + y + "}";
    }


    @Override
    public int compareTo(Coordinates coordinates) {
        return (int) (((x * x) + (y * y)) - ((coordinates.x * coordinates.x)
                + (coordinates.y * coordinates.y)));
    }
}
