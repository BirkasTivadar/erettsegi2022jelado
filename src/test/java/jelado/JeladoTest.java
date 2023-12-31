package jelado;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JeladoTest {

    Jelado jelado = new Jelado();

    @BeforeEach
    void init() {
        jelado.loadFromFile(Path.of("src", "main", "resources", "jel.txt"));
    }

    @Test
    void getJelekTest() {
        assertEquals(250, jelado.getJelek().size());
    }

    @Test
    void getJelByOrdinalNumberTest() {
        assertEquals(new Jel(LocalTime.of(3, 11, 19), 122, 644), jelado.getJelByOrdinalNumber(1));
        assertEquals(new Jel(LocalTime.of(15, 31, 18), 100, 679), jelado.getJelByOrdinalNumber(163));
    }

    @Test
    void getDifferentTimeTest() {
        assertEquals("18:52:40", jelado.getIdoKulonbseg());
    }

    @Test
    void getJeladoMaxTeruletTest() {
        Teglalap teglalap = new Teglalap(4, 147, 639, 727);
        assertEquals(teglalap, jelado.getJeladoMaxTerulet());
    }

    @Test
    void tavolsagTest() {
        Jel jel = new Jel(LocalTime.of(0, 0, 0), 0, 0);
        Jel masik = new Jel(LocalTime.of(0, 0, 0), 3, 4);
        assertEquals(5, jel.tavolsag(masik));
    }

    @Test
    void getOsszesTavTest() {
        assertEquals(2007.676579316191, jelado.getOsszesTav());
    }
}
