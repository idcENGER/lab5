package org.example.MusicBands;

public enum MusicGenre implements Comparable<MusicGenre>{
    ROCK,
    PROGRESSIVE_ROCK,
    PSYCHEDELIC_ROCK,
    SOUL,
    PUNK_ROCK;

    public static void genre() {
        for (var i : MusicGenre.values()){
            String index = String.valueOf(i.ordinal());
            String sign = (MusicGenre.PUNK_ROCK.ordinal() == Integer.parseInt(index)) ? "." : ", ";
            System.out.print((index + ": " + i) + sign);
        }
        System.out.println();
    }

}
