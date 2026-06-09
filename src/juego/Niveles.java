package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

//Clase
public class Niveles {

    private Entorno entorno;
    private Princesa princesa;
    private Castillo castillo;
    private GestionadorPlataformas plataformas;
    private Proyectil proyectil;
    private GestionadorEnemigos enemigos;
    private Jefe jefe;
    private Image fondolvl1 = Herramientas.cargarImagen("fondo1.jpg");
    private GestionadorDeItems items;
    
    private double camaraX = 0;
    private double velMaxCamara = 4;
    private double camaraRecorrido = 4;
    private double maxCamaraRecorrido;
    
    private int nivel = 1;
    
    //Constructor
    public Niveles(Entorno entorno) {
        this.entorno = entorno;
    }
    
    //Metodos
    
    // Método auxiliar (SOLO SE USA EN NIVEL 1 NOW)
    private void aparecerProyectilRandom() {
        if (this.proyectil == null) {
            double randomX = 100 + (Math.random() * (entorno.ancho() - 200));
            double sueloY = entorno.alto() - 40; 
            this.proyectil = new Proyectil(randomX, sueloY);
        }
    }

    //Metodo2
    public void inicializarNivel1() {
        this.nivel = 1;
        int largoPiso = 200;
        camaraRecorrido = 0;
        princesa = new Princesa(entorno.ancho()/2, entorno.alto()/2, 45, 25, entorno);
        plataformas = new GestionadorPlataformas();
        plataformas.crearPiso(largoPiso, entorno);
        plataformas.crearIslas(entorno);
        plataformas.crearIslasSegundaCapa(entorno);
        this.maxCamaraRecorrido = plataformas.getUltimaPlat() - entorno.ancho();
        
        // Nivel 1: Empieza con el spawn aleatorio
        this.proyectil = null;
        aparecerProyectilRandom();
        
        enemigos = new GestionadorEnemigos(entorno);
        enemigos.inicializarEnemigos(10);
        castillo = new Castillo(plataformas.getUltimaPlat()-200, 550, "castillo.jpg", this.entorno);
        items = new GestionadorDeItems(entorno);
    }
    
    private void inicializarNivel2() {
        this.nivel = 2; 
        this.princesa.setX(100);
        this.princesa.setY(480); 
        this.princesa.setLimitesX(30, entorno.ancho()-30);
        this.enemigos.limpiarEnemigos(); 
        
        this.plataformas.limpiarPlataformas();
        this.plataformas.limpiarIslas();
        this.plataformas.crearPisoNivel2(50, entorno); 
        
        // NIVEL 2: Volvemos a tu posición fija original del suelo
        this.proyectil = new Proyectil(300, entorno.alto() - 40);
        
        this.castillo = null;
        this.jefe = new Jefe(entorno);
        this.jefe.iniciarAtaque1(princesa);
    }
    
    //Metodo4
    private void actualizarCamara(Princesa princesa) {
        if (princesa.getX() + 50 > 600 && entorno.estaPresionada(entorno.TECLA_DERECHA) && camaraRecorrido < maxCamaraRecorrido) {
            camaraX ++;  
        } else {
            camaraX = 0;
        }
        if (camaraX > velMaxCamara) {
            camaraX = velMaxCamara;
        }
        camaraRecorrido+= camaraX;
    }
    
    //Metodo5
    private void ejecutarNivel1() {
        entorno.dibujarImagen(fondolvl1, entorno.ancho()/2, entorno.alto()/2, 0);
        actualizarCamara(princesa);
        princesa.dibujarPrincesa();
        princesa.moverPrincesa();
        
        if(princesa.getY() > entorno.alto() + 100) {
            princesa.perderVida();
            princesa.reaparecer();
        }
        princesa.actualizarInvulnerabilidad();
        princesa.actualizarAnimacion();
        items.actualizarItems(princesa, camaraX);
        plataformas.colisionesPlataformas(princesa, items);
        plataformas.dibujarPlataformas(camaraX);
        plataformas.dibujarIslas(camaraX);
        
        // LÓGICA NIVEL 1: Proyectil Aleatorio y Scroll de Cámara
        if (proyectil != null) {
            if (!proyectil.disparo(princesa, entorno, camaraX)) {
                proyectil = null; 
            }
        }
        
        if (enemigos.actualizarEnemigos(princesa, proyectil, items)) {
            proyectil = null;
        }
        
        if (proyectil == null) {
            aparecerProyectilRandom();
        }
        
        enemigos.mantenerEnemigos();
        castillo.dibujar(camaraX);

        if (castillo.verificarVictoria(princesa)) {
            inicializarNivel2();
        }
    }

    //Metodo6
    private void ejecutarNivel2() {
        // 1. Dibujar escenario y procesar físicas de la princesa
        plataformas.dibujarPlataformas(0); 
        plataformas.colisionesPlataformas(princesa, items); 
        princesa.moverPrincesa();
        princesa.dibujarPrincesa();
        princesa.actualizarAnimacion();
        princesa.actualizarInvulnerabilidad();
        
        // LÓGICA NIVEL 2: Como estaba originalmente de forma fija
        if (proyectil != null) {
            if (!proyectil.disparo(princesa, entorno, 0)) {
                // Si se salió del mapa, reaparece FIJO en el centro abajo
                proyectil = new Proyectil(400, entorno.alto() - 40);
            }
        } else {
            // Seguro original por si quedaba null
            proyectil = new Proyectil(400, entorno.alto() - 40);
        }
        
        // Si el jefe lo rompe, queda null y en el próximo loop entra al 'else' de arriba
        if (jefe.detectarColisionProyectil(proyectil)) {
            proyectil = null;
        }
        
        // 4. Texto informativo
        entorno.cambiarFont("Arial", 26, Color.RED, entorno.NEGRITA);
        entorno.escribirTexto("¡NIVEL 2: JEFE FINAL!", 260, 80);
        
        // 5. Movimiento y render del jefe y sus ataques
        jefe.dibujarJefe();
        jefe.actualizarAtaques(princesa);
        if(jefe.derrotado()) {
            this.nivel = 3;
        }
    }
    
    public void actualizarGameplay() {
        if (nivel == 1) {
            ejecutarNivel1();
        } else if (nivel == 2) {
            ejecutarNivel2();
        }
    }

    // Getters necesarios para que Main tome decisiones
    public boolean princesaEstaMuerta() {
        return this.princesa != null && this.princesa.estaMuerta();
    }

    public int getNivelActual() {
        return this.nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}