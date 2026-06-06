package juego;

import java.awt.Color;
import java.util.Random;

import entorno.Entorno;

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
	}
	

	
	public void iniciarAtaque1(Princesa princesa) {
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
		//jf = new JefeProyectil[4];
		//int grados = 0;
		//for (int i = 0; i < jf.length; i++) {
		//	jf[i] = new JefeProyectil(grados, 40, entorno);
		//	jf[i].girarProyectil(x, y);
		//	grados += 60;
		//}
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
			this.ataque = 1;
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


