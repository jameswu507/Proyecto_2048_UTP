package vista;

import control.AudioManager;
import control.ControladorJuego;
import modelo.Tablero;
import modelo.Ficha;
import modelo.Direccion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Point;

public class GUI2048 extends JFrame {
    private ControladorJuego controlador;
    private JLabel lblScore;
    private JButton btnRestart;
    private AudioManager audioManager = new AudioManager();
    private GamePanel gamePanel;

    public GUI2048(Tablero tablero) {
        this.controlador = new ControladorJuego(tablero);

        setTitle("2048 - Edición Futurista UTP");
        setSize(540, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Música de fondo
        audioManager.reproducirMusicaLoop("audio/pantera_rosa.wav");

        // === PANEL SUPERIOR ===
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(10, 10, 30));

        // 🎚Slider de volumen
        JSlider sliderVolumen = new JSlider(0, 100, 50);
        sliderVolumen.setPreferredSize(new Dimension(120, 40));
        sliderVolumen.setBackground(new Color(10, 10, 30));
        sliderVolumen.setForeground(Color.CYAN);
        sliderVolumen.addChangeListener(e -> {
            float nivel = sliderVolumen.getValue() / 100f;
            audioManager.setVolumen(Math.max(nivel, 0.01f));
            GUI2048.this.requestFocusInWindow(); // Mantiene el foco para mover fichas
        });
        topPanel.add(sliderVolumen, BorderLayout.WEST);

        lblScore = new JLabel("Puntaje: 0", SwingConstants.CENTER);
        lblScore.setForeground(Color.CYAN);
        lblScore.setFont(new Font("Consolas", Font.BOLD, 20));
        topPanel.add(lblScore, BorderLayout.CENTER);

        btnRestart = new JButton("Play Again");
        btnRestart.setBackground(new Color(20, 20, 50));
        btnRestart.setForeground(Color.CYAN);
        btnRestart.setFocusPainted(false);
        btnRestart.setFont(new Font("Consolas", Font.BOLD, 14));
        btnRestart.addActionListener(e -> reiniciarJuego());
        topPanel.add(btnRestart, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // === PANEL DEL TABLERO ===
        gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);

        // === CONTROL TECLADO ===
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                String estadoAntes = tableroToString();

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W, KeyEvent.VK_UP -> controlador.gestionarEntrada(Direccion.ARRIBA);
                    case KeyEvent.VK_S, KeyEvent.VK_DOWN -> controlador.gestionarEntrada(Direccion.ABAJO);
                    case KeyEvent.VK_A, KeyEvent.VK_LEFT -> controlador.gestionarEntrada(Direccion.IZQUIERDA);
                    case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> controlador.gestionarEntrada(Direccion.DERECHA);
                }

                String estadoDespues = tableroToString();

                if (!estadoAntes.equals(estadoDespues)) {
                    audioManager.reproducirEfecto("audio/efectos_futuristas.wav");
                }

                lblScore.setText("Puntaje: " + controlador.obtenerPuntuacion());
                repaint();

                if (controlador.estaTerminado()) {
                    mostrarPantallaWasted();
                }
            }
        });

        setFocusable(true);
        setVisible(true);
    }

    // Reiniciar juego
    private void reiniciarJuego() {
        controlador = new ControladorJuego(new Tablero(4));
        lblScore.setText("Puntaje: 0");
        this.requestFocusInWindow();
        repaint();
    }

    // Pantalla WASTED
    private void mostrarPantallaWasted() {
        audioManager.detenerMusica();
        audioManager.reproducirEfecto("audio/wasted.wav");

        ImageIcon iconoWasted = new ImageIcon(getClass().getClassLoader().getResource("imagenes/wasted.png"));
        Image imagen = iconoWasted.getImage().getScaledInstance(350, 200, Image.SCALE_SMOOTH);
        iconoWasted = new ImageIcon(imagen);

        JOptionPane.showMessageDialog(
                null,
                "",
                "💀 WASTED - GAME OVER 💀",
                JOptionPane.PLAIN_MESSAGE,
                iconoWasted
        );

        reiniciarJuego();
        audioManager.reproducirMusicaLoop("audio/pantera_rosa.wav");
    }

    // Convertir tablero en String
    private String tableroToString() {
        StringBuilder sb = new StringBuilder();
        Ficha[][] celdas = controlador.getTablero().getCeldas();
        for (Ficha[] fila : celdas) {
            for (Ficha ficha : fila) {
                sb.append(ficha == null ? "." : ficha.getValor()).append(",");
            }
        }
        return sb.toString();
    }

    // Panel del tablero con fondo animado
    private class GamePanel extends JPanel {
        private Image fondo;
        private int desplazamientoX = 0;
        private int desplazamientoY = 0;
        private Timer animacionFondo;

        public GamePanel() {
            try {
                fondo = new ImageIcon(getClass().getClassLoader().getResource("imagenes/fondo_tablero.png")).getImage();
            } catch (Exception e) {
                System.out.println("No se pudo cargar la imagen de fondo.");
            }

            animacionFondo = new Timer(50, e -> {
                desplazamientoX = (desplazamientoX + 1) % (fondo.getWidth(null));
                desplazamientoY = (desplazamientoY + 1) % (fondo.getHeight(null));
                repaint();
            });
            animacionFondo.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Fondo animado
            if (fondo != null) {
                for (int x = -fondo.getWidth(null); x < getWidth() + fondo.getWidth(null); x += fondo.getWidth(null)) {
                    for (int y = -fondo.getHeight(null); y < getHeight() + fondo.getHeight(null); y += fondo.getHeight(null)) {
                        g.drawImage(fondo, x + desplazamientoX, y + desplazamientoY, this);
                    }
                }
            }

            // Celdas del juego
            Ficha[][] celdas = controlador.getTablero().getCeldas();
            int cellSize = 110;
            int startX = 30, startY = 50;

            for (int i = 0; i < celdas.length; i++) {
                for (int j = 0; j < celdas[i].length; j++) {
                    int x = startX + j * (cellSize + 10);
                    int y = startY + i * (cellSize + 10);

                    if (celdas[i][j] != null) {
                        int valor = celdas[i][j].getValor();

                        Color colorFondo;
                        Color colorTexto = Color.BLACK;
                        switch (valor) {
                            case 2 -> colorFondo = new Color(0, 255, 255);
                            case 4 -> colorFondo = new Color(0, 200, 255);
                            case 8 -> colorFondo = new Color(0, 150, 255);
                            case 16 -> colorFondo = new Color(0, 100, 255);
                            case 32 -> colorFondo = new Color(0, 50, 255);
                            case 64 -> colorFondo = new Color(100, 0, 255);
                            case 128 -> colorFondo = new Color(150, 0, 255);
                            case 256 -> colorFondo = new Color(200, 0, 200);
                            case 512 -> colorFondo = new Color(255, 0, 150);
                            case 1024 -> colorFondo = new Color(255, 50, 100);
                            case 2048 -> { colorFondo = new Color(255, 0, 0); colorTexto = Color.WHITE; }
                            default -> { colorFondo = new Color(255, 255, 255); colorTexto = Color.BLACK; }
                        }

                        g.setColor(colorFondo);
                        g.fillRoundRect(x, y, cellSize, cellSize, 20, 20);
                        g.setColor(Color.CYAN);
                        g.drawRoundRect(x, y, cellSize, cellSize, 20, 20);

                        g.setFont(new Font("Consolas", Font.BOLD, 28));
                        g.setColor(colorTexto);
                        String texto = String.valueOf(valor);
                        int textWidth = g.getFontMetrics().stringWidth(texto);
                        g.drawString(texto, x + (cellSize - textWidth) / 2, y + cellSize / 2);
                    } else {
                        g.setColor(new Color(20, 40, 60));
                        g.fillRoundRect(x, y, cellSize, cellSize, 20, 20);
                        g.setColor(new Color(0, 255, 255, 80));
                        g.drawRoundRect(x, y, cellSize, cellSize, 20, 20);
                    }
                }
            }
        }
    }
}
