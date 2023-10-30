package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;


public class Plantas {
	/* Variables de instancia*/
	double x;
	double y;
	double ancho;
	double alto;
	int direccion = 1;
	double escala;
	Image img;
	boolean colision;
	Image img1;
	Image img2;
	Image img3;
	Image img4;
	
	boolean estaViva;
	boolean ataca;
	private int contadorAtaque;

	
	public Plantas(double x, double y, double e, int d) {
		
		this.x = x;
		this.y = y;
		this.escala=e;
		this.direccion=d;
		this.estaViva = true;
		this.ataca = false;
		contadorAtaque = 0;
		colision = false;
		
		//plantas horizontal
		img1 = Herramientas.cargarImagen("plantaDer.png");
		img2 = Herramientas.cargarImagen("plantaIzq.png");
		//plantas vertical
		img3 = Herramientas.cargarImagen("plantaArr.png");
		img4 = Herramientas.cargarImagen("plantaAb.png");
		
		this.ancho=img1.getWidth(null)*this.escala;
		this.alto=img1.getHeight(null)*this.escala;
		
		this.ancho=img2.getWidth(null)*this.escala;
		this.alto=img2.getHeight(null)*this.escala;
		
		this.ancho=img3.getWidth(null)*this.escala;
		this.alto=img3.getHeight(null)*this.escala;
		
		this.ancho=img4.getWidth(null)*this.escala;
		this.alto=img4.getHeight(null)*this.escala;
		
		img = img1;
		
		System.out.println("ancho "+this.ancho+"  alto "+this.alto);
	}

	public boolean estaAtacando() {
        return this.ataca;
    }

    public void empezarAtaque() {
        this.ataca = true;
    }

    public void detenerAtaque() {
        this.ataca = false;
    }

	public void moverAdelante(double speed, boolean isHorizontal) {
	    if (isHorizontal) {
	        this.x += direccion * speed;
	    } else {
	        this.y += direccion * speed;
	    }
	}

	public boolean puedeLanzarBola() {
		return contadorAtaque >= 9000; 
	}
	
	public void reiniciarContadorAtaque() {
		contadorAtaque = 0;
	}
	

	public void detectarColisionBordes(Entorno entorno, int anchoPantalla, int altoPantalla) {
		if (x + ancho / 2 >= anchoPantalla) {
	        this.img=img2;
	        direccion = -direccion;
	    } else if (x - ancho / 2 <= 0) {
	    	this.img=img1;
	        direccion = -direccion;
	    }
	    if (y + alto / 2 >= altoPantalla) {
	    	this.img=img3;
	        direccion = -direccion;
	    } else if (y - alto / 2 <= 0) {
	    	this.img=img4;
	        direccion = -direccion;
	    }
    }
	
	public void dibujarse(Entorno entorno)
	{
		entorno.dibujarImagen(img, this.x, this.y, 0, this.escala);
		contadorAtaque++;
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
