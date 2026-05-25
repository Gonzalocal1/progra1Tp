package juego;

import java.awt.Color;

import entorno.Entorno;

public class Enemigo {

    private double x;
    private double y;

    private double ancho;
    private double alto;

    private double velocidadX;

    private Entorno entorno;

    public Enemigo(double x, double y, boolean vieneDeIzquierda, Entorno entorno) {

        this.x = x;
        this.y = y;

        this.ancho = 30;
        this.alto = 30;

        this.entorno = entorno;

        // ESTO DEFINE DE DONDE VIENE
        if(vieneDeIzquierda) {
            this.velocidadX = 3;
        }
        else {
            this.velocidadX = -3;
        }
    }

    // MOVIMIENTO
    public void mover() {
        this.x += this.velocidadX;
    }

    public void dibujar() {

        entorno.dibujarRectangulo(
                this.x,
                this.y,
                this.ancho,
                this.alto,
                0,
                Color.BLUE);
    }

    // DETECTA QUE EL ENEMIGO SALIO DE LA PANTALLA
    public boolean fueraDePantalla() {

        return this.x < -this.ancho
                || this.x > entorno.ancho() + this.ancho;
    }


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
}
