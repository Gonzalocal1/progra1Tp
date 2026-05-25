package juego;

import entorno.Entorno;

public class Princesa {
	private double x;
	private double  y;
	private double  alto;
	private double  ancho;
	private double  velocidadX;
	private double velocidadY;

	private Entorno entorno;
	private boolean saltando;
	
	private boolean timer = true;
	private int sec;
	
	public Princesa(int x, int y, int alto, int ancho, Entorno entorno) {
		this.x = x;
		this.y = y;
		this.alto = alto;
		this.ancho = ancho;
		this.entorno = entorno;
		this.velocidadX = 7;
		this.velocidadY = 7;
	}
	
	public boolean contarSegundos(int segundos) {
		if (timer == true) {
			sec= (entorno.tiempo() + 600);
			timer = false;
		}
		if (entorno.tiempo() < sec) {
			return false;
		} else {
			timer = true;
			return true;
		}
		
	}
	
	private void moverIzquierda() {
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			this.x -= this.velocidadX; 
		}
	}
	
	private void moverDerecha() {
		if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			this.x += this.velocidadX; 
		}
	}
	
	private void salto() {
		if (entorno.estaPresionada(entorno.TECLA_ARRIBA) && saltando == false) {
		this.y -= this.velocidadY;}
		
		if (contarSegundos(3)) {
			saltando = true;
		}
		}

	
	public void moverPrincesa() {
		if (this.y >= entorno.alto() - (alto/2)) {
			saltando = false;
		}
		
		/*if (this.y < entorno.alto() - (alto/2)) {
			saltando = true;
		}
		*/
		moverDerecha();
		moverIzquierda();
		salto();
		
		if (saltando == true && this.y < entorno.alto() - (alto/2)) {
			y += 3.5;
		}
	}


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


	public double getVelocidadX() {
		return velocidadX;
	}


	public void setVelocidadX(double velocidadX) {
		this.velocidadX = velocidadX;
	}


	public double getVelocidadY() {
		return velocidadY;
	}


	public void setVelocidadY(double velocidadY) {
		this.velocidadY = velocidadY;
	}



	public Entorno getEntorno() {
		return entorno;
	}


	public void setEntorno(Entorno entorno) {
		this.entorno = entorno;
	}


	public boolean isEnAire() {
		return saltando;
	}


	public void setEnAire(boolean enAire) {
		this.saltando = enAire;
	}


	
	
	
	
	
	
	
}
