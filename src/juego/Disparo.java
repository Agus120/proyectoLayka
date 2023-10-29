package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Disparo {
	private double x, y, velocidad, velocidadY, velocidadX, angulo;
	private boolean activo;
	private Image imagen;

	public Disparo(double x, double y, double velocidadX, double velocidadY, double velocidad) {
		this.x = x;
		this.y = y;
		this.velocidad=velocidad;
		this.velocidadX = velocidadX;
		this.velocidadY = velocidadY;
		this.activo = false;
		this.imagen = Herramientas.cargarImagen("ionDron.png");
		
		if (velocidadX==0 && velocidadY>0) {
			this.angulo=Math.PI;
		}
		else {
			if(velocidadX==0 && velocidadY<0) {
				this.angulo=0;
			}
			else {
				if(velocidadX<0&&velocidadY==0) {
					this.angulo=Math.PI/2;
				}
				else {
					this.angulo=(3*Math.PI)/2;
				}
			}
		}

	}
	
	public void dibujar(Entorno ent) {
		ent.dibujarImagen(imagen,x, y , angulo);
	}

	public void mover() {
		if (activo) {
			x += (velocidad*velocidadX);
			y += (velocidad*velocidadY);
		}
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getVelocidadX() {
		return velocidadX;
	}

	public void setVelocidadX(double velocidadX) {
		this.velocidadX = velocidadX;
	}

	public double getVelocidadY() {
		return velocidadY;
	}

	public void setVelocidadY(double velocidadY) {
		this.velocidadY = velocidadY;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
