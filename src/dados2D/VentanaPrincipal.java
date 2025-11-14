package dados2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {
    private Juego juego;

    public VentanaPrincipal() {
        this.juego = new Juego();
        inicializarVentana();
    }

    private void inicializarVentana() {
        setTitle("Juego de Rol");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton btnAtacar = new JButton("Atacar");
        JButton btnDefender = new JButton("Defender");

        btnAtacar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                juego.atacarConDado();
            }
        });

        btnDefender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(VentanaPrincipal.this, "¡Has decidido defender!");
            }
        });

        panel.add(btnAtacar);
        panel.add(btnDefender);

        add(panel, BorderLayout.CENTER);
    }
}