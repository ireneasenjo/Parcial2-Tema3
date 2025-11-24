package es.upm.aled.tema3.Ejercicio3;

public class Main {

	public static void main(String[] args) {
		RecursoCompartido recurso = new RecursoCompartido(); //este objeto es el q controla el acceso al entero
		Prioridad[] prioridades = Prioridad.values(); //se obtiene el array de prioridades
		
		//bucle 20 veces. Creamos 20 hebras lectoras y aprox 4 hebras escritoras 
		for(int i=0; i<20; i++) {
			Prioridad p = prioridades[(int)(Math.random()*3)]; //se asigna prioridad aleatoria a cada lector. De 0 a 3 (baja,media,alta)
			
			//cada 5 iteraciones creas un escritor
			if(i % 5 ==0) {
				Prioridad pE = prioridades[(int)(Math.random()*3)];
				Thread t2 = new HebraEscritora(recurso, "E" + i, pE);
				t2.start();
			}
			//se crea un lector en cada iteración
			Thread t = new HebraLectora(recurso, "L" + i, p);
			t.start(); 
		}
	}

}
