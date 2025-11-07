package dados3D;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

/**
 * Panel visual 2D para dados genéricos (cualquier número de caras).
 * Diseño moderno, con efecto 3D simple y número grande animado.
 */
public class PanelDado2D extends JPanel {
    private int valor = 1;
    private int numCaras = 6;
    private boolean animando = false;
    private int contadorAnimacion = 0; // Para controlar la animación de los puntos

    public PanelDado2D() {
        setOpaque(false);
    }

    public void setValor(int valor) {
        this.valor = valor;
        repaint();
    }

    public void setNumCaras(int numCaras) {
        this.numCaras = numCaras;
        repaint();
    }

    public void setAnimando(boolean animando) {
        this.animando = animando;
        if (!animando) {
            contadorAnimacion = 0; // Reinicia la animación al terminar
        }
        repaint();
    }

    public void actualizarContadorAnimacion() {
        if (animando) {
            contadorAnimacion = (contadorAnimacion + 1) % 30; // 30 frames antes de reiniciar la secuencia
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int w = getWidth();
        int h = getHeight();
        int size = Math.min(w, h) - 60;
        int x = (w - size) / 2;
        int y = (h - size) / 2;

        // Sombra suave
        g2d.setColor(new Color(0, 0, 0, 60));
        g2d.fillRoundRect(x + 8, y + 8, size, size, 25, 25);

        // Cuerpo del dado (gradiente rojo fuego)
        GradientPaint bodyGP = new GradientPaint(
            x, y, new Color(220, 20, 60),
            x + size, y + size, new Color(178, 34, 34)
        );
        g2d.setPaint(bodyGP);
        g2d.fillRoundRect(x, y, size, size, 25, 25);

        // Borde brillante
        g2d.setColor(new Color(255, 255, 255, 180));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(x + 2, y + 2, size - 4, size - 4, 25, 25);

        // Reflejo circular en esquina superior izquierda
        RadialGradientPaint reflectGP = new RadialGradientPaint(
            new Point(x + 30, y + 30),
            60,
            new float[]{0.0f, 0.8f, 1.0f},
            new Color[]{new Color(255, 255, 255, 200), new Color(255, 255, 255, 80), new Color(255, 255, 255, 0)}
        );
        g2d.setPaint(reflectGP);
        g2d.fill(new RoundRectangle2D.Float(x + 10, y + 10, 100, 100, 30, 30));

        // Número grande en el centro o animación de puntos
        String texto;
        if (animando) {
            // Calculamos cuántos puntos mostrar basados en el contador
            int puntos = (contadorAnimacion / 10) % 3; // 0 -> 0 puntos, 1 -> 1 punto, 2 -> 2 puntos, 3 -> 3 puntos
            texto = ".".repeat(puntos + 1);
        } else {
            texto = String.valueOf(valor);
        }

        // Definimos la fuente y obtenemos FontMetrics aquí, antes de usarlo
        Font numFont = new Font("Arial", Font.BOLD, (int)(size * 0.6));
        g2d.setFont(numFont);
        FontMetrics fm = g2d.getFontMetrics(); // <-- ¡Aquí se declara!
        int txtWidth = fm.stringWidth(texto);
        int ascent = fm.getAscent();
        g2d.setColor(Color.WHITE);
        g2d.drawString(texto, x + (size - txtWidth) / 2, y + (size + ascent) / 2 - 5);

        // Sombra del número (opcional, para más profundidad)
        if (!animando) {
            g2d.setColor(new Color(0, 0, 0, 100));
            g2d.drawString(texto, x + (size - txtWidth) / 2 + 3, y + (size + ascent) / 2 - 5 + 3);
            g2d.setColor(Color.WHITE);
            g2d.drawString(texto, x + (size - txtWidth) / 2, y + (size + ascent) / 2 - 5);
        }
    }
}