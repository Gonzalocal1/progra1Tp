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
	            double x;
	            if(izquierda) {
	                x = -30;
	            }
	            else {
	                x = entorno.ancho() + 30;
	            }
	            double y = 100 + Math.random() * 550;
	            enemigos[i] = new Enemigo(x, y, izquierda, entorno);
	            break;
	        }
	    }
	}
	//Metodo4
	public void mantenerEnemigos() {
	    while(contarEnemigos() < 3) {
	        crearEnemigo();
	    }
	}

	//Metodo5
	public void actualizarEnemigos(Princesa princesa) {

	    for(int i = 0; i < enemigos.length; i++) {

	        if(enemigos[i] != null) {

	            enemigos[i].mover();
	            enemigos[i].dibujar();

	            if(enemigos[i].colisionaCon(princesa)) {
	                princesa.perderVida();
	            }

	            if(enemigos[i].fueraDePantalla()) {
	                enemigos[i] = null;
	            }
	        }
	    }
	}

	//Metodo6
	public void limpiarEnemigos() {
		for (int i = 0; i < enemigos.length; i++) {
			enemigos[i] = null;
		}
	}

}
