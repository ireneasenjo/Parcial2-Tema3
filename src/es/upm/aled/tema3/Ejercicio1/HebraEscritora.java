package es.upm.aled.tema3.Ejercicio1;

public class HebraEscritora extends Thread{
	private final RecursoCompartido recurso;
	private final String nombre;
	
	public HebraEscritora(RecursoCompartido recurso, String nombre) {
		this.recurso=recurso;
		this.nombre=nombre;
	}
	
	@Override
	public void run() {
		try {
			recurso.escribir(nombre);
		} catch (InterruptedException e) {
			
		}
	}

}
