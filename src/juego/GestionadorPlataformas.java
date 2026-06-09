package juego;
import java.util.Random;
import entorno.Entorno;

//Esta clase surgio por dos razones principales: no llenar el main con funciones de la clase plataforma.
//Y para poder gestionar las colisiones de todas las plataformas simultaneamente.
//Clase
public class GestionadorPlataformas { 

	private Plataforma[] suelo;
	private Plataforma[] islas;
	private Plataforma[] islasSegundaCapa;

	public void crearPiso(int cantPlataformas, Entorno entorno) {
		Random random = new Random();
		int separacion = 20;
		int separacionPozo = 200;
		int altura = entorno.alto();
		int alto = 20;
		int ancho= 20;
		this.suelo = new Plataforma[cantPlataformas];
		int inicioPozo = 30;
		int finPozo = 51;
		
		for(int i = 0; i < cantPlataformas; i++) {           // inicializo las plataformas
	        if(suelo[i] == null) {
	        	suelo[i] = new Plataforma(altura,alto,ancho,entorno);
	        }
	    }
		
		while (finPozo < cantPlataformas - 20) {                 // recorro secciones de 20 en 20 y creo un pozo en cada una, evito las ultimas 20 ya que el castillo se encuentra ahi
			int j = random.nextInt(inicioPozo,finPozo);
			suelo[j].setEsPozo(true);
			inicioPozo += 20;
			finPozo+=20;
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
	
	public void crearIslas(Entorno entorno) {
	    Random random = new Random();
	    int separacion = 20;
	    int separacionIsla = 100;
	    int[] alturasPosibles = {350, 400, 500};
	    int alto = 20;
	    int ancho = 20;

	    // === PASO 1: SIMULACIÓN PARA CALCULAR EL TAMAÑO EXACTO ===
	    int xSimulado = 10;
	    int cantidadExacta = 0;
	    
	    while (xSimulado <= getUltimaPlat()) {
	        cantidadExacta++;
	        xSimulado += separacion;
	        
	        
	        if (cantidadExacta % 15 == 0) {
	            xSimulado += separacionIsla;
	        }
	    }

	    // === PASO 2: INICIALIZACIÓN SIN HUECOS ===
	    this.islas = new Plataforma[cantidadExacta];

	    // === PASO 3: GENERACIÓN REAL ===
	    int xActual = 10;
	    int alturaActual = alturasPosibles[random.nextInt(alturasPosibles.length)];

	    for (int i = 0; i < cantidadExacta; i++) {
	        islas[i] = new Plataforma(alturaActual, alto, ancho, entorno);
	        islas[i].setX(xActual);
	        xActual += separacion;
	        
	        if (i > 0 && i % 15 == 0) {
	            alturaActual = alturasPosibles[random.nextInt(alturasPosibles.length)];
	            xActual += separacionIsla;
	        }		
	    }
	}
	
	public void crearIslasSegundaCapa(Entorno entorno) {
	    Random random = new Random();
	    int separacion = 20;
	    int separacionIsla = 100;
	    int[] alturasPosibles = {150, 200, 250};
	    int alto = 20;
	    int ancho = 20;

	    // === PASO 1: SIMULACIÓN DE TAMAÑO EXACTO ===
	    int xSimulado = 200; // Arranca en 200 para desincronizar las islas
	    int cantidadExacta = 0;
	    
	    while (xSimulado <= getUltimaPlat()) {
	        cantidadExacta++;
	        xSimulado += separacion;
	        
	        if (cantidadExacta % 15 == 0) {
	            xSimulado += separacionIsla;
	        }
	    }

	    // === PASO 2: INICIALIZACIÓN SIN HUECOS ===
	    this.islasSegundaCapa = new Plataforma[cantidadExacta];

	    // === PASO 3: GENERACIÓN REAL ===
	    int xActual = 200;
	    int alturaActual = alturasPosibles[random.nextInt(alturasPosibles.length)];

	    for (int i = 0; i < cantidadExacta; i++) {
	        islasSegundaCapa[i] = new Plataforma(alturaActual, alto, ancho, entorno);
	        islasSegundaCapa[i].setX(xActual);
	        xActual += separacion;
	        
	        // CORRECCIÓN: 'i > 0' para evitar el salto molesto en el primer bloque
	        if (i > 0 && i % 15 == 0) {
	            alturaActual = alturasPosibles[random.nextInt(alturasPosibles.length)];
	            xActual += separacionIsla;
	        }
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

	public void colisionesPlataformas(Princesa princesa, GestionadorDeItems items) {
		VidaExtra[] vidas = items.getVidas();
		int contadorIntersecciones = 0;
		for(int i = 0; i < suelo.length; i++) {
	        if(suelo[i] != null) {
	            if(suelo[i].ColisionConPrincesa(princesa)) {
	            	contadorIntersecciones++;
	            }
	            for(int j = 0;j < vidas.length;j++) {
					if (vidas[j] != null) {
						suelo[i].colisionaConVida(vidas[j]);
					}
				}
	        }
	    }
		for(int i = 0; i < islas.length; i++) {
	        if(islas[i] != null) {
	            if(islas[i].ColisionConPrincesa(princesa)) {
	            	contadorIntersecciones++;
	            }
		        for(int j = 0;j < vidas.length;j++) {
					if (vidas[j] != null) {
						islas[i].colisionaConVida(vidas[j]);
					}
				}
	        }
		}
		
		for(int i = 0; i < islasSegundaCapa.length; i++) {
	        if(islasSegundaCapa[i] != null) {
	            if(islasSegundaCapa[i].ColisionConPrincesa(princesa)) {
	            	contadorIntersecciones++;
	            }
		        for(int j = 0;j < vidas.length;j++) {
					if (vidas[j] != null) {
						islasSegundaCapa[i].colisionaConVida(vidas[j]);
					}
				}
	        }
		}
		//Si no hay colisiones la princesa comenzara a caer
		if (contadorIntersecciones == 0) {
        	princesa.setEnElSuelo(false);
        }
	}
	
	public void colisionesPlataformasSoloSuelo(Princesa princesa, GestionadorDeItems items) { //pensado para el nivel 2 sin islas
		VidaExtra[] vidas = items.getVidas();
		int contadorIntersecciones = 0;
		for(int i = 0; i < suelo.length; i++) {
	        if(suelo[i] != null) {
	            if(suelo[i].ColisionConPrincesa(princesa)) {
	            	contadorIntersecciones++;
	            }
	            for(int j = 0;j < vidas.length;j++) {
					if (vidas[j] != null) {
						suelo[i].colisionaConVida(vidas[j]);
					}
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
	
	public void dibujarIslas(double camaraX) {
		for(int i = 0; i < islas.length; i++) {
	        if(islas[i] != null) {
	            islas[i].dibujo();
	            islas[i].moverPlataforma(camaraX);
	        }
	    }
		for(int i = 0; i < islasSegundaCapa.length; i++) {
	        if(islasSegundaCapa[i] != null) {
	        	islasSegundaCapa[i].dibujo();
	        	islasSegundaCapa[i].moverPlataforma(camaraX);
	        }
	    }
	}
	
	public void dibujarPlataformas(double camaraX) {
		for(int i = 0; i < suelo.length; i++) {

	        if(suelo[i] != null) {

	            suelo[i].dibujo();
	            suelo[i].moverPlataforma(camaraX);

	        }
	    }
	}
}

