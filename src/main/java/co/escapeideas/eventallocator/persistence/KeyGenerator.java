package co.escapeideas.eventallocator.persistence;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by tim on 21/09/2016.
 */
public class KeyGenerator {

    public static String generateId(String a, String b) {
        final String id = String.format("%s-%s", a, b);
        try {
            return URLEncoder.encode(id, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return id;
        }
    }
}
