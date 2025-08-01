package modelo;

import java.awt.Point;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Tablero {
    private Ficha[][] celdas;
    private int puntaje;

    // Posiciones fusionadas para animación de zoom
    private Set<Point> celdasFusionadas = new HashSet<>();

    public Tablero(int size) {
        celdas = new Ficha[size][size];
        puntaje = 0;
        agregarFicha();
        agregarFicha();
    }

    public Ficha[][] getCeldas() {
        return celdas;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public Set<Point> getCeldasFusionadas() {
        return celdasFusionadas;
    }

    // Agregar ficha en posición aleatoria
    public void agregarFicha() {
        Random random = new Random();
        int fila, col;
        do {
            fila = random.nextInt(celdas.length);
            col = random.nextInt(celdas.length);
        } while (celdas[fila][col] != null);
        celdas[fila][col] = new Ficha(random.nextDouble() < 0.9 ? 2 : 4);
    }

    // Mover fichas en la dirección indicada
    public void mover(Direccion direccion) {
        celdasFusionadas.clear(); // limpiar fusiones anteriores
        boolean movimientoRealizado = false;

        for (int i = 0; i < celdas.length; i++) {
            Ficha[] linea = obtenerLinea(i, direccion);
            Ficha[] combinada = combinarLinea(linea, i, direccion);
            movimientoRealizado |= insertarLinea(combinada, i, direccion);
        }

        if (movimientoRealizado) {
            agregarFicha();
        }
    }

    // Obtener línea (fila o columna)
    private Ficha[] obtenerLinea(int index, Direccion dir) {
        Ficha[] linea = new Ficha[celdas.length];
        for (int i = 0; i < celdas.length; i++) {
            switch (dir) {
                case IZQUIERDA -> linea[i] = celdas[index][i];
                case DERECHA -> linea[i] = celdas[index][celdas.length - 1 - i];
                case ARRIBA -> linea[i] = celdas[i][index];
                case ABAJO -> linea[i] = celdas[celdas.length - 1 - i][index];
            }
        }
        return compactar(linea);
    }

    // Compactar línea (mover fichas hacia un lado)
    private Ficha[] compactar(Ficha[] linea) {
        Ficha[] nueva = new Ficha[linea.length];
        int idx = 0;
        for (Ficha f : linea) {
            if (f != null) {
                nueva[idx++] = f;
            }
        }
        return nueva;
    }

    // Combinar fichas iguales y registrar fusiones
    private Ficha[] combinarLinea(Ficha[] linea, int filaOColumna, Direccion dir) {
        for (int i = 0; i < linea.length - 1; i++) {
            if (linea[i] != null && linea[i + 1] != null &&
                    linea[i].getValor() == linea[i + 1].getValor()) {

                linea[i].setValor(linea[i].getValor() * 2);
                linea[i + 1] = null;
                puntaje += linea[i].getValor();

                // Registrar posición fusionada
                int x, y;
                switch (dir) {
                    case IZQUIERDA -> { x = i; y = filaOColumna; }
                    case DERECHA -> { x = celdas.length - 1 - i; y = filaOColumna; }
                    case ARRIBA -> { x = filaOColumna; y = i; }
                    default -> { x = filaOColumna; y = celdas.length - 1 - i; }
                }
                celdasFusionadas.add(new Point(x, y));
            }
        }
        return compactar(linea);
    }

    // Insertar línea procesada en el tablero
    private boolean insertarLinea(Ficha[] linea, int index, Direccion dir) {
        boolean cambiado = false;
        for (int i = 0; i < celdas.length; i++) {
            Ficha actual;
            switch (dir) {
                case IZQUIERDA -> actual = celdas[index][i];
                case DERECHA -> actual = celdas[index][celdas.length - 1 - i];
                case ARRIBA -> actual = celdas[i][index];
                default -> actual = celdas[celdas.length - 1 - i][index];
            }
            if (actual != linea[i]) {
                cambiado = true;
            }
            switch (dir) {
                case IZQUIERDA -> celdas[index][i] = linea[i];
                case DERECHA -> celdas[index][celdas.length - 1 - i] = linea[i];
                case ARRIBA -> celdas[i][index] = linea[i];
                default -> celdas[celdas.length - 1 - i][index] = linea[i];
            }
        }
        return cambiado;
    }

    // Verificar si el tablero está bloqueado (sin movimientos posibles)
    public boolean estaBloqueado() {
        for (int i = 0; i < celdas.length; i++) {
            for (int j = 0; j < celdas.length; j++) {
                // Espacio vacío -> no está bloqueado
                if (celdas[i][j] == null) return false;

                // Comparar con derecha
                if (j < celdas.length - 1 && celdas[i][j + 1] != null &&
                        celdas[i][j].getValor() == celdas[i][j + 1].getValor()) return false;

                // Comparar con abajo
                if (i < celdas.length - 1 && celdas[i + 1][j] != null &&
                        celdas[i][j].getValor() == celdas[i + 1][j].getValor()) return false;
            }
        }
        return true;
    }

    // Mostrar tablero en consola
    public void mostrarTablero() {
        System.out.println("=== TABLERO ===");
        for (int i = 0; i < celdas.length; i++) {
            for (int j = 0; j < celdas[i].length; j++) {
                if (celdas[i][j] != null) {
                    System.out.print(celdas[i][j].getValor() + "\t");
                } else {
                    System.out.print(".\t");
                }
            }
            System.out.println();
        }
        System.out.println("Puntaje: " + puntaje);
        System.out.println("================");
    }
}
