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
	private Plataforma plataforma; 
	private Plataforma plataforma2;
	private Plataforma plataforma3;
	private Enemigo[] enemigos;
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		princesa = new Princesa(entorno.ancho()/2,200,20,20, entorno);
		plataforma = new Plataforma(entorno.ancho()/4,entorno.alto()-90,20,20,entorno);
		plataforma2 = new Plataforma(entorno.ancho()/4-40,entorno.alto()-90,20,20,entorno);
		plataforma3 = new Plataforma(entorno.ancho()/4+40,entorno.alto()-90,20,20,entorno);
		enemigos = new Enemigo[10];
		// Inicializar lo que haga falta para el juego

		// Inicia el juego!
		this.entorno.iniciar();
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
		entorno.dibujarRectangulo(princesa.getX(),princesa.getY(),princesa.getAncho(),princesa.getAlto(),0, Color.RED);
		plataforma.ColisionConPrincesa(princesa);
		plataforma2.ColisionConPrincesa(princesa);
		plataforma3.ColisionConPrincesa(princesa);
		princesa.moverPrincesa();
		plataforma.dibujo();
		plataforma2.dibujo();
		plataforma3.dibujo();
		actualizarEnemigos();
		mantenerEnemigos();
		// Procesamiento de un instante de tiempo
		// ...
		
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
