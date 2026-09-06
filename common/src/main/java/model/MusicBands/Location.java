package model.MusicBands;

import java.io.Serial;
import java.io.Serializable;

public class Location implements Serializable {

    private final double x;
    private final long y;
    private final float z;


    @Serial
    private static final long serialVersionUID = -5770729028301307786L;

    public Location(double x, long y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location l = (Location) o;
        return x == l.x() && y == l.y() && z == l.z();
    }

    public double x() {
        return x;
    }

    public long y() {
        return y;
    }

    public float z() {
        return z;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + x + ", " + y + ", " + z + "}";
    }
}
