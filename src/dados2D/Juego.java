package dados2D;

public class Juego {
    private Personaje jugador;
    private Personaje enemigo;

    public Juego() {
        jugador = new Personaje("Héroe", 100, 15);
        enemigo = new Personaje("Monstruo", 80, 12);
    }

    public String atacarConDadoHtml() {
        // Simulamos el lanzamiento de dado
        int resultado = (int) (Math.random() * 6) + 1; // Dado de 6 caras
        int danio = jugador.getAtaque() + resultado;
        enemigo.recibirDanio(danio);
        return "Has atacado con un dado y sacaste " + resultado + ". Daño total: " + danio + ". Vida del enemigo: " + enemigo.getVida();
    }

    public String obtenerEstado() {
        return "Jugador: " + jugador.getNombre() + " (Vida: " + jugador.getVida() + ") | Enemigo: " + enemigo.getNombre() + " (Vida: " + enemigo.getVida() + ")";
    }

    public static void main(String[] args) throws Exception {
        ServidorWeb servidor = new ServidorWeb();
        servidor.iniciar();
    }
}