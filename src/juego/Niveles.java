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
    private GestionadorDeItems items;
    private double camaraX = 0;
    private double maxCamara = 4;
    private int nivel = 1;
    
    //Retorna clases de codigo de otros archivos para poder generar el nivel
    
//Constructor
    public Niveles(Entorno entorno) {
        this.entorno = entorno;
        fondolvl1 = Herramientas.cargarImagen("fondo1.jpg");

    }
    
//Metodos
  //Metodo1


  //Metodo2
    public void inicializarNivel1() {
        this.nivel = 1;
    	princesa = new Princesa(entorno.ancho()/2, entorno.alto()/2, 45, 25, entorno);
        plataformas = new GestionadorPlataformas();
        plataformas.crearPiso(200, entorno);
        plataformas.crearIslas(entorno);
        plataformas.crearIslasSegundaCapa(entorno);
        proyectil = new Proyectil(600, entorno.alto() - 15);
        enemigos = new GestionadorEnemigos(entorno);
        enemigos.inicializarEnemigos(10);
        castillo = new Castillo(plataformas.getUltimaPlat()-40, 550, "castillo.jpg", this.entorno);
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
        
        // Inicializamos el primer proyectil en el suelo del nivel 2
        this.proyectil = new Proyectil(300, entorno.alto() - 40);
        this.castillo = null;
        this.jefe = new Jefe(entorno);
        this.jefe.iniciarAtaque1(princesa);
    }
    
  //Metodo4
    private void actualizarCamara(Princesa princesa) {
        if (princesa.getX() + 50 > 600 && entorno.estaPresionada(entorno.TECLA_DERECHA)) {
            camaraX += 1;
        } else {
            camaraX = 0;
        }
        if (camaraX > maxCamara) {
            camaraX = maxCamara;
        }
    }
    
  //Metodo5
    private void ejecutarNivel1() {
    	entorno.dibujarImagen(fondolvl1, entorno.ancho()/2, entorno.alto()/2, 0);
        actualizarCamara(princesa);
        princesa.dibujarPrincesa();
        plataformas.colisionesPlataformas(princesa);
        princesa.moverPrincesa();
        princesa.actualizarInvulnerabilidad();
        princesa.actualizarAnimacion();
        items.actualizarItems(princesa, camaraX);
        plataformas.dibujarPlataformas(camaraX);
        plataformas.dibujarIslas(camaraX);
        
        if (proyectil != null && !proyectil.disparo(princesa, entorno)) {
            proyectil = null;
        }
        
        if(enemigos.actualizarEnemigos(princesa, proyectil, items)) {
            proyectil = null;
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
        
        // 3 (El jefe comprueba si el proyectil activo lo tocó)
        if (jefe.detectarColisionProyectil(proyectil)) {
            proyectil = null;
        }
        
        // 4. Texto informativo
        entorno.cambiarFont("Arial", 26, Color.RED, entorno.NEGRITA);
        entorno.escribirTexto("¡NIVEL 2: JEFE FINAL!", 260, 80);
        
        // 5. Movimiento y render del jefe y sus ataques
        jefe.dibujarJefe();
        jefe.actualizarAtaques(princesa);
    }
    
    
  //Metodo7
    public void gameOver() {
    	if (princesa.estaMuerta()) {
    		entorno.cambiarFont("Arial", 30, Color.ORANGE, entorno.NEGRITA);
    	    entorno.escribirTexto("GAME OVER", 300, 300);
    	    entorno.cambiarFont("Arial", 20, Color.ORANGE, entorno.NORMAL);
    	    entorno.escribirTexto("Presiona Enter para volver a intentar", 250, 400);
    	    if (entorno.sePresiono(entorno.TECLA_ENTER)) {
    	    	inicializarNivel1();
    	    }
    	    return;
    	}
    }
  //Metodo8
    public void actualizarNivel() {
    	if (nivel == 1 && !princesa.estaMuerta()) {
			ejecutarNivel1();
		} else if (nivel == 2  && !princesa.estaMuerta()) {
			ejecutarNivel2();
		}
    	if (princesa.estaMuerta()) {
    		gameOver();
    	}
    }
}