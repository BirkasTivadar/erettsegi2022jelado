package jelado;

import java.time.LocalTime;

public record Jel(LocalTime time, int coordinateX, int coordinateY) {

    public double tavolsag(Jel masik) {
        int x = Math.abs(coordinateX - masik.coordinateX);
        int y = Math.abs(coordinateY - masik.coordinateY);
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
}
