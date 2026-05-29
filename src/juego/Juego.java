package juego;


import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	
	// Variables y métodos propios de cada grupo
	private Princesa princesa;
	private Enemigo[] enemigos;
	private GestionadorPlataformas plataformas;
	private Proyectil proyectil;
	private Castillo castillo;
	private double camaraY = 0;
	private double maxCamara = 4;

	
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		princesa = new Princesa(entorno.ancho()/2,200,20,20, entorno);
		plataformas = new GestionadorPlataformas();
		plataformas.crearPiso(300, entorno);
		enemigos = new Enemigo[10];
		this.proyectil = new Proyectil(600, entorno.alto() - 15);
		castillo = new Castillo(700, 300, "castillo.jpg", this.entorno);
		
		// Inicializar lo que haga falta para el juego

		// Inicia el juego!
		this.entorno.iniciar();
	}

		
	private void actualizarCamara(Princesa princesa) {
		if (princesa.getX() + 50 > 600 && entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			camaraY += 1;
		} else {
			camaraY = 0;
		}
		if (camaraY > maxCamara) {
			camaraY = maxCamara;
		}
	}
	
	
	
	
	
	// ESTO CUENTA LOS ENEMIGOS VIVOS
	private int contarEnemigos() {

	    int cantidad = 0;

	    for(int i = 0; i < enemigos.length; i++) {

	        if(enemigos[i] != null) {
	            cantidad++;
	        }
	    }

	    return cantidad;
	}


	// ESTO ES PARA CREAR UN ENEMIGO
	private void crearEnemigo() {

	    for(int i = 0; i < enemigos.length; i++) {

	        if(enemigos[i] == null) {

	            boolean izquierda = Math.random() < 0.5;

	            double x;

	            if(izquierda) {
	                x = -30;
	            }
	            else {
	                x = entorno.ancho() + 30;
	            }

	            double y = 100 + Math.random() * 300;

	            enemigos[i] = new Enemigo(x, y, izquierda, entorno);

	            break;
	        }
	    }
	}


	// ESTO ES PARA MANTENER SIEMPRE UN MINIMO DE 3 ENEMIGOS
	private void mantenerEnemigos() {

	    while(contarEnemigos() < 3) {

	        crearEnemigo();
	    }
	}


	private void actualizarEnemigos() {

	    for(int i = 0; i < enemigos.length; i++) {

	        if(enemigos[i] != null) {

	            enemigos[i].mover();

	            enemigos[i].dibujar();

	            // IMPORTANTISIMO:
	            // si sale de pantalla -> NULL
	            if(enemigos[i].fueraDePantalla()) {

	                enemigos[i] = null;
	            }
	        }
	    }
	}

	

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick()
	{
		actualizarCamara(princesa);
		entorno.dibujarRectangulo(princesa.getX(),princesa.getY(),princesa.getAncho(),princesa.getAlto(),0, Color.RED);
		plataformas.colisionesPlataformas(princesa);
		princesa.moverPrincesa();
		plataformas.dibujarPlataformas(camaraY);
		// --- LÓGICA DEL PROYECTIL RE CORTADA ---
		if (proyectil != null && !proyectil.disparo(princesa, entorno)) {
			proyectil = null;
		}
		actualizarEnemigos();
		mantenerEnemigos();
		castillo.dibujar();
		// Procesamiento de un instante de tiempo
		// ...
		
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
