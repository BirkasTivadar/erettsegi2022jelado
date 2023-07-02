package jelado;

import java.nio.file.Path;
import java.util.Scanner;

public class JeladoMain {

    public static void main(String[] args) {


//      1. Olvassa be a jel.txt állomány tartalmát, tárolja el a rögzített jelek adatait, és azok felhasználásával oldja meg a következő feladatokat
        Jelado jelado = new Jelado();
        jelado.loadFromFile(Path.of("src", "main", "resources", "jel.txt"));

//      2. Kérje be a felhasználótól egy jel sorszámát (a sorszámozás 1-től indul), és írja a képernyőre az adott jeladáshoz tartozó x és y koordinátát!
        System.out.println("2. feladat");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Kérem a kiírandó jel sorszámát: ");
        int ordinalNunmber = scanner.nextInt();
        System.out.printf("A %d. sorszámú jel X koordinátája %d", ordinalNunmber, jelado.getJelByOrdinalNumber(ordinalNunmber).coordinateX());
        System.out.println();
        System.out.printf("A %d. sorszámú jel Y koordinátája %d", ordinalNunmber, jelado.getJelByOrdinalNumber(ordinalNunmber).coordinateY());
        System.out.println();

//      4. Adja meg, mennyi idő telt el az első és az utolsó észlelés között! Az időt óra:perc:mperc alakban írja a képernyőre!
        System.out.println();
        System.out.println("4. feladat");
        System.out.printf("Az eltelt idő az első és az utolsó észlelés között: %s", jelado.getDifferentTime());
        System.out.println();

//      5. Határozza meg azt a legkisebb, a koordináta-rendszer tengelyeivel párhuzamos oldalú téglalapot, amelyből nem lépett ki a jeladó! Adja meg a téglalap bal alsó és jobb felső sarkának koordinátáit!
        System.out.println();
        System.out.println("5. feladat");
        Teglalap teglalap = jelado.getJeladoMaxTerulet();
        System.out.printf("A kérdezett terület bal alsó koordinátái: x = %d, y = %d", teglalap.minX(), teglalap.minY());
        System.out.println();
        System.out.printf("A kérdezett terület jobb felső koordinátái: x = %d, y = %d", teglalap.maxX(), teglalap.maxY());
        System.out.println();

        /*
        6. Írja a képernyőre, hogy mennyi volt a jeladó elmozdulásainak összege!
        Úgy tekintjük, hogy a jeladó két pozíciója közötti elmozdulása a pozíciókat összekötő egyenes mentén történt.
        Az összeget három tizedes pontossággal jelenítse meg!
        A kiírásnál a tizedesvessző és tizedespont kiírása is elfogadott.
        Az i-edik és az i+1-edik pontok távolságát képlet segítségével határozhatja meg.
        */
        System.out.println();
        System.out.println("6. feladat");
        System.out.printf("A jeladó által bejárt össztávolság: %.3f", jelado.getOsszesTav());
    }
}