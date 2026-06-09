package juego;

import entorno.Entorno;

public class GestionadorDeItems {
    private VidaExtra[] vidas;
    private Entorno entorno;
    public GestionadorDeItems(Entorno entorno) {
        this.entorno = entorno;
        vidas = new VidaExtra[10];
    }
    public void crearVida(double x, double y) {
        for(int i = 0; i < vidas.length; i++) {
            if(vidas[i] == null) {
                vidas[i] = new VidaExtra(x, y);
                break;
            }
        }
    }
    public void actualizarItems(Princesa princesa,double camaraX) {
        for(int i = 0; i < vidas.length; i++) { 
            if(vidas[i] != null) {
            	vidas[i].actualizar(camaraX, entorno);
                vidas[i].dibujar(entorno);
                if(vidas[i].colisionaCon(princesa)) {
                    princesa.ganarVida();
                    vidas[i] = null;
                }
                if(vidas[i] != null && vidas[i].seSalioDelMapa(entorno)) {
                	vidas[i] = null;
                }
            }
        }
    }
    
    public VidaExtra[] getVidas() {
    	return vidas;
    }
}
