import me.bunny.encoder.Base26Encoder;
import me.bunny.encoder.Encoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

public class Base26EncoderTest {

    @Test
    @DisplayName("Base26 should display correctly")
    void base26_display_correctly() {
        Encoder encoder = new Base26Encoder(6);
        assertEquals("AAAAAA", encoder.intToBase(new BigInteger("0")));
        assertEquals(0, encoder.baseToInt("AAAAAA").intValue());
        assertEquals("AAAABA", encoder.intToBase(new BigInteger("26")));
        assertEquals(26, encoder.baseToInt("AAAABA").intValue());
    }

    @Test
    @DisplayName("Wrong length should throw exception")
    void base26_wrong_length() {
        Encoder encoder = new Base26Encoder(6);
        assertThrows(ArithmeticException.class, () -> encoder.baseToInt("ABCDE"));
        assertThrows(ArithmeticException.class, () -> encoder.baseToInt("ABCDEFG"));
    }

}
