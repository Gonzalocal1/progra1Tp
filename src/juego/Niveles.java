package juego;

import java.awt.Color;
import entorno.Entorno;

public class Niveles {


    private Entorno entorno;
    private Princesa princesa;
    private Castillo castillo;
    private GestionadorPlataformas plataformas;
    private Proyectil proyectil;
    private GestionadorEnemigos enemigos;
    
    
    private double camaraX = 0;
	private double maxCamara = 4;
    
	private int nivel = 1; 
    
    public Niveles(Entorno entorno) {
        this.entorno = entorno;
    }

    public void inicializarNivel1() {
    	princesa = new Princesa(entorno.ancho()/2,200,20,20, entorno);
    	plataformas = new GestionadorPlataformas();
		plataformas.crearPiso(50, entorno);
		proyectil = new Proyectil(600, entorno.alto() - 15);
		enemigos = new GestionadorEnemigos(entorno);
		enemigos.inicializarEnemigos(10);
		castillo = new Castillo(plataformas.getUltimaPlat(), 550, "castillo.jpg", this.entorno);
    }
    
    private void inicializarNivel2() {
        this.nivel = 2; 
        this.princesa.setX(100);
        this.princesa.setY(480); 
        this.enemigos.limpiarEnemigos(); 
        this.plataformas.limpiarPlataformas();
        castillo = null;
    }
    
    public void actualizarCamara(Princesa princesa) {
		if (princesa.getX() + 50 > 600 && entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			camaraX += 1;
		} else {
			camaraX = 0;
		}
		if (camaraX > maxCamara) {
			camaraX = maxCamara;
		}
	}
    
    public void ejecutarNivel1() {
    	
        
      
        
        actualizarCamara(princesa);
        princesa.dibujarPrincesa();
        plataformas.colisionesPlataformas(princesa);
        princesa.moverPrincesa();
        plataformas.dibujarPlataformas(camaraX);
        
        if (proyectil != null && !proyectil.disparo(princesa, entorno)) {
            proyectil = null;
        }
        
        enemigos.actualizarEnemigos();
        enemigos.mantenerEnemigos();
        castillo.dibujar(camaraX);
        castillo.moverCastillo(camaraX);

        // Si pasa 2 segundos en el castillo, pasamos al nivel 2
        if (castillo.verificarVictoria(princesa)) {
            inicializarNivel2();
        }
    }

    public void ejecutarNivel2() {
        
        // 1. Suelo plano
        double nivelSueloY = 550;
        entorno.dibujarRectangulo(400, nivelSueloY, 800, 100, 0, Color.GRAY);
        
        // 2. Gravedad simple para el piso plano
        double limitePiso = nivelSueloY - 50 - (princesa.getAlto() / 2);
        if (princesa.getY() < limitePiso) {
            princesa.setY(princesa.getY() + 4); 
        } else {
            princesa.setY(limitePiso); 
        }
        
        // 3. Movimiento y render de la princesa
        princesa.moverPrincesa(); 
        princesa.dibujarPrincesa();
        
        // 4. Texto informativo
        entorno.cambiarFont("Arial", 26, Color.RED, entorno.NEGRITA);
        entorno.escribirTexto("¡NIVEL 2: JEFE FINAL!", 260, 80);
    }

    
    
    public int getNivel() { return nivel; }
}