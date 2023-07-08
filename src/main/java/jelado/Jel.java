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



    public boolean kimaradt(Jel masik) {
        return Math.abs(coordinateX - masik.coordinateX()) > 10 ||
                Math.abs(coordinateY - masik.coordinateY()) > 10 ||
                Math.abs(getSeconds(masik)) > 600;
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
        if ((seconds = Math.abs(getSeconds(masik))) > 600) {
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

    private long getSeconds(Jel masik) {
        LocalTime startTime = time;
        LocalTime endTime = masik.time();
        return eltelt(startTime.getHour(), startTime.getMinute(), startTime.getSecond(), endTime.getHour(), endTime.getMinute(), endTime.getSecond());
    }

    private long eltelt(int startHour, int startMinute, int startSecond, int endHour, int endMinute, int endSecond) {
        LocalTime startTime = LocalTime.of(startHour, startMinute, startSecond);
        LocalTime endTime = LocalTime.of(endHour, endMinute, endSecond);
        return startTime.until(endTime, ChronoUnit.SECONDS);
    }
}
