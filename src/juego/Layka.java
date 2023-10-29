package juego;

import java.awt.Image;
import java.awt.Rectangle;

import entorno.Entorno;
import entorno.Herramientas;

public class Layka {

	// Variables de instancia
	double x;
	double y;
	double ancho;
	double alto;
	Rectangle rlayka;
	int direccion;
	Image[] img;
	Disparo disparo;

	// constructor

	public Layka(double x, double y) {
		this.x = x;
		this.y = y;
		this.direccion = 1;
		this.img = new Image[4];
		for (int i = 0; i < img.length; i++) {

			img[i] = Herramientas.cargarImagen("layka"+i+".png");

		}

		this.ancho = img[0].getWidth(null) * 0.24;
		this.alto = img[0].getHeight(null) * 0.24;
		rlayka = new Rectangle((int) this.x, (int) this.y, (int) this.ancho, (int) this.alto);
	}
	
	public void generarDisparo() {
		if (disparo == null) {
			double xDisparo = x;
			double yDisparo = y;
			double velocidadX = 0;
			double velocidadY = 0;

			switch (direccion) {
			case 0:
				velocidadY = -1;
				break;
			case 1:
				velocidadX = 1;
				break;
			case 2:
				velocidadY = 1;
				break;
			case 3:
				velocidadX = -1;
				break;
			}

			disparo = new Disparo(xDisparo, yDisparo, velocidadX, velocidadY, 10);
		}
	}

	public void disparar() {
		if (this.disparo != null) {
			this.disparo.setActivo(true);
		} else {
			generarDisparo();
			this.disparo.setActivo(true);
		}
	}


	public void reiniciarDisparo() {
		disparo = null;
	}

	public void mover(int d, Entorno e) {

		this.direccion = d;
		double speed = 1.5;

		if (direccion == 0) {
			y-=speed;
		}
		if (direccion == 1) {
			x+=speed;
		}
		if (direccion == 2) {
			y+=speed;
		}
		if (direccion == 3) {
			x-=speed;
		}

		if (x > e.ancho()-15) {
			this.x = e.ancho()-15;
		}
		if (x < 15) {
			this.x = 15;
		}
		if (y > e.alto() - 15) {
			this.y = e.alto()-15;
		}
		if (y < 15) {
			this.y = 15;
		}

	}
	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(img[this.direccion], this.x, this.y, 0, 0.3);
	}
}