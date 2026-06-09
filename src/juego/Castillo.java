package juego;

import java.awt.Image;
import java.awt.Color;
import entorno.Entorno;
import entorno.Herramientas;

//Clase
public class Castillo {

    private double x;
    private double y;
    private Image imagen;
    private Entorno entorno;
    
    // Almacena el número de tick del entorno cuando la princesa se sube al castillo
    private int tickInicialContacto = -1; 
    private final int TICKS_NECESARIOS = 120; // 2 segundos aprox.

    
//Constructor
    public Castillo(double x, double y, String rutaImagen, Entorno entorno) {
        this.x = x;
        this.y = y;
        this.entorno = entorno; 
        this.imagen = Herramientas.cargarImagen(rutaImagen); 
    }
//Metodos
    //Metodo1
    public void dibujar(double camaraX) {
        if (this.imagen != null) {
        	moverCastillo(camaraX);
            this.entorno.dibujarImagen(this.imagen, this.x, this.y, 0, 0.1);
        }
    }
    
  //Metodo2
    private void moverCastillo(double camaraX) {
    	this.x -= camaraX;
    }
    
  //Metodo3
    public boolean verificarVictoria(Princesa princesa) {
        // Colisión simple por proximidad de coordenadas entre la princesa y el castillo
        boolean estaEncima = Math.abs(this.x - princesa.getX()) < 50 && Math.abs(this.y - princesa.getY()) < 60;

        if (estaEncima) {
            // Si es el primer instante en que lo toca, guardamos el tick actual del motor
            if (tickInicialContacto == -1) {
                tickInicialContacto = this.entorno.numeroDeTick();
            }
            
            // Calculamos cuántos ticks han pasado desde que hizo contacto
            int ticksTranscurridos = this.entorno.numeroDeTick() - tickInicialContacto;

            // Mostramos el texto pedido en pantalla
            this.entorno.cambiarFont("Arial", 22, Color.WHITE, this.entorno.NEGRITA);
            this.entorno.escribirTexto("Enhorabuena, ahora ve y salva a tu amado", 200, 100);

            // Si ya pasaron los 2 segundos (120 ticks), devolvemos verdadero
            if (ticksTranscurridos >= TICKS_NECESARIOS) {
                return true; 
            }
        } else {
            // Si la princesa se baja o se mueve del castillo, reiniciamos el temporizador
            tickInicialContacto = -1;
        }

        return false;
    }
    
//Getters y Setters para usar el código en otros archivos
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
}