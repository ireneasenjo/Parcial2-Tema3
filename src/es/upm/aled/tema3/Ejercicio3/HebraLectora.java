package es.upm.aled.tema3.Ejercicio3;

public class HebraLectora extends Thread {
	private RecursoCompartido recurso;
	private String nombre;
	private Prioridad prioridad;
	
	public HebraLectora(RecursoCompartido r, String n, Prioridad p) {
		this.recurso=r;
		this.nombre=n;
		this.prioridad=p;
	}
	
	@Override
	public void run() {
		try {
			recurso.leer(nombre, prioridad);
		} catch (InterruptedException e) {}
	}

}

