package utility;

import java.util.Arrays;

public class Check {

    public static <T extends Enum<T>> boolean enumInclude(Class<T> enumClass, String name) {
        return Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(e -> e.name().equals(name));
    }
}
