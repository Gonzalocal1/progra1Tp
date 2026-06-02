package juego;
import java.util.Random;

import entorno.Entorno;
public class GestionadorPlataformas { 
	//Esta clase surgio por dos razones principales: no llenar el main con funciones de la clase plataforma.
	//Y para poder gestionar las colisiones de todas las plataformas simultaneamente.
	// Clase destinada a gestionar creacion y metodos de la clase plataforma.
	private Plataforma[] suelo;
	private Plataforma[] islas;
	
	public void crearPiso(int cantPlataformas, Entorno entorno) {
		Random random = new Random();
		int separacion = 20;
		int separacionPozo = 200;
		int altura = entorno.alto();
		int alto = 20;
		int ancho= 20;
		this.suelo = new Plataforma[cantPlataformas];
		
		int cantPozos = random.nextInt(1,3); // Creo un numero aleatorio de pozos de 1 a 2 TODO cuando nivel 1 este completo subir cantidad de pozos
		
		for(int i = 0; i < cantPlataformas; i++) {           // inicializo las plataformas
	        if(suelo[i] == null) {
	        	suelo[i] = new Plataforma(altura,alto,ancho,entorno);
	        }
	    }
		
		for(int i = 0; i < cantPozos;i++) {                 // defino Pozos al azar exceptuando las 10 primeras plataformas y la ultima
			int j = random.nextInt(10,suelo.length);
			suelo[j].setEsPozo(true);
		}
		
		for (int i = 1; i < cantPlataformas; i++) {         // asigna una separacion en funcion de si son pozos o no
			double platX = suelo[i-1].getX();
			if (!suelo[i].isEsPozo()) {
				suelo[i].setX(platX += separacion);
			} else {
				suelo[i].setX(platX += separacionPozo);
			}
		}
	}
	
	public void dibujarPlataformas(double camaraX) {
		for(int i = 0; i < suelo.length; i++) {

	        if(suelo[i] != null) {

	            suelo[i].dibujo(camaraX);
	            suelo[i].moverPlataforma(camaraX);

	        }
	    }
	}
	
	
	public void colisionesPlataformas(Princesa princesa) {
		int contadorIntersecciones = 0;
		for(int i = 0; i < suelo.length; i++) {
	        if(suelo[i] != null) {
	            if(suelo[i].ColisionConPrincesa(princesa)) {
	            	contadorIntersecciones++;
	            }
	            
	        }
	        if(islas[i] != null) {
	            if(islas[i].ColisionConPrincesa(princesa)) {
	            	contadorIntersecciones++;
	            }
	            
	        }
	    }
		//Si no hay colisiones la princesa comenzara a caer
		if (contadorIntersecciones == 0) {
        	princesa.setEnElSuelo(false);
        }
	}
	
	public double getUltimaPlat() {
		return suelo[suelo.length-1].getX();
	}
	
	public void limpiarPlataformas( ) {
		for (int i = 0;i < suelo.length;i++) {
			suelo[i] = null;
		}
	}
	public void crearPisoNivel2(int cantPlataformas, Entorno entorno) {
	    int separacion = 20; // Distancia entre el centro de una plataforma y la siguiente
	    int altura = entorno.alto(); // Posición en Y (el suelo)
	    int alto = 20;
	    int ancho = 20;
	    this.suelo = new Plataforma[cantPlataformas];
	    
	    // 1. Inicializamos la primera plataforma en el inicio de la pantalla 
	    if (cantPlataformas > 0) {
	        suelo[0] = new Plataforma(altura, alto, ancho, entorno);
	        suelo[0].setX(10); // Posición inicial X para la primera plataforma (mitad de su ancho)
	        suelo[0].setEsPozo(false); // Nos aseguramos de que no sea pozo
	    }
	    
	    // 2. Inicializamos y posicionamos el resto de las plataformas de forma corrida
	    for (int i = 1; i < cantPlataformas; i++) {
	        suelo[i] = new Plataforma(altura, alto, ancho, entorno);
	        suelo[i].setEsPozo(false); // Ninguna es pozo
	        
	        // Tomamos la X de la plataforma anterior y le sumamos la separación constante
	        double platXAnterior = suelo[i - 1].getX();
	        suelo[i].setX(platXAnterior + separacion);
	    }
	}
	
	
	public void crearIslas(int cantPlataformas, Entorno entorno) {
		Random random = new Random();
		int separacion = 20;
		int separacionIsla = 200;
		int[] alturas = {700,750,500};
		int alto = 20;
		int ancho= 20;
		this.islas = new Plataforma[cantPlataformas];
		
		for(int i = 0; i < cantPlataformas; i++) {           // inicializo las plataformas
	        int alturaRandom = random.nextInt(0,3);          // toma un indice aleatorio de las posibles alturas
			int altura = alturas[alturaRandom];
			if(islas[i] == null) {
	        	islas[i] = new Plataforma(altura,alto,ancho,entorno);
	        }
	    }
		for(int i = 0; i < cantPlataformas; i++) { 
			islas[i].setX(i * separacion);
		}
	}
	
	public void dibujarIslas(double camaraX) {
		for(int i = 0; i < islas.length; i++) {

	        if(islas[i] != null) {

	            islas[i].dibujo(camaraX);
	            islas[i].moverPlataforma(camaraX);

	        }
	    }
	}
	
	public void limpiarIslas( ) {
		for (int i = 0;i < islas.length;i++) {
			islas[i] = null;
		}
	}
	}

