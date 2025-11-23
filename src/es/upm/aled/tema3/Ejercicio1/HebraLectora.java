package es.upm.aled.tema3.Ejercicio1;

public class HebraLectora extends Thread {
	private final RecursoCompartido recurso; //es el objeto q todos los lectores y escritores utilizan para leer o escribir
	private final String nombre; //identificador del lector
	
	public HebraLectora(RecursoCompartido recurso, String nombre) { 
		this.recurso=recurso;
		this.nombre=nombre;	
	}
	
	@Override
	public void run() {
		try {
			recurso.leer(nombre); //intenta leer el recurso
		} catch (InterruptedException e) {
			
		}
	}
}
