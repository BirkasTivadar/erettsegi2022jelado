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
        LocalTime startTime = time;
        LocalTime endTime = masik.time();
        long seconds = eltelt(startTime.getHour(), startTime.getMinute(), startTime.getSecond(), endTime.getHour(), endTime.getMinute(), endTime.getSecond());
        return LocalTime.MIN.plusSeconds(seconds).toString();
    }

    private long eltelt(int startHour, int startMinute, int startSecond, int endHour, int endMinute, int endSecond) {
        LocalTime startTime = LocalTime.of(startHour, startMinute, startSecond);
        LocalTime endTime = LocalTime.of(endHour, endMinute, endSecond);
        return startTime.until(endTime, ChronoUnit.SECONDS);
    }
}
