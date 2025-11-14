package dados2D;

import dados2D.LanzadorDeDados;
import javax.swing.SwingUtilities;

public class Juego {
	
	    private Personaje jugador;
	    private Personaje enemigo;

	    public Juego() {
	        jugador = new Personaje("Héroe", 100, 15);
	        enemigo = new Personaje("Monstruo", 80, 12);
	    }

	    public void atacarConDado() {
	        LanzadorDeDados lanzador = new LanzadorDeDados(null); // null si no tienes una ventana principal
	        lanzador.setVisible(true); // Esto es modal, se queda aquí hasta que se cierre

	        Integer resultado = lanzador.obtenerResultado();
	        if (resultado != null) {
	            System.out.println("Has tirado el dado y salió: " + resultado);
	            int danio = jugador.getAtaque() + resultado;
	            enemigo.recibirDanio(danio);
	            System.out.println("Has atacado al " + enemigo.getNombre() + " por " + danio + " puntos de daño.");
	            System.out.println("Vida restante del enemigo: " + enemigo.getVida());
	        } else {
	            System.out.println("No se obtuvo un resultado del dado.");
	        }
	    }

	    public static void main(String[] args) {
	        Juego juego = new Juego();

	        // Ejemplo de uso: tirar un dado para atacar
	        SwingUtilities.invokeLater(() -> {
	            juego.atacarConDado();
	        });
	    }
	}



