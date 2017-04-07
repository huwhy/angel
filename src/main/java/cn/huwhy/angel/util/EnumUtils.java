package cn.huwhy.angel.util;

public class EnumUtils {

    public static boolean in(Enum e, Enum... enums) {
        if (e == null || enums.length == 0)
            return false;
        for (Enum em : enums) {
            if (e.equals(em)) return true;
        }
        return false;
    }

    public static boolean nin(Enum e, Enum... enums) {
        return !in(e, enums);
    }

    public static <T extends Enum> T valueOf(Class<T> enumClass, String name) {
        for (Enum e : enumClass.getEnumConstants()) {
            if (e.name().equals(name)) return (T) e;
        }
        return null;
    }

}
