package dados3D;

import java.util.Random;

public class Dado {
    private int caras;
    private Random random;

    public Dado(int caras) {
        this.caras = Math.max(3, caras); // Asegura al menos 3 caras
        this.random = new Random();
    }

    public int tirar() {
        return random.nextInt(caras) + 1;
    }

    public int getCaras() {
        return caras;
    }
}