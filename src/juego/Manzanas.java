package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Manzanas {
	

			/* Variables de instancia*/
			double x;
			double y;
			double ancho;
			double alto;
			Image img;
			double escala;
			
			public Manzanas(double x, double y, double e) {
				
				this.x = x;
				this.y = y;
				this.escala=e;
				double aux = Math.random();
				img = Herramientas.cargarImagen(aux>0.6?"Cuadra1.png":"Cuadra2.png");
				this.ancho=img.getWidth(null)*this.escala;
				this.alto=img.getHeight(null)*this.escala;
				System.out.println("ancho "+this.ancho+"  alto "+this.alto);
			}
			
			public void dibujarse(Entorno entorno)
			{
				entorno.dibujarImagen(img, this.x, this.y, 0, this.escala);
			}
			
			
		}
