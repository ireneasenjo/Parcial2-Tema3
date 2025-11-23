package es.upm.aled.tema3.Ejercicio1;

public class Main {
	
	private static final int NUM_LECTORAS = 300; //creamos 300 hebras lectoras
	private static final int RATIO_LECTORAS_ESCRITORAS = 10; //por cada 10 lectoras habrá 1 escritora
	
	public static void main(String[] args) {
		//creamos recurso compartido, este objeto es el q controla el acceso sincronizado para lectores y escritores
		RecursoCompartido recurso = new RecursoCompartido();
		
		//Iniciando: 300 lectoras y 30 escritoras
		System.out.println("Iniciando: " + NUM_LECTORAS + " lectoras y " +
				NUM_LECTORAS / RATIO_LECTORAS_ESCRITORAS + " escritoras.");
		
		//Bucles de creación de hebras
		try {
		for (int i=0; i< NUM_LECTORAS; i++) { //se ejecuta 300 veces
			//crear y arrancar una hebra lectora
			Thread t = new HebraLectora(recurso, "L" + i);
			t.start();
			
			//crear una escritora cada N lectoras
			if(i% RATIO_LECTORAS_ESCRITORAS == 0 ) {
				Thread t2 = new HebraEscritora(recurso, "E" + i);
				t2.start();	
			}
			//pausa entre creación de hebras
			Thread.sleep((long)(Math.random()*500));
			}
		} catch (InterruptedException e) {}
	
	}
}
