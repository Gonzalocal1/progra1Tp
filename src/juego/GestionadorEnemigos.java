package juego;

import entorno.Entorno;

//Clase
public class GestionadorEnemigos {
	private Enemigo[] enemigos;
	private Entorno entorno;
	
	//Constructor
	public GestionadorEnemigos(Entorno entorno) {
		this.enemigos = null;
		this.entorno = entorno;
	}
	
	//Metodo1
	public void inicializarEnemigos(int numero) {
		enemigos = new Enemigo[numero];
	}

	//Metodo2
	private int contarEnemigos() {
	    int cantidad = 0;
	    for(int i = 0; i < enemigos.length; i++) {
	        if(enemigos[i] != null) {
	            cantidad++;
	        }
	    }
	    return cantidad;
	}

	//Metodo3
	public void crearEnemigo() {
	    for(int i = 0; i < enemigos.length; i++) {
	        if(enemigos[i] == null) {
	            boolean izquierda = Math.random() < 0.5;
	            boolean esFuerte = Math.random() < 0.3;
	            double x;
	            if(izquierda) {
	                x = -30;
	            }
	            else {
	                x = entorno.ancho() + 30;
	            }
	            double y = 100 + Math.random() * 400;
	            enemigos[i] = new Enemigo(x, y, izquierda, esFuerte, entorno);
	            break;
	        }
	    }
	}

	public void mantenerEnemigos() {
	    while(contarEnemigos() < 3) {
	        crearEnemigo();
	    }
	}
	private void crearVida(Enemigo enemigo, GestionadorDeItems items) {
		if (Math.random() < 0.9) {
			items.crearVida(enemigo.getX(),enemigo.getY());
		}
	}

	public boolean actualizarEnemigos( Princesa princesa, Proyectil proyectil,GestionadorDeItems items) {

		boolean proyectilDebeMorir = false;
		
	    for(int i = 0; i < enemigos.length; i++) {

	        if(enemigos[i] != null) {
	            enemigos[i].mover();
	            enemigos[i].actualizarAnimacion();
	            enemigos[i].dibujar();
	            
	            if(enemigos[i].colisionaCon(proyectil)) {
	            	crearVida(enemigos[i], items);
	                enemigos[i].setColisionoTrue();;
	                proyectilDebeMorir = true;
	            }

	            if(enemigos[i].colisionaCon(princesa)) {
	                princesa.perderVida();
	                enemigos[i].setColisionoTrue();;
	                
	            }

	            if(enemigos[i].fueraDePantalla() || enemigos[i].isColisiono()) {
	                enemigos[i] = null;
	            }
	        }
	    }
	    return proyectilDebeMorir;
	}

	//Metodo6
	public void limpiarEnemigos() {
		for (int i = 0; i < enemigos.length; i++) {
			enemigos[i] = null;
		}
	}

}
