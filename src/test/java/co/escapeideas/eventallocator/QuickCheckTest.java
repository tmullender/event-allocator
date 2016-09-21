package co.escapeideas.eventallocator;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.apache.commons.lang.StringUtils;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by tmullender on 26/04/16.
 */
@RunWith(JUnitQuickcheck.class)
public class QuickCheckTest {

    @Property
    public void translate(String input) {
        final String result = StringUtils.reverse(input);
        assertEquals(input.length(), result.length());
        char[] i = input.toCharArray();
        char[] r = input.toCharArray();
        Arrays.sort(i);
        Arrays.sort(r);
        assertEquals(new String(i), new String(r));
    }

}