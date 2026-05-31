package juego;

import java.awt.Color;

import entorno.Entorno;

public class JefeProyectil {
	private double x;
	private double y;
	private double alto;
	private double ancho;
	private Entorno entorno;
	private double grados;
	
	
	
	public JefeProyectil(double grados, Entorno entorno) {
		this.entorno = entorno;
		this.x = (entorno.ancho()/2);
		this.y = entorno.alto()/2 - 70;
		this.alto = 10;
		this.ancho = 10;
		this.grados = grados;
	}

	public void moverProyectil() {
		if (grados > 360) {
			grados = 1;
		}
		
		double radianes = Math.toRadians(grados);

		double resultado = Math.sin(radianes*5);
		double resultado2 = Math.cos(radianes*5);
		
		x += resultado2 *6;
		y += resultado *6;
		
		this.grados ++;
	}
	
	public void dibujarJefeProyectil() {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.ORANGE);
	}
	
}
