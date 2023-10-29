package juego;

import java.awt.Image;

import entorno.Herramientas;

public class Spawn {
	Image img;
	double x;
	double y;
	public Spawn(double x, double y){
		this.x = x;
		this.y = y;
		img = Herramientas.cargarImagen("cubo.png");
	}
}
