package vista;

import modelo.Tablero;
import modelo.Direccion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AppConsola {
    public static void main(String[] args) throws IOException {
        Tablero tablero = new Tablero(4);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input;

        System.out.println("=== 2048 - Versión Consola ===");
        tablero.mostrarTablero();

        while (true) {
            System.out.println("Mover (W/A/S/D) o Q para salir:");
            input = br.readLine().toUpperCase();

            switch (input) {
                case "W" -> tablero.mover(Direccion.ARRIBA);
                case "S" -> tablero.mover(Direccion.ABAJO);
                case "A" -> tablero.mover(Direccion.IZQUIERDA);
                case "D" -> tablero.mover(Direccion.DERECHA);
                case "Q" -> {
                    System.out.println("Juego terminado.");
                    return;
                }
                default -> System.out.println("Comando inválido.");
            }

            tablero.mostrarTablero();

            if (tablero.estaBloqueado()) {
                System.out.println("¡Game Over!");
                break;
            }
        }
    }
}
