package juego;

import java.awt.Color;

import entorno.Entorno;

public class JefeProyectil {
	private double x;
	private double y;
	private double alto;
	private double ancho;
	private Entorno entorno;
	private double velocidad;
	private double grados;
	private double radio;
	private double radioRadio;
	private double vx;
    private double vy;

	
	
	
	public JefeProyectil(double grados,double radio, Entorno entorno) {
		this.entorno = entorno;
		this.x = (entorno.ancho()/2);
		this.y = entorno.alto()/2 - 70;
		this.alto = 10;
		this.ancho = 10;
		this.grados = grados;
		this.radio = radio;
		this.radioRadio = 3;
		this.velocidad = 10;
	}
	

	
	public void moverDireccionPrincesa() {
		x += vx * velocidad;
		y += vy * velocidad;
	}
	
	

	public void calcularAngulo(double centroX, double centroY) {
		double radianes = Math.toRadians(grados);

		this.x = centroX + Math.cos(radianes) * radio;
		this.y = centroY + Math.sin(radianes) * radio;
	}
	
	public void calcularAnguloPrincesa(Princesa princesa) {
		double deltaX = princesa.getX() - this.x;
        double deltaY = princesa.getY() - this.y;
        double distancia = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Control de seguridad para evitar división por cero
        if (distancia == 0) {
            distancia = 1;
            deltaX = 1;
        }

        // Descomposición vectorial de la velocidad
        this.vx = (deltaX / distancia) ;
        this.vy = (deltaY / distancia) ;
	}
	
	
	
	public void girarProyectil(double centroX, double centroY, double velodidadGiro) {
		this.grados += velodidadGiro;
		
		if (grados > 360) {
			grados = 1;
		}
		
		calcularAngulo(centroX,centroY);
		
	}
	
	
	public void animacionRadio(){
		this.radio += radioRadio;
		// Si se pasa de cierto límite, invertimos la velocidad
	    if (this.radio > 250) { 
	        this.radioRadio = -3; // Empieza a ir a la izquierda
	    }
	    
	    // Si se pasa de cierto límite hacia el centro, la volvemos a invertir
	    if (this.radio < 40) { 
	    	this.radioRadio = 3; // Empieza a ir a la derecha
	    }
	}
	
	public boolean seSalioDelMapa(Entorno entorno) {
        return (this.x > entorno.ancho() + 50 || this.x < -50 || this.y > entorno.alto() + 50 || this.y < -50);
    }
	
	public void dibujarJefeProyectil() {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.ORANGE);
	}
	
    public boolean colisionaCon(Princesa princesa) {

    	return
    		this.x - this.ancho/2 < princesa.getX() + princesa.getAncho()/2
    		&&
    		this.x + this.ancho/2 > princesa.getX() - princesa.getAncho()/2
    		&&
    		this.y - this.alto/2 < princesa.getY() + princesa.getAlto()/2
    		&&
    		this.y + this.alto/2 > princesa.getY() - princesa.getAlto()/2;
    }
    

}
