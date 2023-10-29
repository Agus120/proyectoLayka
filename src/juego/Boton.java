package juego;

import java.awt.Color;
import entorno.Entorno;

public class Boton {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private String etiqueta;
    private boolean activo;

    public Boton(double x, double y, double ancho, double alto, String etiqueta) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.etiqueta = etiqueta;
        this.activo = false;
    }

    public void dibujar(Entorno entorno) {
        if (activo) {
            // Dibujar el botón de manera destacada si está activo
            entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.YELLOW);
            entorno.cambiarFont("Arial", 18, Color.BLACK);
            entorno.escribirTexto(this.etiqueta, this.x - 20, this.y + 5);
        } else {
            // Dibujar el botón normalmente
            entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.LIGHT_GRAY);
            entorno.cambiarFont("Arial", 18, Color.BLACK);
            entorno.escribirTexto(this.etiqueta, this.x - 20, this.y + 5);
        }
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean estaSobreBoton(double x, double y) {
        return x >= this.x && x <= this.x + this.ancho && y >= this.y && y <= this.y + this.alto;
    }
}
