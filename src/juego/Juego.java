package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	Layka perrita;
	Manzanas[] manzanitas;
	Plantas[] plantas;
	Autos[] autos;
	Spawn[] s;
	Spawn[] sA;
	BolasDeFuego[] bolasDeFuego;

	double speed = 1;
	int cantPlantas = 6;
	int cantAutos = 3;
	private int puntos = 0;
	private int tiempoPuntos = 0;
	int contador = 0;
	int cont = 0;
	boolean disparando = false;
	boolean vivo = true;
	int muertes = 0;

	Image fondo = Herramientas.cargarImagen("calles.png");
	Image imgFondoMuerto = Herramientas.cargarImagen("perdi.png");

	// ************************** */

	Image fondoMenu = Herramientas.cargarImagen("FondoMenu.png");

	private static final int ESTADO_MENU = 0;
	private static final int ESTADO_JUEGO = 1;

	private int estadoActual;

	private Boton botonJugar;
	private Boton botonSalir;
	private int selectedButton = 0;
	private boolean keyEnabled = true;

	private int tiempo=90;
	// ************************** */

	// Variables y métodos propios de cada grupo
	// ...

	Juego() {
		this.entorno = new Entorno(this, "Plantas Invasoras - Grupo ... - v1", 800, 600);
		this.estadoActual = ESTADO_MENU; // Establece el estado inicial como el menú
		// ...
		perrita = new Layka(400, 545);
		manzanitas = new Manzanas[6];

		plantas = new Plantas[cantPlantas];
		s = new Spawn[cantPlantas];
		generarSpawnsP(s);

		bolasDeFuego = new BolasDeFuego[cantPlantas];

		autos = new Autos[cantAutos];
		sA = new Spawn[cantAutos];
		generarSpawnsA(sA);

		for (int i = 0; i < autos.length; i++) {
			autos[i] = new Autos(sA[i].x, sA[i].y, 0.03, 1);
		}

		generarPlantas();

		for (int i = 0; i < manzanitas.length; i++) {
			if (i < 3) {
				manzanitas[i] = new Manzanas(166 + (234 * i), 170, 1);
			} else {

				manzanitas[i] = new Manzanas(166 + (234 * (i - 3)), 427, 1);
			}
		}

		botonJugar = new Boton(120, 300, 150, 50, "Jugar");
		botonSalir = new Boton(120, 400, 150, 50, "Salir");

		// Inicia el juego!
		this.entorno.iniciar();
	}

	private void generarPlantas() {
		for (int i = 0; i < plantas.length; i++) {
			plantas[i] = new Plantas(s[i].x, s[i].y, 0.02, 1);
			bolasDeFuego[i] = new BolasDeFuego(plantas[i].x, plantas[i].y, 0.02, 1);
		}
	}

	private void respawnPlantas(int i) {
		plantas[i] = new Plantas(s[i].x, s[i].y, 0.02, 1);
		bolasDeFuego[i] = new BolasDeFuego(plantas[i].x, plantas[i].y, 0.02, 1);
	}

	private void generarSpawnsA(Spawn[] s) {
		// TODO Auto-generated method stub
		// s[0]=new Spawn(70,540); // Esquina Abajo izquierda superior
		s[0] = new Spawn(777, 280); // Medio Abajo derecha

		s[1] = new Spawn(780, 25); // Esquina arriba derecha superior

		s[2] = new Spawn(300, 575); // Medio abajo sur
	}

	private void generarSpawnsP(Spawn[] s) {
		// TODO Auto-generated method stub

		s[0] = new Spawn(70, 315); // Medio izquierda oeste

		s[1] = new Spawn(70, 18); // Esquina arriba izquierda inferior

		s[2] = new Spawn(500, 60); // Medio derecha norte

		s[3] = new Spawn(775, 577); // Esquina abajo derecha inferior

		s[4] = new Spawn(730, 60);// Esquina Arriba derecha inferior

		s[5] = new Spawn(30, 577); // Esquina Abajo izquierda Inferior

		// s[6]=new Spawn(30,18); // Esquina Arriba izquierda Superior

		// s[7]=new Spawn(730,540); //Esquina Abajo derecha superior

	}

	public void tick() {
		switch (estadoActual) {
			case ESTADO_MENU:
				entorno.dibujarImagen(fondoMenu, 400, 300, 0);
				// Control con teclado en el menú
				if (entorno.estaPresionada(entorno.TECLA_ARRIBA) && keyEnabled) {
					selectedButton = (selectedButton == 0) ? 1 : 0;
					keyEnabled = false;
				} else if (entorno.estaPresionada(entorno.TECLA_ABAJO) && keyEnabled) {
					selectedButton = (selectedButton == 1) ? 0 : 1;
					keyEnabled = false;
				} else if (!entorno.estaPresionada(entorno.TECLA_ARRIBA)
						&& !entorno.estaPresionada(entorno.TECLA_ABAJO)) {
					keyEnabled = true;
				}

				// Lógica para cambiar de estado al presionar "Enter"
				if (entorno.estaPresionada(entorno.TECLA_ENTER)) {
					if (selectedButton == 0) {
						estadoActual = ESTADO_JUEGO;
					} else if (selectedButton == 1) {
						System.exit(0);
					}
				}

				// Dibujar botones
				botonJugar.setActivo(selectedButton == 0);
				botonSalir.setActivo(selectedButton == 1);
				botonJugar.dibujar(entorno);
				botonSalir.dibujar(entorno);

				break;

			case ESTADO_JUEGO:
				if (vivo) {
					entorno.dibujarImagen(fondo, 400, 300, 0);

					startLayka();

					for (int i = 0; i < manzanitas.length; i++) {
						manzanitas[i].dibujarse(this.entorno);
					}

					perrita.dibujarse(this.entorno);

					if (perrita.disparo != null && perrita.disparo.isActivo()) {
						perrita.disparo.dibujar(this.entorno);
						perrita.disparo.mover();
					}

					for (int i = 0; i < autos.length; i++) {
						if (autos[i].estaActivo) {
							autos[i].dibujarse(this.entorno);
							autos[i].detectarColisionBordes(this.entorno, entorno.ancho(), entorno.alto());

							if (i == 0 || i == 1) {
								autos[i].moverAdelante(speed + 0.5, true); // DERECHA
							} else {
								autos[i].moverAdelante(speed + 1.5, false); // ABAJO
							}

						}
					}

					for (int i = 0; i < plantas.length; i++) {
						if (plantas[i].estaViva) {
							if (plantas[i].puedeLanzarBola()) {
								plantas[i].empezarAtaque();
							}
							// Mueve la planta siempre
							if (i == 0 || i == 4 || i == 5) {
								plantas[i].moverAdelante(speed, true); // DERECHA
							} else {
								plantas[i].moverAdelante(speed + 0.5, false); // ABAJO
							}

							// Verifica si la planta ataca o debe comenzar su ataque
							if (plantas[i].puedeLanzarBola() && plantas[i].estaAtacando()) {
								// La planta está atacando, así que mueve la bola de fuego y dibújala
								bolasDeFuego[i].dibujarse(this.entorno);
								bolasDeFuego[i].moverAdelante(speed + 1, i == 0 || i == 4 || i == 5);
								plantas[i].reiniciarContadorAtaque();
								// El último argumento es true si la planta se mueve hacia la derecha (i == 0,
								// 4, o 5)
							} else {
								// La planta no está atacando, verifica si debe comenzar un nuevo ataque
								if (!plantas[i].estaAtacando()) {
									bolasDeFuego[i].x = plantas[i].x;
									bolasDeFuego[i].y = plantas[i].y;
								}
							}

							// Dibuja la planta
							plantas[i].dibujarse(this.entorno);

							// Detecta colisiones con los bordes
							plantas[i].detectarColisionBordes(this.entorno, entorno.ancho(), entorno.alto());
						} else if (!plantas[i].estaViva && muertes % 2 == 0) {
							respawnPlantas(i);
						}
					}

					administrarColisiones();
					entorno.cambiarFont("Arial", 18, Color.white);
					entorno.escribirTexto("posicion en x:" + perrita.x, 600, 50);
					entorno.escribirTexto("posicion en y:" + perrita.y, 600, 100);
				} else {
					entorno.dibujarImagen(imgFondoMuerto, 400, 300, 0);
					entorno.cambiarFont("Arial", 60, Color.GREEN);
					entorno.escribirTexto("Cantidad de puntos:" + intToString(puntos), 70, 300);
				}

				break;

			
		}
	}

	private void startLayka() {
		if (entorno.estaPresionada(entorno.TECLA_DERECHA) && restriccionm(manzanitas, perrita) != 1) {
			perrita.mover(1, this.entorno);
		}

		if (entorno.estaPresionada(entorno.TECLA_ARRIBA) && restriccionm(manzanitas, perrita) != 0) {
			perrita.mover(0, this.entorno);
		}

		if (entorno.estaPresionada(entorno.TECLA_ABAJO) && restriccionm(manzanitas, perrita) != 2) {
			perrita.mover(2, this.entorno);
		}

		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && restriccionm(manzanitas, perrita) != 3) {
			perrita.mover(3, this.entorno);

		}
		if (entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
			perrita.disparar();
		}

		if (perrita.disparo != null && !estaDentroPantalla(perrita.disparo.getX(), perrita.disparo.getY())) {
			perrita.reiniciarDisparo();
		}
	}

	public void administrarColisiones() {
		// Código de administración de colisiones
		for (int i = 0; i < plantas.length; i++) {
			if (plantas[i].estaViva) {
				if (perrita.disparo != null && plantas[i].estaViva && perrita.disparo.isActivo() && colisionar(
						perrita.disparo.getX(), perrita.disparo.getY(), plantas[i].getX(), plantas[i].getY(), 20)) {
					muertes++;
					puntos+=50;
					plantas[i].estaViva = false;
					bolasDeFuego[i].estaActiva = false;
					perrita.reiniciarDisparo();
					System.out.println("Colisión: Disparo de la perrita y planta " + i);
				}

				if (plantas[i].estaViva) {
					// Plantas que están vivas pueden atacar
					if (!plantas[i].ataca) {
						// Si la planta no está atacando, comienza su ataque
						bolasDeFuego[i].x = plantas[i].x;
						bolasDeFuego[i].y = plantas[i].y;
					} else {
						// Plantas que están atacando pueden continuar su ataque
						bolasDeFuego[i].dibujarse(this.entorno);
						bolasDeFuego[i].moverAdelante(speed + 1, i == 0 || i == 4 || i == 5);
					}
				} else {
					// Si la planta no está viva, detén su ataque
					plantas[i].detenerAtaque();
					System.out.println("Planta " + i + " detuvo su ataque");
				}
			}
		}
		// Recorre las bolas de fuego y desactívalas si salen de la pantalla
		for (int i = 0; i < bolasDeFuego.length; i++) {
			if (plantas[i].estaAtacando()) {
				// Añade la lógica para detectar si la bola de fuego sale de la pantalla
				if (!estaDentroPantalla(bolasDeFuego[i].x, bolasDeFuego[i].y)) {
					plantas[i].detenerAtaque();
					System.out.println("Bola de fuego de Planta " + i + " se desactivó");
					if (!plantas[i].estaAtacando() && plantas[i].puedeLanzarBola()) {
						System.out.println("Bola de fuego de Planta " + i + " se volvio a activar");
						plantas[i].empezarAtaque();
						bolasDeFuego[i].estaActiva = true;
					}
				}
			}
		}
		for (int i = 0; i < bolasDeFuego.length; i++) {
			if (perrita.disparo != null && plantas[i].estaAtacando() && perrita.disparo.isActivo() && colisionar(

					perrita.disparo.getX(), perrita.disparo.getY(), bolasDeFuego[i].getX(), bolasDeFuego[i].getY(),
					20)) {

				plantas[i].detenerAtaque();
				System.out.println("Bola de fuego de Planta " + i + " se desactivó");

				if (!plantas[i].estaAtacando() && plantas[i].puedeLanzarBola()) {
					System.out.println("Bola de fuego de Planta " + i + " se volvio a activar");
					plantas[i].empezarAtaque();
					bolasDeFuego[i].estaActiva = true;
				}

				perrita.reiniciarDisparo();

			}
			if (plantas[i].estaViva && colisionar(perrita.x, perrita.y, plantas[i].getX(), plantas[i].getY(), 20)) {

				this.vivo = false;

			}
			if (plantas[i].estaAtacando()
					&& colisionar(perrita.x, perrita.y, bolasDeFuego[i].getX(), bolasDeFuego[i].getY(), 20)) {

				this.vivo = false;

			}
		}
		for (int i = 0; i < autos.length; i++) {
			if (autos[i].estaActivo) {
				for (int j = 0; j < bolasDeFuego.length; j++) {
					if (plantas[i].estaAtacando()) {
						if (bolasDeFuego[j].estaActiva && colisionar(autos[i].getX(), autos[i].getY(),
								bolasDeFuego[j].getX(), bolasDeFuego[j].getY(), 30)) {
							autos[i].estaActivo = false;
							bolasDeFuego[j].estaActiva = false;
						}
					}
				}
			}
		}

		for (int i = 0; i < autos.length; i++) {
			if (perrita.disparo != null && autos[i].estaActivo && perrita.disparo.isActivo() && colisionar(

					perrita.disparo.getX(), perrita.disparo.getY(), autos[i].getX(), autos[i].getY(), 30)) {

				perrita.reiniciarDisparo();

			}
			if (autos[i].estaActivo && colisionar(perrita.x, perrita.y, autos[i].getX(), autos[i].getY(), 30)) {

				this.vivo = false;

			}
			if (vivo) {
				tiempoPuntos++;

				if (tiempoPuntos >= 200) {
					puntos += 1;
					tiempo--;
					tiempoPuntos = 0;
				}

				entorno.cambiarFont("Serif", 18, Color.white);
				entorno.escribirTexto("Puntos: " + puntos, 20, 20);
				entorno.escribirTexto("Tiempo restante: " + tiempo, 330, 20);
			}
		}
	}

	private boolean colisionar(double x, double y, double x2, double y2, double dist) {
		return (x - x2) * (x - x2) + (y - y2) * (y - y2) < dist * dist;
	}

	private boolean estaDentroPantalla(double x, double y) {
		return x < this.entorno.ancho() && x > 0 && y < this.entorno.alto() && y > 0;
	}

	private int restriccionm(Manzanas[] m, Layka a) {
		for (int i = 0; i < m.length; i++) {
			if (restriccion(m[i], a) < 5) {
				return restriccion(m[i], a);
			}
		}
		return 5;
	}

	public int restriccion(Manzanas m, Layka a) {
		double zona1 = m.x - m.ancho / 2;
		double zona2 = m.y - m.alto / 2;
		double zona0 = m.y + m.alto / 2;
		double zona3 = m.x + m.ancho / 2;
		if (a.y > zona2 && a.y < zona0 && a.x > zona1 - 20 && a.x < zona3) {
			return 1;
		}
		if (a.y > zona2 && a.y < zona0 && a.x > zona1 && a.x < zona3 + 20) {
			return 3;
		}
		if (a.y > zona2 - 20 && a.y < zona0 && a.x > zona1 && a.x < zona3) {
			return 2;
		}
		if (a.y > zona2 && a.y < zona0 + 20 && a.x > zona1 && a.x < zona3) {
			return 0;
		}
		return 5;
	}
	public String intToString(int numero) {
		// Método 1: Usando String.valueOf()
		String resultado1 = String.valueOf(numero);
	
		return resultado1;  // O resultado2, dependiendo de tu preferencia
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
