import me.bunny.BunnyHash;
import me.bunny.encoder.Base26Encoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BunnyHashTest {

    @Test
    @DisplayName("BunnyHash should work")
    void BunnyHash() {
        BunnyHash bunny = new BunnyHash("57885161", new Base26Encoder(6));
        assertEquals("WRKYJA", bunny.hash("26"));
        assertEquals(26, bunny.reverse("WRKYJA").intValue());
    }

    @Test
    @DisplayName("BunnyHash offset too long")
    void BunnyHash_offset_too_long() {
        assertThrows(ArithmeticException.class, () -> new BunnyHash("57885161", new Base26Encoder(1), "27"));
    }

    @Test
    @DisplayName("BunnyHash offset is negative")
    void BunnyHash_offset_negative() {
        assertThrows(ArithmeticException.class, () -> new BunnyHash("57885161", new Base26Encoder(1), "-27"));
    }

    @Test
    @DisplayName("BunnyHash id too big")
    void BunnyHash_ID_too_big() {
        BunnyHash bunny = new BunnyHash("57885161", new Base26Encoder(6));
        assertThrows(ArithmeticException.class, () -> bunny.hash("18725187248172456"));
    }

    @Test
    @DisplayName("BunnyHash id is negative")
    void BunnyHash_ID_negative() {
        BunnyHash bunny = new BunnyHash("57885161", new Base26Encoder(6));
        assertThrows(ArithmeticException.class, () -> bunny.hash("-1"));
    }

    @Test
    @DisplayName("BunnyHash multiplier isn't coprime to capacity")
    void BunnyHash_multiplier_coprime_capacity() {
        assertThrows(ArithmeticException.class, () -> new BunnyHash("26", new Base26Encoder(6)));
    }

}
