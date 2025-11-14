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

	    public void recibirDanio(int danio) {
	        this.vida -= danio;
	        if (this.vida < 0) this.vida = 0;
	    }

	    public int getVida() { return vida; }
	    public String getNombre() { return nombre; }
	    public int getAtaque() { return ataque; }
	}

