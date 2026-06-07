package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

//Clase
public class Princesa {
	private double x;
    private double y;
    private double alto;
    private double ancho;
    private double velocidadX;
    private double velocidadY;
    private double limiteX;
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
    
    
    // Arreglo de Images para guardar la animación
    private Image[] spritesCaminata; 
    private int frameActual;
    private int timerAnimacion;

//Constructor
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
        this.limiteX = entorno.ancho()-200;
        this.vidas = 10;
        this.tiempoInvulnerable = 0;
        this.frameActual = 0;
        this.timerAnimacion = 0;

        // Cargamos los sprites individuales (asumiendo que recortaste 4 frames)
        this.spritesCaminata = new Image[3];
        this.spritesCaminata[0] = Herramientas.cargarImagen("reimu_caminar_0.png");
        this.spritesCaminata[1] = Herramientas.cargarImagen("reimu_caminar_1.png");
        this.spritesCaminata[2] = Herramientas.cargarImagen("reimu_caminar_2.png");
        //this.spritesCaminata[3] = Herramientas.cargarImagen("reimu_caminar_3.png");
    }

    public void actualizarAnimacion() {
        this.timerAnimacion++;
        
        // Cada 8 fotogramas del juego, cambiamos al siguiente sprite de Reimu
        if (this.timerAnimacion >= 15) { 
            this.frameActual++;
            this.timerAnimacion = 0; 
            
            if (this.frameActual >= this.spritesCaminata.length) {
                this.frameActual = 0; // Reinicia la animación en bucle
            }
        }
    }
	
    public void dibujarPrincesa(Entorno entorno) {
        // Obtenemos la imagen que toca mostrar en este fotograma
        Image imagenActual = this.spritesCaminata[this.frameActual];
        
        // Llamamos a tu método pasándole la imagen, la posición y el ángulo
        entorno.dibujarImagen(imagenActual, this.x, this.y, 0, 0.2);
        dibujarVidas();
    }

	
	private void chequearPiso() {
        if (this.enElSuelo == true) {
            this.velocidadY = 0;
            this.tiempoSaltando = 0;
        }
    }
	
	//Metodo2
	private void moverIzquierda() {
		if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			this.velocidadX = 7;
			this.x -= this.velocidadX; 
		}
	}
	//Metodo3
	private void moverDerecha() {
		if (entorno.estaPresionada(entorno.TECLA_DERECHA) && x < limiteX) {
			this.velocidadX = 7;
			this.x += this.velocidadX; 
		}
	}
	
	//Metodo4
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
	//Metodo5
	private void gravedad() {
		if (enElSuelo == false) {
            this.velocidadY += GRAVEDAD; // La gravedad aumenta la velocidad de caída
            if (this.velocidadY > CAIDA_MAXIMA) {
                this.velocidadY = CAIDA_MAXIMA; // Terminal velocity para que no atraviese bloques
            }
        }
        this.y += this.velocidadY; // Aplica el movimiento final en Y
	}
	//Metodo6
	public void moverPrincesa() {
		chequearPiso();
		moverDerecha();
		moverIzquierda();
		salto();
		gravedad();
	}
	
	

	//Metodo8
	public void perderVida() {
        if(this.tiempoInvulnerable == 0) {
            this.vidas--;
            this.tiempoInvulnerable = 60;
        }
    }
	
    
    
	//Getters y Setters
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

    

    public void actualizarInvulnerabilidad() {
        if(this.tiempoInvulnerable > 0) {
            this.tiempoInvulnerable--;
        }
    }
    
    public boolean estaMuerta() {
    	return this.vidas <= 0;
    }
    
    private void dibujarVidas() {

    	entorno.cambiarFont("Arial", 20, java.awt.Color.WHITE);

    	entorno.escribirTexto("VIDAS", 20, 30);

    	for(int i = 0; i < this.vidas; i++) {

    		entorno.dibujarRectangulo(30 + (i * 30),60,20,20,0,java.awt.Color.RED);
    	}
    }
}