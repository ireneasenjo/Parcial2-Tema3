package es.upm.aled.tema3.Ejercicio2;

public class HebraEscritora extends Thread {
	private final RecursoCompartido recurso1;
	private final RecursoCompartido recurso2;
	private final String nombre;
	
	public HebraEscritora(RecursoCompartido r1, RecursoCompartido r2, String nombre ) {
		this.recurso1 = r1;
		this.recurso2 = r2;
		this.nombre = nombre;
	}
	@Override
//la escritora sustituye uno de los dos enteros es decir elige UN recurso y escribe en ese único
	public void run() {
		try {
			if (Math.random() < 0.5) { //devuelve un número entre 0 y 1. Con probabilidad 50% lee primero R1 y luego R2 y viceversa
				System.out.println(nombre + "decide escribir en recurso 1");
				recurso1.escribir(nombre);
			} else {
				System.out.println(nombre + "decide escribir en recurso 2");
				recurso2.escribir(nombre);
			}
		} catch (InterruptedException e) {}
	}

}


