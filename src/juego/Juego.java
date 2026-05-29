package juego;


import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	
	// Variables y métodos propios de cada grupo
	
	
	
	
	
	private Niveles gestorNiveles;
	
	// CORRECCIÓN: Faltaba declarar la variable nivel que usás en el tick
	
	
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


	
	/**
	 * El tick() ahora SOLO decide qué nivel se debe renderizar y procesar.
	 * Se eliminó todo el código duplicado que estaba acá adentro.
	 */
	public void tick()
	{
		if (gestorNiveles.getNivel() == 1) {
			gestorNiveles.ejecutarNivel1();
		} else if (gestorNiveles.getNivel() == 2) {
			gestorNiveles.ejecutarNivel2();
		}
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}