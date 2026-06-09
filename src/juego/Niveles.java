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
    private Image fondolvl1;
    private Image imagenVictoria; // Imagen de Candace festeando
    private GestionadorDeItems items;
    
    private double camaraX = 0;
    private double velMaxCamara = 4;
    private double camaraRecorrido = 4;
    private double maxCamaraRecorrido;
    
    private int nivel = 1;
    
    //Constructor
    public Niveles(Entorno entorno) {
        this.entorno = entorno;
        this.fondolvl1 = Herramientas.cargarImagen("fondo1.jpg");
        this.imagenVictoria = Herramientas.cargarImagen("juego/victoria.jpg"); 
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
        plataformas.colisionesPlataformas(princesa);
        if(princesa.getY() > entorno.alto() + 100) {
            princesa.perderVida();
            princesa.reaparecer();
        }
        princesa.actualizarInvulnerabilidad();
        princesa.actualizarAnimacion();
        items.actualizarItems(princesa, camaraX);
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
        plataformas.colisionesPlataformas(princesa); 
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
    
    //Metodo7
    public void gameOver() {
        entorno.cambiarFont("Arial", 30, Color.ORANGE, entorno.NEGRITA);
        entorno.escribirTexto("GAME OVER", 300, 300);
        entorno.cambiarFont("Arial", 20, Color.ORANGE, entorno.NORMAL);
        entorno.escribirTexto("Presiona Enter para volver a intentar", 250, 400);
        if (entorno.sePresiono(entorno.TECLA_ENTER)) {
            inicializarNivel1();
        }
        return;
    }
    
    public void pantallaVictoria() {
        // Dibuja la imagen de Candace
        entorno.dibujarImagen(this.imagenVictoria, entorno.ancho() / 2, entorno.alto() / 2, 0);
        
        // Textos centrados
        entorno.cambiarFont("Arial", 35, Color.CYAN, entorno.NEGRITA);
        entorno.escribirTexto("¡¡¡GANASTE!!!", entorno.ancho() / 2 - 100, 100); 
        entorno.escribirTexto("Mucho trabajo detras :):)", entorno.ancho() / 2 - 130, entorno.alto() - 50);
        entorno.cambiarFont("Arial", 22, Color.WHITE, entorno.NORMAL);
        entorno.escribirTexto("Presiona Enter para volver a jugar", entorno.ancho() / 2 - 170, entorno.alto() - 80); 
        
        if (entorno.sePresiono(entorno.TECLA_ENTER)) {
            inicializarNivel1();
        }
        return;
    }
    
    //Metodo8
    public void actualizarNivel() {
        if (nivel == 1 && !princesa.estaMuerta()) {
            ejecutarNivel1();
        } else if (nivel == 2  && !princesa.estaMuerta()) {
            ejecutarNivel2();
        } else if (nivel == 3) {
            pantallaVictoria();
        }
        if (princesa.estaMuerta()) {
            gameOver();
        }
    }
}