package jelado;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private long eltelt(int startHour, int startMinute, int startSecond, int endHour, int endMinute, int endSecond) {
        LocalTime startTime = LocalTime.of(startHour, startMinute, startSecond);
        LocalTime endTime = LocalTime.of(endHour, endMinute, endSecond);
        return startTime.until(endTime, ChronoUnit.SECONDS);
    }

    public String getDifferentTime() {
        LocalTime startTime = getJelByOrdinalNumber(1).time();
        LocalTime endTime = getJelByOrdinalNumber(jelek.size()).time();
        long seconds = eltelt(startTime.getHour(), startTime.getMinute(), startTime.getSecond(), endTime.getHour(), endTime.getMinute(), endTime.getSecond());
        return LocalTime.MIN.plusSeconds(seconds).toString();
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

    private double tavolsag(Jel start, Jel end) {
        int startX = start.coordinateX();
        int startY = start.coordinateY();
        int endX = end.coordinateX();
        int endY = end.coordinateY();
        int x = Math.abs(endX - startX);
        int y = Math.abs(endY - startY);
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public double getOsszesTav() {
        List<Jel> jelList = jelek.values().stream().toList();
        int length = jelList.size();
        double osszTav = 0;
        for (int i = 1; i < length; i++) {
            osszTav += tavolsag(jelList.get(i - 1), jelList.get(i));
        }
        return osszTav;
    }
}
