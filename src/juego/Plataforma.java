package juego;

import java.awt.Color;
import entorno.Entorno;

//Clase
public class Plataforma {
	private double x;
	private double  y;
	private double  alto;
	private double  ancho;
	private Entorno entorno;
	
	private boolean esPozo;
	
//Constructor
	public Plataforma(double y, double alto, double ancho, Entorno entorno) {
		this.x = 0; //El gestionador luego se encarga de asignarles un x
		this.y = y;
		this.alto = alto;
		this.ancho = ancho;
		this.entorno = entorno;
		this.esPozo = false;
	}
	
//Metodos
	//Metodo1 
	public void dibujo() {
		entorno.dibujarRectangulo(x, y, ancho, alto, 0, Color.blue);
	}
	
	//Metodo2
	public boolean ColisionConPrincesa(Princesa princesa) {
	    // 1. Calculamos los bordes de la plataforma (basado en el CENTRO)
	    double platIzquierda = this.x - (this.ancho / 2);
	    double platDerecha   = this.x + (this.ancho / 2);
	    double platArriba    = this.y - (this.alto / 2);
	    double platAbajo     = this.y + (this.alto / 2);

	    // 2. Calculamos los bordes de la Princesa (basado en su CENTRO)
	    double princesaIzquierda = princesa.getX() - (princesa.getAncho() / 2);
	    double princesaDerecha   = princesa.getX() + (princesa.getAncho() / 2);
	    double princesaArriba    = princesa.getY() - (princesa.getAlto() / 2);
	    double princesaAbajo     = princesa.getY() + (princesa.getAlto() / 2);

	    // 3. Verificamos si los rectángulos se superponen
	    boolean hayInterseccion = princesaDerecha > platIzquierda &&
	                              princesaIzquierda < platDerecha &&
	                              princesaAbajo > platArriba &&
	                              princesaArriba < platAbajo;

	    if (hayInterseccion) {
	        // 4. Medimos qué tan profundo se metieron para saber de qué lado fue el choque
	        double overlapX = Math.min(princesaDerecha - platIzquierda, platDerecha - princesaIzquierda);
	        double overlapY = Math.min(princesaAbajo - platArriba, platAbajo - princesaArriba);

	        // Si la superposición es menor en Y, la colisión es vertical (techo o suelo)
	        if (overlapY < overlapX) {
	            if (princesa.getY() < this.y) { // Princesa está por encima del centro de la plataforma -> Cayó arriba
	                princesa.setY(platArriba - (princesa.getAlto() / 2)); // Corregimos posición
	                princesa.setVelocidadY(0);
	                princesa.setEnElSuelo(true);
	                
	            } else { // Princesa está por debajo del centro de la plataforma -> Le pegó al techo
	                princesa.setY(platAbajo + (princesa.getAlto() / 2));
	                princesa.setVelocidadY(0);
	            }
	        } 
	        // Si la superposición es menor en X, la colisión es lateral (paredes)
	        else {
	            if (princesa.getX() < this.x) { // Viene de la izquierda
	                princesa.setX(platIzquierda - (princesa.getAncho() / 2));
	            } else { // Viene de la derecha
	                princesa.setX(platDerecha + (princesa.getAncho() / 2));
	            }
	            princesa.setVelocidadX(0); // Detiene el avance horizontal contra la pared
	        }
	    }
	    return hayInterseccion;
	}
	
	//Metodo3
	public void moverPlataforma(double camaraY) {
		x -= camaraY;
	}
	
	public boolean colisionaConVida(VidaExtra vida) {
	    double platIzq = this.x - this.ancho / 2;
	    double platDer = this.x + this.ancho / 2;
	    double platArriba = this.y - this.alto / 2;
	    double vidaIzq = vida.getX() - 10;
	    double vidaDer = vida.getX() + 10;
	    double vidaAbajo = vida.getY() + 10;
	    return vidaDer > platIzq
	        && vidaIzq < platDer
	        && vidaAbajo >= platArriba
	        && vidaAbajo <= platArriba + 10;
	}
	
//Getters y Setters para usar el codigo en otros archivos
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getAlto() {
		return alto;
	}
	public void setAlto(double alto) {
		this.alto = alto;
	}
	public double getAncho() {
		return ancho;
	}
	public void setAncho(double ancho) {
		this.ancho = ancho;
	}
	public boolean isEsPozo() {
		return esPozo;
	}
	public void setEsPozo(boolean esPozo) {
		this.esPozo = esPozo;
	}
}
