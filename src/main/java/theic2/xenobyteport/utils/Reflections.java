package theic2.xenobyteport.utils;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Arrays;

public class Reflections {

    public static boolean exists(String clazz) {
        try {
            Class.forName(clazz);
            return true;
        } catch (ClassNotFoundException e) {
        }
        return false;
    }

    public static Field findField(Class<?> clazz, String... fieldNames) {
        Exception failed = null;
        for (String fieldName : fieldNames) {
            try {
                Field f = clazz.getDeclaredField(fieldName);
                f.setAccessible(true);
                return f;
            } catch (Exception e) {
                failed = e;
            }
        }
        throw new IllegalArgumentException(Arrays.toString(fieldNames) + " failed: " + failed);
    }

    public static <T, E> void setPrivateValue(Class<? super T> classToAccess, @Nullable T instance, @Nullable E value, String... fieldName) {
        try {
            findField(classToAccess, fieldName).set(instance, value);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T, E> E getPrivateValue(Class<? super T> classToAccess, @Nullable T instance, String... fieldName) {
        try {
            return (E) findField(classToAccess, fieldName).get(instance);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
