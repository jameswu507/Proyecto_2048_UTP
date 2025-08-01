package control;

import modelo.Direccion;
import modelo.Tablero;

public class ControladorJuego {
    private Tablero tablero;

    public ControladorJuego(Tablero tablero) {
        this.tablero = tablero;
    }



    // Reinicia el juego con un tablero nuevo
    public void reiniciarJuego() {
        this.tablero = new Tablero(4);
    }

    // Devuelve la puntuación actual
    public int obtenerPuntuacion() {
        return tablero.getPuntaje();
    }

    // Retorna el tablero actual
    public Tablero getTablero() {
        return tablero;
    }

    // Verifica si el juego terminó
    public boolean estaTerminado() {
        return tablero.estaBloqueado();
    }
}
