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

	public void pseudoParry(Proyectil proyectil) {
        // Solo intentamos el parry si el proyectil fue disparado y está activo
        if (proyectil != null && proyectil.isDisparado()) {

            // Caja de colisión (AABB) entre el Jefe y el Proyectil
            boolean colision = (this.x - this.ancho/2 < proyectil.getX() + proyectil.getAncho()/2 &&
                                this.x + this.ancho/2 > proyectil.getX() - proyectil.getAncho()/2 &&
                                this.y - this.alto/2 < proyectil.getY() + proyectil.getAlto()/2 &&
                                this.y + this.alto/2 > proyectil.getY() - proyectil.getAlto()/2);

            if (colision) {
                // 1. Invertimos la dirección del proyectil multiplicando sus velocidades por -1
                proyectil.setVx(proyectil.getVx() * -1);
                proyectil.setVy(proyectil.getVy() * -1);

                // 2. Anti-Stuck: Desplazamos levemente el proyectil hacia afuera del jefe 
                // para que en el próximo frame no vuelva a colisionar y se quede titilando.
                if (proyectil.getX() < this.x) {
                    proyectil.setX(proyectil.getX() - 10); // Empuje a la izquierda
                } else {
                    proyectil.setX(proyectil.getX() + 10); // Empuje a la derecha
                }
            }
        }
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


