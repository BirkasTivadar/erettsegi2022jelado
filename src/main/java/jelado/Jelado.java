package jelado;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Jelado {

    private final Map<Integer, Jel> jelek = new HashMap<>();

    public Map<Integer, Jel> getJelek() {
        return Collections.unmodifiableMap(jelek);
    }

    public void loadFromFile(Path path) {
        String line;
        Integer ordinalNumber = 1;
        try (BufferedReader bufferedReader = Files.newBufferedReader(path)) {
            while ((line = bufferedReader.readLine()) != null) {
                String[] numbersArray = line.split(" ");
                int hour = Integer.parseInt(numbersArray[0]);
                int minute = Integer.parseInt(numbersArray[1]);
                int second = Integer.parseInt(numbersArray[2]);
                int coordinateX = Integer.parseInt(numbersArray[3]);
                int coordinateY = Integer.parseInt(numbersArray[4]);
                Jel jel = new Jel(LocalTime.of(hour, minute, second), coordinateX, coordinateY);
                jelek.put(ordinalNumber, jel);
                ordinalNumber++;
            }
        } catch (IOException ioException) {
            throw new IllegalStateException("Can not read file", ioException);
        }
    }

    public Jel getJelByOrdinalNumber(int ordinalNumber) {
        return getJelek().get(ordinalNumber);
    }

    /*
        3. Készítsen függvényt eltelt néven, amely megadja, hogy a paraméterként átadott két
        időpont között hány másodperc telik el! A két időpontot, mint paramétert tetszőleges módon
        átadhatja. Használhat három-három számértéket, két tömböt vagy listát, de más, a célnak
        megfelelő típusú változót is. Ezt a függvényt később használja fel legalább egy feladat
        megoldása során!
     */
    public String getIdoKulonbseg() {
        return getJelByOrdinalNumber(1).idoKulonbseg(getJelByOrdinalNumber(jelek.size()));
    }

    public Teglalap getJeladoMaxTerulet() {
        int minX = jelek.values().stream()
                .mapToInt(Jel::coordinateX).min().orElse(0);
        int maxX = jelek.values().stream()
                .mapToInt(Jel::coordinateX).max().orElse(0);
        int minY = jelek.values().stream()
                .mapToInt(Jel::coordinateY).min().orElse(0);
        int maxY = jelek.values().stream()
                .mapToInt(Jel::coordinateY).max().orElse(0);
        return new Teglalap(minX, maxX, minY, maxY);
    }

    public double getOsszesTav() {
        return IntStream.range(1, jelek.size())
                .mapToDouble(i -> jelek.get(i).tavolsag(jelek.get(i + 1)))
                .sum();
    }

    public void printKimaradtak() {
        IntStream.range(2, jelek.size() + 1)
                .filter(i -> jelek.get(i).kimaradt(jelek.get(i - 1)))
                .forEach(i -> System.out.println(jelek.get(i).kimaradtJel(jelek.get(i - 1))));
    }

    public void writeToFile(Path path) {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            for (int i = 2; i < jelek.size() + 1; i++) {
                if (jelek.get(i).kimaradt(jelek.get(i - 1))) {
                    bufferedWriter.write(jelek.get(i).kimaradtJel(jelek.get(i - 1))
                            .concat(System.lineSeparator()));
                }
            }
        } catch (IOException ioException) {
            throw new IllegalStateException("Can not write file", ioException);
        }
    }

}
