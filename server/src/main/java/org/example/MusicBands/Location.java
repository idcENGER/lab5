package org.example.MusicBands;

public class Location {
    private double x;
    private long y;
    private float z;

    public Location(double x,long y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    @Override
    public boolean equals(Object o){
        if (this == o)return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location l = (Location) o;
        return x == l.getX() && y == l.getY() && z == l.getZ();
    }

    @Override
    public String toString(){
        return getClass().getSimpleName() + "{" + x + ", " + y + ", " + z + "}";
    }
}
