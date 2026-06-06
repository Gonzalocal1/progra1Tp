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
	private double vxBala;
    private double vyBala;
	private int vidas;
	private int timerAtaqueMaximo;
	private int timerAtaque;
	JefeProyectil[] jf;
	Princesa princesa;
	
	private int ataque;
	
	
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
	
	private void cacularAnguloPrincesa() {
		double deltaX = princesa.getX() - this.x;
        double deltaY = princesa.getY() - this.y;
        double distancia = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Control de seguridad para evitar división por cero
        if (distancia == 0) {
            distancia = 1;
            deltaX = 1;
        }

        // Descomposición vectorial de la velocidad
        this.vxBala = (deltaX / distancia) ;
        this.vyBala = (deltaY / distancia) ;
	}
	
	public void iniciarAtaque1(Princesa princesa) {
		this.princesa = princesa;
		this.timerAtaqueMaximo = 1000;
		jf = new JefeProyectil[4];
		int grados = 0;
		for (int i = 0; i < jf.length; i++) {
			jf[i] = new JefeProyectil(grados, 40, entorno);
			grados += 30;
		}
	}
	
	private void iniciarAtaque2() {
		jf = new JefeProyectil[4];
		this.timerAtaqueMaximo = 150;
		int grados = 0;
		for (int i = 0; i < jf.length; i++) {
			jf[i] = new JefeProyectil(grados, 40, entorno);
			jf[i].girarProyectil(x, y);
			grados += 30;
		}
		
		timerAtaque += 1;
	}
	
	private void actualizarAtaque1() {
		if (jf == null) return;
		for (int i = 0; i < jf.length; i++) {
			jf[i].animacionRadio();
			jf[i].girarProyectil(x, y);
			jf[i].dibujarJefeProyectil();
			
			
			if(jf[i].colisionaCon(princesa)) {
                princesa.perderVida();
            }
			
			
			timerAtaque += 1;
			if (timerAtaque > timerAtaqueMaximo) {
				iniciarAtaque2();
				this.ataque = 2;
				this.timerAtaque = 0;
			}
		}
		moverJefeDerechaIzq();
	}
	
	private void actualizarAtaque2() {
		if (jf == null) return;
		for (int i = 0; i < jf.length; i++) {
			cacularAnguloPrincesa();
			jf[i].moverDireccion(vxBala, vyBala);
			jf[i].dibujarJefeProyectil();
			
			
			if(jf[i].colisionaCon(princesa)) {
                princesa.perderVida();
            }
			
			
			timerAtaque += 1;
			if (timerAtaque > timerAtaqueMaximo) {
				iniciarAtaque1(princesa);
				this.ataque = 1;
				this.timerAtaque = 0;
			}
		}
		dibujarJefe();
		moverJefeDerechaIzq();
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


