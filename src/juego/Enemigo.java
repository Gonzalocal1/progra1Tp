package juego;

import java.awt.Color;
import entorno.Entorno;
import java.awt.Image;
import entorno.Herramientas;

//Clase
public class Enemigo {

    private double x;
    private double y;
    private double ancho;
    private double alto;
    private double velocidadX;
    private Entorno entorno;
    private Image[] sprites;
    private int frameActual;
    private int timerAnimacion;
    private boolean colisiono;
    private boolean fuerte;
    private double yBase;          // La altura central por la que viaja el enemigo
    private double anguloOnda = 0; // Reemplaza a tu variable 'anim'
    private double amplitud = 20;  // Qué tan alta es la onda en píxeles (probá con 40 o 50)
    private double velocidadOnda = 0.05; // Qué tan rápido oscila (números chicos entre 0.01 y 0.1)
    
//Constructor
    public Enemigo(double x, double y, boolean vieneDeIzquierda, boolean esFuerte, Entorno entorno) {
        this.x = x;
        this.y = y;
        this.yBase = y;
        this.fuerte = esFuerte;
        this.sprites = new Image[4];
        this.sprites[0] =
            Herramientas.cargarImagen("enemigo0.png");
        this.sprites[1] =
            Herramientas.cargarImagen("enemigo1.png");
        this.sprites[2] =
            Herramientas.cargarImagen("enemigo3.png");
        this.sprites[3] =
            Herramientas.cargarImagen("enemigo4.png");
        if (fuerte) {
        	this.frameActual = 2;
        } else {
        	this.frameActual = 0;
        }        
        this.timerAnimacion = 0;
        this.ancho = 30;
        this.alto = 30;
        this.colisiono = false;
        this.entorno = entorno;
        if(vieneDeIzquierda) {
            this.velocidadX = 3;
        }
        else {
            this.velocidadX = -3;
        }
    }

//Metodos
    
  //Metodo1
    public void mover() {
        // 1. Avanza horizontalmente de forma normal
        this.x += this.velocidadX;
        if (fuerte) {
            this.anguloOnda += this.velocidadOnda;
            
            this.y = this.yBase + (Math.sin(anguloOnda) * amplitud);
        }
    }


  //Metodo2
    
    public void actualizarAnimacion() {
        timerAnimacion++;
        if(timerAnimacion >= 15) {
            frameActual++;
            timerAnimacion = 0;
            if(!fuerte &&frameActual >= 2) {
                frameActual = 0;
            }
            if(fuerte && frameActual >= sprites.length) {
                frameActual = 2;
            }
        }
    }
//Metodo3
    
    public void dibujar() {
        entorno.dibujarImagen(sprites[frameActual],this.x,this.y,0,0.55);
    }
  //Metodo4
    
    public boolean fueraDePantalla() {
        return this.x < -this.ancho
                || this.x > entorno.ancho() + this.ancho;
    }

    
    //ESTO COMPRUEBA SI COLISIONA EL PROYECTIL CON EL ENEMIGO
    public boolean colisionaCon(Proyectil proyectil) {
        if(proyectil == null || !proyectil.isDisparado()) {
            return false;
        }
        return this.x - this.ancho/2 < proyectil.getX() + proyectil.getAncho()/2 &&
               this.x + this.ancho/2 > proyectil.getX() - proyectil.getAncho()/2 &&
               this.y - this.alto/2 < proyectil.getY() + proyectil.getAlto()/2 &&
               this.y + this.alto/2 > proyectil.getY() - proyectil.getAlto()/2;
    }

    //ESTO DETECTA SI EL ENEMIGO ESTA SUPERPUESTO A LA PRINCESA DETECTANDO LA COLISION
    public boolean colisionaCon(Princesa princesa) {

        if(princesa == null) {
            return false;
        }
        return
            this.x - this.ancho/2 < princesa.getX() + princesa.getAncho()/2
            &&
            this.x + this.ancho/2 > princesa.getX() - princesa.getAncho()/2
            &&
            this.y - this.alto/2 < princesa.getY() + princesa.getAlto()/2
            &&
            this.y + this.alto/2 > princesa.getY() - princesa.getAlto()/2;
    }


    
    
//Getters y Setters para que se usen en otros archivos el codigo
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getAncho() {
        return ancho;
    }

    public double getAlto() {
        return alto;
    }
    
    public boolean isColisiono() {
        return colisiono;
    }
    
    public void setColisionoTrue() {
        this.colisiono = true;
    }


}
