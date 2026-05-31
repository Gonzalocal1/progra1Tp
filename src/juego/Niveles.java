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
    private Jefe jefe;
    private JefeProyectil jefeProyectil;
    private JefeProyectil jefeProyectil2;
    private JefeProyectil jefeProyectil3;
    private JefeProyectil jefeProyectil4;
    
    private double camaraX = 0;
    private double maxCamara = 4;
    
    private int nivel = 1; 
    
    public Niveles(Entorno entorno) {
        this.entorno = entorno;
    }

    public void inicializarNivel1() {
        princesa = new Princesa(entorno.ancho()/2, 200, 20, 20, entorno);
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
        
        this.plataformas = new GestionadorPlataformas();
        this.plataformas.crearPisoNivel2(50, entorno); 
        
        // Inicializamos el primer proyectil en el suelo del nivel 2
        this.proyectil = new Proyectil(300, entorno.alto() - 40);
        this.castillo = null;
        this.jefe = new Jefe(entorno);
        this.jefeProyectil = new JefeProyectil(0, 40, entorno);
        this.jefeProyectil2 = new JefeProyectil(90, 40, entorno);
        this.jefeProyectil3 = new JefeProyectil(180, 40, entorno);
        this.jefeProyectil4 = new JefeProyectil(270, 40, entorno);
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

        if (castillo.verificarVictoria(princesa)) {
            inicializarNivel2();
        }
    }

    public void ejecutarNivel2() {
        // 1. Dibujar escenario y procesar físicas de la princesa
        plataformas.dibujarPlataformas(0); 
        plataformas.colisionesPlataformas(princesa); 
        
        princesa.moverPrincesa(); 
        princesa.dibujarPrincesa();
        
        // 2. Lógica del Proyectil (Se ejecuta DESPUÉS de mover a la princesa)
        if (proyectil != null) {
            if (!proyectil.disparo(princesa, entorno)) {
                // Si se salió del mapa (ya sea porque fallaste o porque el jefe te lo devolvió)
                // lo hacemos reaparecer en el centro del mapa para que puedas volver a agarrarlo.
                proyectil = new Proyectil(400, entorno.alto() - 40);
            }
        } else {
            // Seguro por si acaso queda en null
            proyectil = new Proyectil(400, entorno.alto() - 40);
        }
        
        // 3. ¡EL PARRY TRABAJANDO! (El jefe comprueba si el proyectil activo lo tocó)
        if (jefe != null) {
            jefe.pseudoParry(proyectil);
        }
        
        // 4. Texto informativo
        entorno.cambiarFont("Arial", 26, Color.RED, entorno.NEGRITA);
        entorno.escribirTexto("¡NIVEL 2: JEFE FINAL!", 260, 80);
        
        // 5. Movimiento y render del jefe y sus ataques
        jefe.dibujarJefe();
        jefe.moverJefe();
        jefeProyectil.moverProyectil(jefe.getX(),jefe.getY());
        jefeProyectil.dibujarJefeProyectil();
        jefeProyectil.animacionRadio();
        
        jefeProyectil2.moverProyectil(jefe.getX(),jefe.getY());
        jefeProyectil2.dibujarJefeProyectil();
        jefeProyectil2.animacionRadio();
        
        jefeProyectil3.moverProyectil(jefe.getX(),jefe.getY());
        jefeProyectil3.dibujarJefeProyectil();
        jefeProyectil3.animacionRadio();
        
        jefeProyectil4.moverProyectil(jefe.getX(),jefe.getY());
        jefeProyectil4.dibujarJefeProyectil();
        jefeProyectil4.animacionRadio();
        
    }

    public int getNivel() { return nivel; }
}