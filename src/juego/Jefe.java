package juego;

import java.awt.Color;

import entorno.Entorno;

public class Jefe {
	private double x;
	private double y;
	private double alto;
	private double ancho;
	private Entorno entorno;
	private double velocidad = 3;
	
	
	
	public Jefe(Entorno entorno) {
		this.entorno = entorno;
		this.x = entorno.ancho()/2;
		this.y = entorno.alto()/2;
		this.alto = 40;
		this.ancho = 40;
	}

	public void moverJefe() {
	    // Sumamos la velocidad a la posición X
	    this.x += velocidad;

	    // Si se pasa de cierto límite a la derecha, invertimos la velocidad
	    if (this.x > 600) { 
	        velocidad = -3; // Empieza a ir a la izquierda
	    }
	    
	    // Si se pasa de cierto límite a la izquierda, la volvemos a invertir
	    if (this.x < 200) { 
	        velocidad = 3; // Empieza a ir a la derecha
	    }
	}
	
	public void dibujarJefe() {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.RED);
	}

	/**
	 * @return el x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return el y
	 */
	public double getY() {
		return y;
	}
	
	
}


