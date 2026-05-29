package juego;
import entorno.Entorno;
public class GestionadorPlataformas { 
	//Esta clase surgio por dos razones principales: no llenar el main con funciones de la clase plataforma.
	//Y para poder gestionar las colisiones de todas las plataformas simultaneamente.
	// Clase destinada a gestionar creacion y metodos de la clase plataforma.
	private Plataforma[] plataformas;
	
	public void crearPiso(int cantPlataformas, Entorno entorno) {
		int separacion = 20;
		int separacionPozo = 200;
		int altura = entorno.alto();
		int alto = 20;
		int ancho= 20;
		this.plataformas = new Plataforma[cantPlataformas];
		
		int cantPozos = (int) (Math.random() * 8) + 3; // Creo un numero aleatorio de pozos de 2 a 6
		
		for(int i = 0; i < cantPlataformas; i++) {           // inicializo las plataformas
	        if(plataformas[i] == null) {
	        	plataformas[i] = new Plataforma(altura,alto,ancho,entorno);
	        }
	    }
		
		for(int i = 0; i < cantPozos;i++) {                 // defino Pozos al azar
			int j = (int) (Math.random() * cantPlataformas-1) + 1;
			plataformas[j].setEsPozo(true);
		}
		
		for (int i = 1; i < cantPlataformas; i++) {         // asigna una separacion en funcion de si son pozos o no
			double platX = plataformas[i-1].getX();
			if (!plataformas[i].isEsPozo()) {
				plataformas[i].setX(platX += separacion);
			} else {
				plataformas[i].setX(platX += separacionPozo);
			}
		}
	}
	
	public void dibujarPlataformas(double camaraY) {
		for(int i = 0; i < plataformas.length; i++) {

	        if(plataformas[i] != null) {

	            plataformas[i].dibujo(camaraY);
	            plataformas[i].moverPlataforma(camaraY);

	        }
	    }
	}
	
	
	public void colisionesPlataformas(Princesa princesa) {
		int contadorIntersecciones = 0;
		for(int i = 0; i < plataformas.length; i++) {
	        if(plataformas[i] != null) {
	            if(plataformas[i].ColisionConPrincesa(princesa)) {
	            	contadorIntersecciones++;
	            }
	            
	        }
	    }
		//Si no hay colisiones la princesa comenzara a caer
		if (contadorIntersecciones == 0) {
        	princesa.setEnElSuelo(false);
        }
	}
	
	
	
}
