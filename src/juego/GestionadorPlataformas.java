package juego;
import entorno.Entorno;
public class GestionadorPlataformas { 
	//Esta clase surgio por dos razones principales: no llenar el main con funciones de la clase plataforma.
	//Y para poder gestionar las colisiones de todas las plataformas simultaneamente.
	// Clase destinada a gestionar creacion y metodos de la clase plataforma.
	private Plataforma[] plataformas;
	
	public void crearPlataformas(int cantPlataformas, Entorno entorno) {
		
		this.plataformas = new Plataforma[cantPlataformas];
		
		for(int i = 0; i < cantPlataformas; i++) {

	        if(plataformas[i] == null) {
	        	plataformas[i] = new Plataforma(21*i,entorno.alto(),20,20,entorno);
	        }
	    }
	}
	
	public void dibujarPlataformas() {
		for(int i = 0; i < plataformas.length; i++) {

	        if(plataformas[i] != null) {

	            plataformas[i].dibujo();

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
