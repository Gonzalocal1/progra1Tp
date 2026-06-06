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

	
	
	
	public JefeProyectil(double grados,double radio, Entorno entorno) {
		this.entorno = entorno;
		this.x = (entorno.ancho()/2);
		this.y = entorno.alto()/2 - 70;
		this.alto = 10;
		this.ancho = 10;
		this.grados = grados;
		this.radio = radio;
		this.radioRadio = 3;
		this.velocidad = 12;
	}
	

	
	public void moverDireccion(double vx, double vy) {
		x += vx * velocidad;
		y += vy * velocidad;
	}
	
	

	private void calcularAngulo(double centroX, double centroY) {
		double radianes = Math.toRadians(grados);

		this.x = centroX + Math.cos(radianes) * radio;
		this.y = centroY + Math.sin(radianes) * radio;
	}
	
	
	
	public void girarProyectil(double centroX, double centroY) {
		this.grados += 5;
		
		if (grados > 360) {
			grados = 1;
		}
		
		calcularAngulo(centroX,centroY);
		
	}
	
	public void animacionRadio(){
		this.radio += radioRadio;
		// Si se pasa de cierto límite a la derecha, invertimos la velocidad
	    if (this.radio > 200) { 
	        this.radioRadio = -3; // Empieza a ir a la izquierda
	    }
	    
	    // Si se pasa de cierto límite a la izquierda, la volvemos a invertir
	    if (this.radio < 40) { 
	    	this.radioRadio = 3; // Empieza a ir a la derecha
	    }
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
