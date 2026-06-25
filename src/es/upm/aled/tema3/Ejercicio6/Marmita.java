package es.upm.aled.tema3.Ejercicio6;

public class Marmita {
	private int raciones; //comida q queda ahora
	private final int CAPACIDAD_MAX; //tamaño máximo de la marmita
	private boolean cocineroAvisado = false; //si ya se ha avisado al cocinero
	
	public Marmita (int capacidad) {
		this.CAPACIDAD_MAX = capacidad;
		this.raciones = capacidad; //empieza llena
	}
	
	//método comer llama a los caníbales. Como es synchronized solo una hebra puede modificar raciones a la vez
	public synchronized void comer (String nombreCanibal) throws InterruptedException {
		//si marmita está vacía, avisar al cocinero y esperar
		while (raciones == 0) {
			if (!cocineroAvisado) {
				System.out.println(nombreCanibal + " ve la marmita VACÍA y avisa al cocinero.");
				cocineroAvisado = true;
				notifyAll(); //despertamos al cocinero
			}
			System.out.println(nombreCanibal + " espera comida.");
			wait();
		}
		//comer
		raciones--;
		System.out.println(nombreCanibal + " come. Raciones restantes: " + raciones);
		
		//el ultimjo en comer deja la marmita a 0, el siguiente avisará
	}
	
	public synchronized void rellenar() throws InterruptedException {
		//el cocinero espera mientras haya comida o no le hayan avisado
		while (raciones > 0 || !cocineroAvisado) {
			wait();
		}
		
		System.out.println("Cocinero cocinando....");
		//simulacion tiempo de cocina (fuera del bloqueo si quisiéramos concurrencia real pero dentro para simplificar consistencia en este modelo simple
		Thread.sleep(1000); 
		
		raciones = CAPACIDAD_MAX;
		cocineroAvisado = false;
		System.out.println("cocinero ha rellenado la marmita (" + CAPACIDAD_MAX + "raciones).");
		
		notifyAll(); //avisamos a caníbales
	}

}
