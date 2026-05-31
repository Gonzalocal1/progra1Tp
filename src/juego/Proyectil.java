package juego;

import java.awt.Color;
import entorno.Entorno;

public class Proyectil {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private double velocidad;
    
    // Componentes de velocidad para permitir cualquier diagonal/dirección
    private double vx; 
    private double vy; 
    
    private boolean disparado;
    private boolean equipado; // Evita el bug de quedarse flotando en el aire

    // Constructor para cuando el proyectil aparece en el mapa
    public Proyectil(double x, double y) {
        this.ancho = 30; 
        this.alto = 15;
        this.velocidad = 12; // Velocidad óptima para las diagonales
        this.disparado = false;
        this.equipado = false; 
        this.vx = 0;
        this.vy = 0;
        this.x = x;
        this.y = y;
    }

    // Dibuja el proyectil en la pantalla rotando según hacia dónde vuela
    public void dibujar(Entorno entorno) {
        double angulo = 0;
        if (disparado) {
            angulo = Math.atan2(vy, vx);
        }
        entorno.dibujarRectangulo(this.x, this.y, this.ancho, this.alto, angulo, Color.RED);
    }
    
    // Actualiza la posición del proyectil
    public void actualizar(Princesa princesa) {
        if (!disparado) {
            // SI NO SE DISPARÓ: Sigue fielmente a la princesa
            this.x = princesa.getX();
            this.y = princesa.getY() - (princesa.getAlto() / 2);
        } else {
            // SI YA SE DISPARÓ: Se mueve de forma autónoma en la diagonal calculada
            this.x += this.vx;
            this.y += this.vy;
        }
    }

    // Calcula el vector de velocidad apuntando directamente al mouse (360 grados)
    public void disparar(int mouseX, int mouseY) {
        if (!this.disparado) {
            this.disparado = true;

            double deltaX = mouseX - this.x;
            double deltaY = mouseY - this.y;

            // Calculamos la distancia total (hipotenusa)
            double distancia = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

            // Control de seguridad para evitar división por cero
            if (distancia == 0) {
                distancia = 1;
                deltaX = 1;
            }

            // Descomposición vectorial de la velocidad
            this.vx = (deltaX / distancia) * this.velocidad;
            this.vy = (deltaY / distancia) * this.velocidad;
        }
    }

    /**
     * Controla todo el ciclo de vida del proyectil.
     * @return true si el proyectil sigue vivo, false si debe destruirse.
     */
    public boolean disparo(Princesa princesa, Entorno entorno) {
        // 1. Dibujarse en la pantalla
        this.dibujar(entorno);

        // 2. Manejar el agarre y el seguimiento a la princesa
        if (!this.disparado) {
            if (!this.equipado) {
                // Caja de colisión para agarrarlo del suelo por primera vez
                boolean tocando = (princesa.getX() - princesa.getAncho()/2 < this.x + this.ancho/2 &&
                                   princesa.getX() + princesa.getAncho()/2 > this.x - this.ancho/2 &&
                                   princesa.getY() - princesa.getAlto()/2 < this.y + this.alto/2 &&
                                   princesa.getY() + princesa.getAlto()/2 > this.y - this.alto/2);
                if (tocando) {
                    this.equipado = true; // Se equipa y no se suelta más
                }
            }
            
            if (this.equipado) {
                this.actualizar(princesa);
            }
        } else {
            this.actualizar(princesa);
        }

        // 3. DETECTAR EL DISPARO CON EL CLICK DEL MOUSE
        if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) && this.equipado && !this.disparado) {
            this.disparar(entorno.mouseX(), entorno.mouseY());
        }

        // 4. Verificar si se salió del mapa (por los costados, arriba o abajo)
        if (this.seSalioDelMapa(entorno)) {
            return false; 
        }
        return true; 
    }
    
    // Verifica si el proyectil se salió de los límites visibles del entorno
    public boolean seSalioDelMapa(Entorno entorno) {
        return (this.x > entorno.ancho() + 50 || this.x < -50 || this.y > entorno.alto() + 50 || this.y < -50);
    }

    
    
    // --- GETTERS Y SETTERS ---

    public boolean isDisparado() { 
        return disparado; 
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
	public double getVx() {
	    return this.vx;
	}

	public void setVx(double vx) {
	    this.vx = vx;
	}

	public double getVy() {
	    return this.vy;
	}

	public void setVy(double vy) {
	    this.vy = vy;
	}
}