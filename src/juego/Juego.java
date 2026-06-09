package juego;


import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;


//Clase
public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	
	// Variables y métodos propios de cada grupo
	private Niveles gestorNiveles;
	
	private String estadoJuego = "JUGANDO";
	

    Image imagenVictoria = Herramientas.cargarImagen("juego/victoria.jpg");
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		
		// Inicializamos el gestor de niveles
		this.gestorNiveles = new Niveles(this.entorno);
		gestorNiveles.inicializarNivel1();
		// Inicia el juego!
		this.entorno.iniciar();
	}


	
	
	 // El tick() ahora SOLO decide qué nivel se debe renderizar y procesar.
	 // Se eliminó todo el código duplicado que estaba acá adentro.
	public void tick()
	{
		if (estadoJuego.equals("JUGANDO")) {
			gestorNiveles.actualizarGameplay();
            
            // Verificaciones globales
            if (gestorNiveles.princesaEstaMuerta()) {
                estadoJuego = "GAME_OVER";
            }
            if (gestorNiveles.getNivelActual() == 3) {
                estadoJuego = "VICTORIA";
            }
            
        } else if (estadoJuego.equals("GAME_OVER")) {
            mostrarPantallaGameOver();
            
        } else if (estadoJuego.equals("VICTORIA")) {
            mostrarPantallaVictoria();
        }
    }
	
	private void mostrarPantallaGameOver() {
        entorno.cambiarFont("Arial", 30, Color.ORANGE, entorno.NEGRITA);
        entorno.escribirTexto("GAME OVER", 300, 300);
        entorno.cambiarFont("Arial", 20, Color.ORANGE, entorno.NORMAL);
        entorno.escribirTexto("Presiona Enter para volver a intentar", 250, 400);
        
        if (entorno.sePresiono(entorno.TECLA_ENTER)) {
        	gestorNiveles.inicializarNivel1(); // Resetea el juego
            estadoJuego = "JUGANDO";
        }
    }

    private void mostrarPantallaVictoria() {
        // Aquí dibujas la imagen de Candace festeando que ahora tendrías en Main
        entorno.dibujarImagen(this.imagenVictoria, entorno.ancho() / 2, entorno.alto() / 2, 0);
        entorno.cambiarFont("Arial", 35, Color.CYAN, entorno.NEGRITA);
        entorno.escribirTexto("¡¡¡GANASTE!!!", entorno.ancho() / 2 - 100, 100); 
        entorno.escribirTexto("Mucho trabajo detras :):)", entorno.ancho() / 2 - 130, entorno.alto() - 50);
        entorno.cambiarFont("Arial", 22, Color.WHITE, entorno.NORMAL);
        entorno.escribirTexto("Presiona Enter para volver a jugar", entorno.ancho() / 2 - 170, entorno.alto() - 80); 
        if (entorno.sePresiono(entorno.TECLA_ENTER)) {
            gestorNiveles.inicializarNivel1();
            estadoJuego = "JUGANDO";
        }
    }
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}