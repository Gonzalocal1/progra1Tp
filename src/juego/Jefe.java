package juego;

import java.awt.Color;
import java.util.Random;

import entorno.Entorno;

//Creacion de la clase
public class Jefe {
	private double x;
	private double y;
	private double alto;
	private double ancho;
	private Entorno entorno;
	private double velocidad = 3;
	
	private int vidas;
	private int timerAtaqueMaximo;
	private int timerAtaque;
	JefeProyectil[] jf; // contenedor de balas
	Princesa princesa;  // para calcular hacia donde disparar la balas
	private int altura; // para saber hacia que valor y se va a mover el jefe
	
	private int ataque; // para saber que ataque esta efectuando el jefe
	private int tiempoInvulnerable;
	
//Constructor
	public Jefe(Entorno entorno) {
		this.entorno = entorno;
		this.x = entorno.ancho()/2;
		this.y = entorno.alto()/2;
		this.alto = 40;
		this.ancho = 40;
		this.vidas = 10;
		this.timerAtaque = 0;
		this.ataque = 1;
		this.timerAtaqueMaximo = 0;
		this.tiempoInvulnerable = 0;
	}
	

	
	public void iniciarAtaque1(Princesa princesa) {
		this.ataque = 1;
		this.princesa = princesa;
		this.timerAtaqueMaximo = 1500;
		jf = new JefeProyectil[6];
		int grados = 0;
		for (int i = 0; i < jf.length; i++) {
			jf[i] = new JefeProyectil(grados, 40, entorno);
			grados += 60;
		}
	}
	
	private void iniciarAtaque2() {
		this.ataque = 2;
		int[] posiblesAlturasJefe = {200,400,155};
		Random r = new Random();
		altura = posiblesAlturasJefe[r.nextInt(0,posiblesAlturasJefe.length)];
	}
	
	private void actualizarAtaque1() {
		if (jf == null) return;
		for (int i = 0; i < jf.length; i++) {
			if (jf[i] != null) {
				jf[i].animacionRadio();
				jf[i].girarProyectil(x, y, 3);
				jf[i].dibujarJefeProyectil();
			
			
				if(jf[i].colisionaCon(princesa)) {
					princesa.perderVida();
					jf[i] = null;
				}
			
			}
			timerAtaque += 1;
			if (timerAtaque > timerAtaqueMaximo) {
				iniciarAtaque2();
				this.timerAtaque = 0;
			}
		}
		moverJefeDerechaIzq();
	}
	
	private void actualizarAtaque2() {
		if (jf == null) return;
		int contadorBalas = 0;
		
		moverJefeA(entorno.ancho()/2,altura);
		
		for (int i = 0; i < jf.length; i++) {
			if (jf[i] != null) {
				contadorBalas ++;
				timerAtaqueMaximo = i * 15 + 5;
				
				if (timerAtaque == timerAtaqueMaximo) {
					jf[i].calcularAnguloPrincesa(princesa);
				}
				
				if (timerAtaque > timerAtaqueMaximo) {
					jf[i].moverDireccionPrincesa();
				} else {
					jf[i].girarProyectil(x, y, -1);
				}
				
				jf[i].dibujarJefeProyectil();
			
			
				if(jf[i].colisionaCon(princesa)) {
					princesa.perderVida();
					jf[i] = null;
				}
				if(jf[i] != null && jf[i].seSalioDelMapa(entorno)) {
					jf[i] = null;
				}
			}
		}
		timerAtaque ++;
		dibujarJefe();
		if (contadorBalas == 0) {
			iniciarAtaque1(princesa);
			this.timerAtaque = 0;
		}
	}
	
	public void actualizarAtaques(Princesa princesa) {
		this.princesa = princesa;
		if (ataque == 1) {
			actualizarAtaque1();
		}
		if (ataque == 2) {
			actualizarAtaque2();
		}
	}

//Metodos
	//Metodo1

	private void moverJefeDerechaIzq() {

	    // Sumamos la velocidad a la posición X
	    this.x += velocidad;

	    // Si se pasa de cierto límite a la derecha, invertimos la velocidad
	    if (this.x > 600) { 
	        velocidad = -2; // Empieza a ir a la izquierda
	    }
	    
	    // Si se pasa de cierto límite a la izquierda, la volvemos a invertir
	    if (this.x < 200) { 
	        velocidad = 2; // Empieza a ir a la derecha
	    }
	}

	//Metodo2

	
	private void moverJefeA(double x, double y) {
		if (this.x < x) {
			this.x += 2;
		}
		
		if (this.x > x) {
			this.x -= 2;
		}
		
		if (this.y < y) {
			this.y += 2;
		}

		if (this.y > y) {
			this.y -= 2;
		}
	}
	

	public void dibujarJefe() {
		entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, 0, Color.RED);
		dibujarVidas();
	}

	public boolean detectarColisionProyectil(Proyectil proyectil) {
        // Solo intentamos el parry si el proyectil fue disparado y está activo
        if (proyectil != null && proyectil.isDisparado()) {

            // Caja de colisión (AABB) entre el Jefe y el Proyectil
            boolean colision = (this.x - this.ancho/2 < proyectil.getX() + proyectil.getAncho()/2 &&
                                this.x + this.ancho/2 > proyectil.getX() - proyectil.getAncho()/2 &&
                                this.y - this.alto/2 < proyectil.getY() + proyectil.getAlto()/2 &&
                                this.y + this.alto/2 > proyectil.getY() - proyectil.getAlto()/2);

            if (colision) {
            	perderVida();
            	return true;
            }
        }
        return false;
    }
    
	private void dibujarVidas() {

    	entorno.cambiarFont("Arial", 20, java.awt.Color.RED);

    	entorno.escribirTexto("Jefe", 590, 30);
    	int columna1Vidas = 0;
    	int columna2Vidas = 0;
    	for(int i = 0; i < this.vidas; i++) {
    		if (i < 7 ) {
    			entorno.dibujarRectangulo(600 + columna1Vidas,60,20,20,0,java.awt.Color.LIGHT_GRAY);
    			columna1Vidas += 30;
    		} else {
    			entorno.dibujarRectangulo(600 + columna2Vidas,100,20,20,0,java.awt.Color.LIGHT_GRAY);
    			columna2Vidas += 30;
    		}
    	}
    }
	
    private void perderVida() {
    	this.vidas--;
    }
    

    
	public boolean estaMuerto() {
		if (vidas <= 0) {
			return true;
		}
		return false;
	}
	
	
	//Getters y setters porque son privadas las variables y se tiene que usar el codigo en otros archivos
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}




	
}


