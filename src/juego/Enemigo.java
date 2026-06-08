package juego;

import java.awt.Color;

import entorno.Entorno;

//Clase
public class Enemigo {

    private double x;
    private double y;

    private double ancho;
    private double alto;

    private double velocidadX;

    private Entorno entorno;
    
    private boolean colisiono;

    
//Constructor
    public Enemigo(double x, double y, boolean vieneDeIzquierda, Entorno entorno) {

        this.x = x;
        this.y = y;

        this.ancho = 30;
        this.alto = 30;

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
        this.x += this.velocidadX;
    }


  //Metodo2
    public void dibujar() {
        entorno.dibujarRectangulo(
                this.x,
                this.y,
                this.ancho,
                this.alto,
                0,
                Color.BLUE);
    }

  //Metodo3
    public boolean fueraDePantalla() {

        return this.x < -this.ancho
                || this.x > entorno.ancho() + this.ancho;
    }

    
    //ESTO COMPRUEBA SI COLISIONA EL PROYECTIL CON EL ENEMIGO
    public boolean colisionaCon(Proyectil proyectil) {
    	
        if(proyectil == null) {
            return false;
        }
        return this.x - this.ancho/2 < proyectil.getX() + proyectil.getAncho()/2 &&
               this.x + this.ancho/2 > proyectil.getX() - proyectil.getAncho()/2 &&
               this.y - this.alto/2 < proyectil.getY() + proyectil.getAlto()/2 &&
               this.y + this.alto/2 > proyectil.getY() - proyectil.getAlto()/2;
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
}

