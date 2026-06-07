package juego;

import java.awt.Color;
import entorno.Entorno;

public class VidaExtra {
    private double x;
    private double y;
    private double ancho;
    private double alto;
    private double velocidadY;
    private boolean enSuelo;
    
    public VidaExtra(double x, double y) {
        this.x = x;
        this.y = y;
        this.ancho = 20;
        this.alto = 20;
        this.velocidadY = 0;
        this.enSuelo = false;
    }
    public void dibujar(Entorno entorno) {
        entorno.dibujarRectangulo( this.x,this.y,this.ancho,this.alto,0,Color.GREEN);
    }
    
    public void aplicarGravedad() {
        if(!enSuelo) {
            velocidadY += 0.4;
            if(velocidadY > 8) {
                velocidadY = 8;
            }
            y += velocidadY;
        }
    }
    
    public void verificarSuelo(Entorno entorno) {
        double piso = entorno.alto() - 15;
	        if(y + alto/2 >= piso) {
	        	y = piso - alto/2;
	            velocidadY = 0;
	            enSuelo = true;
	        }
    }
   
    public void detenerEn(double yPlataforma) {
    	this.y = yPlataforma - this.alto/2;
    	this.velocidadY = 0;
        this.enSuelo = true;
    }
    
    public void actualizar(Entorno entorno) {
        aplicarGravedad();
        verificarSuelo(entorno);
    }

    public boolean colisionaCon(Princesa princesa) {
    	if(princesa == null) {
            return false;
        }
        return
            this.x - this.ancho/2 < princesa.getX() + princesa.getAncho()/2 &&
            this.x + this.ancho/2 > princesa.getX() - princesa.getAncho()/2 &&
            this.y - this.alto/2 < princesa.getY() + princesa.getAlto()/2 &&
            this.y + this.alto/2 > princesa.getY() - princesa.getAlto()/2;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
}