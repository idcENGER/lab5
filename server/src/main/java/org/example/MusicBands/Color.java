package org.example.MusicBands;

public enum Color {
    GREEN,
    BLUE,
    ORANGE,
    WHITE;

    public static void colors() {
        for (var i : Color.values()){
            String index = String.valueOf(i.ordinal());
            String sign = (Color.WHITE.ordinal() == Integer.parseInt(index)) ? "." : ", ";
            System.out.print((index + ": " + i) + sign);
        }
        System.out.println();
    }
}
