package modelo;

public class Ficha {
    private int valor;

    public Ficha(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean esIgual(Ficha otra) {
        return otra != null && this.valor == otra.getValor();
    }
}
