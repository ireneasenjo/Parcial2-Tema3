package es.upm.aled.tema3.Ejercicio2;

public class HebraLectora extends Thread {
	//Ahora se controlan DOS recursos compartidos INDEPENDIENTES: dos enteros
	//Si una hebra está modificando el rpimer entero, no habrá problema es que la otra hebra escriba o lea el segundo entero
	private final RecursoCompartido recurso1;
	private final RecursoCompartido recurso2;
	private final String nombre;
	
	public HebraLectora(RecursoCompartido r1, RecursoCompartido r2, String nombre ) {
		this.recurso1 = r1;
		this.recurso2 = r2;
		this.nombre = nombre;
	}
	@Override
	public void run() {
		try {
			if (Math.random() < 0.5) { //devuelve un número entre 0 y 1. Con probabilidad 50% lee primero R1 y luego R2 y viceversa
				System.out.println(nombre + "decide leer recurso 1 y luego recurso 2");
				recurso1.leer(nombre + "(r1)");
				recurso2.leer(nombre + "(r2)");
			} else {
				System.out.println(nombre + "decide leer recurso 2 y luego recurso 1");
				recurso2.leer(nombre + "(r2)");
				recurso1.leer(nombre + "(r1)");
			}
		} catch (InterruptedException e) {}
	}

}
