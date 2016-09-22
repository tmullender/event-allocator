package co.escapeideas.eventallocator.persistence;

/**
 * Created by tim on 21/09/2016.
 */
public class KeyGenerator {

    public static String generateId(String a, String b) {
        return "ID" + String.format("%s-%s", a, b).hashCode();
    }
}
