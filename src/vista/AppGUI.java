package vista;

import modelo.Tablero;
import javax.swing.*;

public class AppGUI {
    public static void main(String[] args) {
        // Crear tablero de 4x4
        Tablero tablero = new Tablero(4);

        // Iniciar GUI futurista
        SwingUtilities.invokeLater(() -> {
            GUI2048 gui = new GUI2048(tablero);
            gui.setVisible(true);
        });
    }
}
