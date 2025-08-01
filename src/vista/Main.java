package vista;

import modelo.Tablero;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Objects;

public class Main extends JFrame {

    public Main() {
        setTitle("2048 - Proyecto Final UTP");
        setSize(650, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panelBase = new JPanel(new BorderLayout());
        panelBase.setBackground(Color.BLACK);
        setContentPane(panelBase);

        // === Fondo GIF ===
        JLabel background = new JLabel(new ImageIcon(Objects.requireNonNull(
                getClass().getClassLoader().getResource("imagenes/fondo_inicio.gif"))));
        background.setLayout(new BorderLayout());
        panelBase.add(background, BorderLayout.CENTER);

        // === Panel superior con dos logos y título ===
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setOpaque(false);
        panelTop.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Logo UTP izquierda
        JLabel lblLogoUTP = new JLabel(cargarImagen("imagenes/logo_utp.png", 90, 90));

        // Logo FISC derecha
        JLabel lblLogoFISC = new JLabel(cargarImagen("imagenes/logo2_utp.png", 95, 95));

        // Panel central del título
        JPanel panelTitulo = new JPanel(new GridLayout(3, 1));
        panelTitulo.setOpaque(false);
        JLabel titulo = new JLabel("Proyecto Final - Juego 2048", SwingConstants.CENTER);
        titulo.setFont(new Font("Consolas", Font.BOLD, 22));
        titulo.setForeground(new Color(0, 255, 255));

        JLabel facultad = new JLabel("Facultad de Ingeniería de Sistemas Computacionales", SwingConstants.CENTER);
        facultad.setForeground(Color.white);
        facultad.setFont(new Font("Consolas", Font.PLAIN, 14));

        JLabel carrera = new JLabel("Carrera: Licenciatura en Ingeniería de Software", SwingConstants.CENTER);
        carrera.setForeground(Color.white);
        carrera.setFont(new Font("Consolas", Font.PLAIN, 14));

        panelTitulo.add(titulo);
        panelTitulo.add(facultad);
        panelTitulo.add(carrera);

        panelTop.add(lblLogoUTP, BorderLayout.WEST);
        panelTop.add(panelTitulo, BorderLayout.CENTER);
        panelTop.add(lblLogoFISC, BorderLayout.EAST);

        // === Panel central con integrantes ===
        JPanel panelCentro = new JPanel();
        panelCentro.setOpaque(false);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        JLabel integrantes = new JLabel("<html><center>Integrantes:<br>Jaime Wu 8-1024-2485 <br>Andrés Wu 8-1027-2259 <br>- Michael Chen 8-1034-732 <br>- YongSheng Du E-8-199982 <br> 1SF122</center></html>", SwingConstants.CENTER);
        integrantes.setForeground(Color.WHITE);
        integrantes.setFont(new Font("Consolas", Font.PLAIN, 30));
        integrantes.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel profesor = new JLabel("Profesor: Rodrigo Yángüez", SwingConstants.CENTER);
        profesor.setForeground(Color.WHITE);
        profesor.setFont(new Font("Consolas", Font.PLAIN, 30));
        profesor.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelCentro.add(Box.createVerticalStrut(70));
        panelCentro.add(integrantes);
        panelCentro.add(Box.createVerticalStrut(10));
        panelCentro.add(profesor);

        // === Panel inferior con botones centrados ===
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

        JButton btnIniciar = crearBotonFuturista("Iniciar Juego", new Color(0, 255, 255));
        btnIniciar.addActionListener(e -> {
            new GUI2048(new Tablero(4));
            dispose();
        });

        JButton btnSalir = crearBotonFuturista("Salir", new Color(255, 80, 80));
        btnSalir.addActionListener(e -> System.exit(0));

        panelBotones.add(btnIniciar);
        panelBotones.add(Box.createHorizontalStrut(30));
        panelBotones.add(btnSalir);

        background.add(panelTop, BorderLayout.NORTH);
        background.add(panelCentro, BorderLayout.CENTER);
        background.add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    private ImageIcon cargarImagen(String ruta, int ancho, int alto) {
        try {
            URL url = getClass().getClassLoader().getResource(ruta);
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            }
        } catch (Exception e) {
            System.err.println("No se pudo cargar: " + ruta);
        }
        return null;
    }

    private JButton crearBotonFuturista(String texto, Color colorTexto) {
        JButton boton = new JButton(texto);
        boton.setBackground(Color.BLACK);
        boton.setForeground(colorTexto);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Consolas", Font.BOLD, 18));
        boton.setBorder(BorderFactory.createLineBorder(colorTexto, 2, true));
        boton.setPreferredSize(new Dimension(200, 45));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(20, 20, 20));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(Color.BLACK);
            }
        });
        return boton;
    }

    public static void main(String[] args) {
        new Main();
    }
}
