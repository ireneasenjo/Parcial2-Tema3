package es.upm.aled.tema3.Ejercicio2;

public class Main {
	private static final int NUM_LECTORAS = 300;
	private static final int RATIO_LECTORAS_ESCRITORAS = 10;
	
	public static void main(String[] args) {
		//instanciamos dos recursos independientes
		RecursoCompartido recurso1 = new RecursoCompartido();
		RecursoCompartido recurso2 = new RecursoCompartido();
		
		System.out.println("Iniciando hebras para dos recursos ");
		
		try { 
			for (int i=0; i< NUM_LECTORAS; i++) {
				//ahora pasamos ambos recursos a la lectora
				Thread t = new HebraLectora(recurso1, recurso2, "L" + i);
				t.start();
				
				if(i% RATIO_LECTORAS_ESCRITORAS == 0) { //crea una escritora cada N lectoras
					//pasamos ambos recursos a la escritora
					Thread t2= new HebraLectora(recurso1, recurso2, "E" + i);
					t2.start();
				}
				
				Thread.sleep((long)(Math.random()*200)); //esperamos 200ms entre creación de hebras
			}
		} catch (InterruptedException e) {}
	}

}
