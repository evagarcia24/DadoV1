package dados2D;

public class Personaje {
    private String nombre;
    private int vida;
    private int ataque;

    public Personaje(String nombre, int vida, int ataque) {
        this.nombre = nombre;
        this.vida = vida;
        this.ataque = ataque;
    }

    public void recibirDanio(int cantidad) {
        this.vida = Math.max(0, this.vida - cantidad);
    }

    // Getters
    public String getNombre() { return nombre; }
    public int getVida() { return vida; }
    public int getAtaque() { return ataque; }
}