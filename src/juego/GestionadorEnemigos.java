package juego;

import entorno.Entorno;

public class GestionadorEnemigos {
	private Enemigo[] enemigos;
	private Entorno entorno;
	
	
	
	
	public GestionadorEnemigos(Entorno entorno) {
		this.enemigos = null;
		this.entorno = entorno;
	}
	
	
	public void inicializarEnemigos(int numero) {
		enemigos = new Enemigo[numero];
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

	// CORRECCIÓN: 'public' para que Niveles.java lo pueda ejecutar
	public void mantenerEnemigos() {
	    while(contarEnemigos() < 3) {
	        crearEnemigo();
	    }
	}

	// CORRECCIÓN: 'public' para que Niveles.java lo pueda ejecutar
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

	// CORRECCIÓN: Método necesario para borrar los enemigos al cambiar de nivel
	public void limpiarEnemigos() {
		for (int i = 0; i < enemigos.length; i++) {
			enemigos[i] = null;
		}
	}

}
