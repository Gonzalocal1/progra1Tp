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
	    double platIzquierda = this.x - (this.ancho / 2);
	    double platDerecha = this.x + (this.ancho / 2);
	    double platArriba = this.y - (this.alto / 2);
	    double platAbajo = this.y + (this.alto / 2);
	    double princesaIzquierda = princesa.getX() - (princesa.getAncho() / 2);
	    double princesaDerecha = princesa.getX() + (princesa.getAncho() / 2);
	    double princesaArriba = princesa.getY() - (princesa.getAlto() / 2);
	    double princesaAbajo = princesa.getY() + (princesa.getAlto() / 2);
	    boolean hayInterseccion =
	            princesaDerecha > platIzquierda &&
	            princesaIzquierda < platDerecha &&
	            princesaAbajo > platArriba &&
	            princesaArriba < platAbajo;
	            if(hayInterseccion) {
	                double overlapX = Math.min(princesaDerecha - platIzquierda,platDerecha - princesaIzquierda);
	                double overlapY = Math.min(princesaAbajo - platArriba,platAbajo - princesaArriba);
	                if(overlapY < overlapX) {
	                    // Princesa arriba de la plataforma
	                    if(princesa.getY() < this.y) {
	                            princesa.setY(platArriba - (princesa.getAlto() / 2));
	                            princesa.setVelocidadY(0);
	                            princesa.setEnElSuelo(true);
	                            princesa.setUltimaPlataformaSegura(this);
	                    }
	                    else {
	                        // Golpeó desde abajo
	                        princesa.setY(platAbajo + (princesa.getAlto() / 2));
	                        princesa.setVelocidadY(0);
	                    }
	                }
	                else {
	                    // Colisión lateral
	                    if(princesa.getX() < this.x) {
	                        princesa.setX(platIzquierda - (princesa.getAncho() / 2));
	                    }
	                    else {
	                        princesa.setX(platDerecha + (princesa.getAncho() / 2));
	                    }
	                    princesa.setVelocidadX(0);
	                }
	            }

	            return hayInterseccion;
	 }
	            
	public void colisionaConVida(VidaExtra vida) {
		// 1. Bordes del piso/plataforma
		double platIzquierda = this.x - (this.ancho / 2);
		double platDerecha   = this.x + (this.ancho / 2);
		double platArriba    = this.y - (this.alto / 2);
		double platAbajo     = this.y + (this.alto / 2);

		// 2. Bordes del objeto que cae (la vida)
		double vidaIzquierda = vida.getX() - (vida.getAncho() / 2);
		double vidaDerecha   = vida.getX() + (vida.getAncho() / 2);
		double vidaArriba    = vida.getY() - (vida.getAlto() / 2);
		double vidaAbajo     = vida.getY() + (vida.getAlto() / 2);

		// 3. Verificación completa (Eje X Y Eje Y al mismo tiempo)
		boolean hayInterseccion = vidaDerecha > platIzquierda &&  // Choca por la izquierda
		                          vidaIzquierda < platDerecha &&  // Choca por la derecha
		                          vidaAbajo > platArriba &&       // Choca por arriba
		                          vidaArriba < platAbajo;         // Choca por abajo 
	    if(hayInterseccion) {
		    vida.setEnSuelo(true);
	    	vida.setVelocidadY(0);
	    	vida.setY(platArriba - (vida.getAlto() / 2));  
	    }
	}
	
	//Metodo3
	public void moverPlataforma(double camaraY) {
		x -= camaraY;
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
