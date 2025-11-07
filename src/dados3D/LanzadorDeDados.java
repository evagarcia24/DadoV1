package dados3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LanzadorDeDados extends JDialog {
    private String labelResultadoText = "Selecciona el dado y tira";
    private JComboBox<Integer> comboCaras;
    private JPanel panelDadoContainer;
    private PanelDado2D panelDado2D;
    private JPanel panelResultado;
    private JLabel lblTitulo;
    private Integer resultado = null; // Variable para almacenar el resultado

    public LanzadorDeDados(Frame parent) {
        super(parent, "Tirar Dado", true); // Modal
        setSize(1150, 850);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = crearPanelConFondo();
        panelPrincipal.setLayout(new BorderLayout(30, 30));

        JLabel titulo = new JLabel("JUEGO DE ROL - TIRADA DE DADOS", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 50));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(20, 20));
        centro.setOpaque(false);
        centro.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        JPanel panelControl = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelControl.setOpaque(false);

        JLabel lblCaras = new JLabel("Número de caras:");
        lblCaras.setForeground(Color.WHITE);
        lblCaras.setFont(new Font("Arial", Font.PLAIN, 18));

        comboCaras = new JComboBox<>(new Integer[]{3, 6, 9, 12, 15, 18, 21});
        comboCaras.setFont(new Font("Arial", Font.PLAIN, 18));
        comboCaras.setPreferredSize(new Dimension(100, 35));
        comboCaras.addActionListener(e -> actualizarTituloDado());

        JButton btnTirar = crearBoton("🎲 Tirar Dado");
        btnTirar.setFont(new Font("Arial", Font.BOLD, 20));
        btnTirar.setPreferredSize(new Dimension(220, 50));
        btnTirar.addActionListener(e -> tirarDado());

        panelControl.add(lblCaras);
        panelControl.add(comboCaras);
        panelControl.add(btnTirar);

        centro.add(panelControl, BorderLayout.NORTH);

        panelDadoContainer = new JPanel(new BorderLayout());
        panelDadoContainer.setOpaque(false);

        lblTitulo = new JLabel("Dado de " + comboCaras.getSelectedItem() + " caras", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        panelDadoContainer.add(lblTitulo, BorderLayout.NORTH);

        actualizarPanelDado();

        centro.add(panelDadoContainer, BorderLayout.CENTER);

        panelResultado = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                g2d.setColor(new Color(0, 0, 0, 180));
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setFont(new Font("Arial", Font.BOLD, 32));
                g2d.setColor(Color.WHITE);
                String texto = labelResultadoText;
                FontMetrics fm = g2d.getFontMetrics();
                int txtWidth = fm.stringWidth(texto);
                int ascent = fm.getAscent();
                int x = (getWidth() - txtWidth) / 2;
                int y = (getHeight() + ascent) / 2 - 5;
                g2d.drawString(texto, x, y);
            }
        };
        panelResultado.setOpaque(false);

        centro.add(panelResultado, BorderLayout.SOUTH);

        panelPrincipal.add(centro, BorderLayout.CENTER);
        add(panelPrincipal);
    }

    private void actualizarTituloDado() {
        int caras = (Integer) comboCaras.getSelectedItem();
        lblTitulo.setText("Dado de " + caras + " caras");
    }

    private void actualizarPanelDado() {
        Component[] components = panelDadoContainer.getComponents();
        for (Component c : components) {
            if (!(c instanceof JLabel)) {
                panelDadoContainer.remove(c);
            }
        }

        panelDado2D = new PanelDado2D();
        panelDado2D.setNumCaras((Integer) comboCaras.getSelectedItem());
        panelDadoContainer.add(panelDado2D, BorderLayout.CENTER);

        panelDadoContainer.revalidate();
        panelDadoContainer.repaint();
    }

    private void tirarDado() {
        int caras = (Integer) comboCaras.getSelectedItem();
        Dado dado = new Dado(caras);
        animarDado(dado, caras);
    }

    private void animarDado(final Dado dado, final int caras) {
        labelResultadoText = "🎲 Girando...";
        if (panelDado2D != null) {
            panelDado2D.setAnimando(true);
        }
        panelResultado.repaint();

        final int[] contador = {0};
        Timer animacion = new Timer(60, e -> {
            if (panelDado2D != null) {
                panelDado2D.actualizarContadorAnimacion();
            }

            contador[0]++;
            if (contador[0] >= 35) {
                ((Timer) e.getSource()).stop();
                int valor = dado.tirar();
                labelResultadoText = "✅ Resultado: " + valor;
                // Guardamos el resultado para que el juego lo pueda leer
                resultado = valor;
                if (panelDado2D != null) {
                    panelDado2D.setAnimando(false);
                    panelDado2D.setValor(valor);
                    panelDado2D.setNumCaras(caras);
                }
                panelResultado.repaint();

                // Opcional: Cerrar la ventana después de mostrar el resultado
                // SwingUtilities.invokeLater(() -> this.dispose());
            }
        });
        animacion.start();
    }

    private JPanel crearPanelConFondo() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, Color.BLACK, 0, getHeight(), new Color(20, 20, 20));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setForeground(Color.BLACK);
        boton.setBackground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                boton.setBackground(new Color(240, 240, 240));
            }

            public void mouseExited(MouseEvent evt) {
                boton.setBackground(Color.WHITE);
            }
        });
        return boton;
    }

    // Método para obtener el resultado
    public Integer obtenerResultado() {
        return resultado;
    }
}