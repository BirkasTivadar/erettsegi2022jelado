package jelado;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public record Jel(LocalTime time, int coordinateX, int coordinateY) {

    public double tavolsag(Jel masik) {
        int x = Math.abs(coordinateX - masik.coordinateX());
        int y = Math.abs(coordinateY - masik.coordinateY());
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public String idoKulonbseg(Jel masik) {
        long seconds = getSeconds(masik);
        return LocalTime.MIN.plusSeconds(seconds).toString();
    }

    private long getSeconds(Jel masik) {
        LocalTime startTime = time;
        LocalTime endTime = masik.time();
        long seconds = eltelt(startTime.getHour(), startTime.getMinute(), startTime.getSecond(), endTime.getHour(), endTime.getMinute(), endTime.getSecond());
        return Math.abs(seconds);
    }

    private long eltelt(int startHour, int startMinute, int startSecond, int endHour, int endMinute, int endSecond) {
        LocalTime startTime = LocalTime.of(startHour, startMinute, startSecond);
        LocalTime endTime = LocalTime.of(endHour, endMinute, endSecond);
        return startTime.until(endTime, ChronoUnit.SECONDS);
    }
/*
    Rendelkezésére áll a jel.txt nevű adatfájl, amely egy napról tartalmaz adatokat
    időrendben. Soraiban öt egész szám található, egymástól egy-egy szóközzel elválasztva. Az
    első három szám a jeladás időpontját (óra, perc, másodperc) adja meg, a negyedik szám az x,
    az ötödik az y koordináta. A sorok száma legfeljebb 1000, a koordináták -10 000 és 10 000
    közötti értékek lehetnek.
            Például: …
            3 21 19 126 639
            3 26 19 131 641
            3 27 55 124 651
            3 31 50 134 649
            …
            4 19 11 126 42
            4 29 11 128 36
            4 32 21 130 7
            …
    A példa első csoportjában a második sor megmutatja, hogy a jeladó 5 egységnyit mozdult x,
            2 egységnyit pedig y irányban 5 perc alatt. A harmadik bejegyzés azért született, mert y
    irányban 10 egységnyit mozdult el a jeladó, a negyedik bejegyzés pedig egy x irányú
10 egységnyi elmozdulást jelez.

Előfordulhat, hogy a vétel meghiúsul, ezért
lehetnek egymást követő adatsorok, amelyek között 5 percnél több idő telik el, vagy a
koordináták változása 10 egységnél nagyobb.
R
    */

    public boolean kimaradt(Jel masik) {
        return Math.abs(coordinateX - masik.coordinateX()) > 10 ||
                Math.abs(coordinateY - masik.coordinateY()) > 10 ||
                getSeconds(masik) > 600;
    }

    public String kimaradtJel(Jel masik) {
        String result = null;
        long elteresIdo = 0;
        int elteresTer = 0;
        int elteresX = 0;
        int elteresY = 0;
        long seconds = 0;
        if ((elteresX = Math.abs(coordinateX - masik.coordinateX())) > 10 ||
                (elteresY = Math.abs(coordinateY - masik.coordinateY())) > 10) {
            elteresTer = Math.max(elteresX / 10, elteresY / 10);
        }
        if ((seconds = getSeconds(masik)) > 600) {
            elteresIdo = seconds / 600;
        }
        if (elteresTer >= elteresIdo) {
            result = String.format("%d %d %d koordináta-eltérés %d", time.getHour(), time.getMinute(), time.getSecond(), elteresTer);
        }
        if (elteresIdo > elteresTer) {
            result = String.format("%d %d %d idoelteres %d", time.getHour(), time.getMinute(), time.getSecond(), elteresIdo);
        }
        return result;
    }
}
