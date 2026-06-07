package juego;

import java.awt.Color;

import entorno.Entorno;

public class Princesa {
	private double x;
    private double y;
    private double alto;
    private double ancho;
    private double velocidadX;
    private double velocidadY;
    private double limiteY;
    private int vidas;
    private int tiempoInvulnerable; //ESTO LO AGREGO XQ SI NO LOS MUCIELAGOS MATABAN DE UNA A LA PRINCESA
    
    // Constantes del movimiento
    private static final double FUERZA_SALTO = 6.0; // transforma cada velocidadY durante el salto tick
    private static final double GRAVEDAD = 0.5;     // gravedad que se acumula cada tick
    private static final double CAIDA_MAXIMA = 8.0; // velocidad terminal de caida

    private Entorno entorno;
    
    private double limitePiso;
    private boolean enElSuelo;  
    private int tiempoSaltando;
    private static final int MAX_TIEMPO_SALTO = 20;

	
	public Princesa(int x, int y, int alto, int ancho, Entorno entorno) {
		this.x = x;
		this.y = y;
		this.alto = alto;
		this.ancho = ancho;
		this.entorno = entorno;
		this.velocidadX = 0;
		this.velocidadY = 0;
		this.enElSuelo = false;
        this.tiempoSaltando = 0;
        this.limiteY = entorno.ancho()-200;
        this.vidas = 3;
        this.tiempoInvulnerable = 0;
	}
	
	
	private void chequearPiso() {
        if (this.enElSuelo == true) {
            this.velocidadY = 0;
            this.tiempoSaltando = 0;
        }
    }
	
	
	private void moverIzquierda() {
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			this.velocidadX = 7;
			this.x -= this.velocidadX; 
		}
	}
	
	private void moverDerecha() {
		if (entorno.estaPresionada(entorno.TECLA_DERECHA) && x < limiteY) {
			this.velocidadX = 7;
			this.x += this.velocidadX; 
		}
	}
	
	
	
	private void salto() {
		// Inicio del salto: está en el suelo y presiona arriba
        if (entorno.estaPresionada(entorno.TECLA_ARRIBA) && enElSuelo) {
            enElSuelo = false;
            this.velocidadY = -FUERZA_SALTO; // Impulso inicial hacia arriba
        }
        
        // Salto variable: si mantiene presionado, el salto es más alto (estilo Mario)
        if (entorno.estaPresionada(entorno.TECLA_ARRIBA) && !enElSuelo && tiempoSaltando < MAX_TIEMPO_SALTO) {
            this.velocidadY = -FUERZA_SALTO; 
            tiempoSaltando++;
        }
        
        // Si suelta el botón antes de tiempo, corta el impulso ascendente
        if (entorno.seLevanto(entorno.TECLA_ARRIBA) && this.velocidadY < 0) {
            tiempoSaltando = MAX_TIEMPO_SALTO; // Forza a que empiece a caer
        }
	}

	private void gravedad() {
		if (enElSuelo == false) {
            this.velocidadY += GRAVEDAD; // La gravedad aumenta la velocidad de caída
            if (this.velocidadY > CAIDA_MAXIMA) {
                this.velocidadY = CAIDA_MAXIMA; // Terminal velocity para que no atraviese bloques
            }
        }
        this.y += this.velocidadY; // Aplica el movimiento final en Y
	}

	public void moverPrincesa() {
		chequearPiso();
		moverDerecha();
		moverIzquierda();
		salto();
		gravedad();
	}
	
	public void dibujarPrincesa() {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.RED);
	}

	// --- GETTERS Y SETTERS ---
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }

    public double getY() { return y; }
    public void setY(double y) { this.y = y; }

    public double getAlto() { return alto; }
    public void setAlto(double alto) { this.alto = alto; }

    public double getAncho() { return ancho; }
    public void setAncho(double ancho) { this.ancho = ancho; }

    public double getVelocidadX() { return velocidadX; }
    public void setVelocidadX(double velocidadX) { this.velocidadX = velocidadX; }

    public double getVelocidadY() { return velocidadY; }
    public void setVelocidadY(double velocidadY) { this.velocidadY = velocidadY; }

    public boolean isEnElSuelo() { return enElSuelo; }
    public void setEnElSuelo(boolean enElSuelo) { this.enElSuelo = enElSuelo; }
    
    public double getLimitePiso() { return limitePiso; }
    public void setLimitePiso(double limitePiso) { this.limitePiso = limitePiso; }
    
    public int getVidas() {
    	return this.vidas;
    }
    
    public void ganarVida() {
        if(this.vidas < 5) {
            this.vidas++;
        }
    }

    public void perderVida() {
        if(this.tiempoInvulnerable == 0) {
            this.vidas--;
            this.tiempoInvulnerable = 60;
        }
    }
    
    public void actualizarInvulnerabilidad() {
        if(this.tiempoInvulnerable > 0) {
            this.tiempoInvulnerable--;
        }
    }
    
    public boolean estaMuerta() {
    	return this.vidas <= 0;
    }
}