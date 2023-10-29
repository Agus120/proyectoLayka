package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;


public class BolasDeFuego {
	/* Variables de instancia*/
	double x;
	double y;
	double ancho;
	double alto;
	double direccion;
	double escala;
	Image img;
	boolean colision;
	boolean estaActiva;
	
	public BolasDeFuego(double x, double y, double e, int d) {
		
		this.x = x;
		this.y = y;
		this.escala=e;
		this.direccion=d;
		this.estaActiva = true;
		img = Herramientas.cargarImagen("bolaDeFuego.png");
		
	}
	public void moverAdelante(double speed, boolean isHorizontal) {
	    if (isHorizontal) {
	        this.x += direccion * speed;
	    } else {
	        this.y += direccion * speed;
	    }
	}
	public void dibujarse(Entorno entorno)
	{
		entorno.dibujarImagen(img, this.x, this.y, 0, this.escala);
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

	    public double getAncho() {
	        return ancho;
	    }

	    public void setAncho(double ancho) {
	        this.ancho = ancho;
	    }

	    public double getAlto() {
	        return alto;
	    }

	    public void setAlto(double alto) {
	        this.alto = alto;
	    }

	    public double getDireccion() {
	        return direccion;
	    }

	    public void setDireccion(double direccion) {
	        this.direccion = direccion;
	    }

	    public Image getImg() {
	        return img;
	    }

	    public void setImg(Image img) {
	        this.img = img;
	    }

	    public double getEscala() {
	        return escala;
	    }

	    public void setEscala(double escala) {
	        this.escala = escala;
	    }
}
