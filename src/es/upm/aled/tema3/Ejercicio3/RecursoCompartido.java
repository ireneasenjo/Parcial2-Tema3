package es.upm.aled.tema3.Ejercicio3;

public class RecursoCompartido {
	
	private Integer entero = null; //el dato compartido
	private int numLectores = 0; //cuántos lectores están leyendo ahora
	private boolean escribiendo = false; //si hay un escritor dentro
	
	//Contadores de hebras esperando por prioridad
	//cada índice corresponde con una prioridad: 0=BAJA, 1=MEDIA, 2=ALTA
	private int [] lectoresEsperando = {0, 0, 0};
	private int [] escritoresEsperando = {0, 0, 0};
	
	//LECTURA
	public void leer(String nombre, Prioridad p) throws InterruptedException {
		accesoLectura(nombre, p);
		
		System.out.println("Lector" + nombre + " (" + p + ") leyendo. Valor: " + entero );
		Thread.sleep((long)(Math.random()*500));
		
		terminaLectura(nombre, p);
	}
	
	private synchronized void accesoLectura(String nombre, Prioridad p) throws InterruptedException {
		//registrar q estamos esperando
		lectoresEsperando[p.ordinal()]++; //p es valor del enum Prioridad. Ej prioridad alta-> p.ordinal()=2->lectoresEsperando[2]++ y suma 1 a los lectores de prioridad ALTA esperando
		
		try {
			//CONDICIÓN DE ESPERA
			//1. Si alguien está escribiendo
			//2. si el entero es null
			//3. PRIORIDAD: Si hay escritores de mayor prioridad esperando
			while (escribiendo || entero ==null || hayEscritoresMayorPrioridad(p)) {
				System.out.println("Lector " + nombre + " (" + p + ") espera.");
                wait();
			}	
		} finally { //ya no estamos esperando
			lectoresEsperando[p.ordinal()]--; //cuando puede pasar decrementa el contador
		}
		numLectores++;
		System.out.println("Lector " + nombre + " (" + p + ") entra.");
	}
	
	private synchronized void terminaLectura(String nombre, Prioridad p) {
		numLectores--;
		if(numLectores == 0) {
			notifyAll(); //despertamos a todos para que reevalúen las condiciones
		}
		System.out.println("Lector " + nombre + " (" + p + ") sale.");
	}
	
	//ESCRIBIR
	public void escribir(String nombre, Prioridad p) throws InterruptedException {
		accesoEscritura(nombre, p);
		
		entero = (int)(Math.random()*10000);
		System.out.println("Escritor " + nombre + " (" + p + ") escribe nuevo valor: " + entero);
		Thread.sleep((long)(Math.random()*500));
		terminaEscritura(nombre, p);
	}
	
	private synchronized void accesoEscritura(String nombre, Prioridad p) throws InterruptedException {
		escritoresEsperando[p.ordinal()]++; //marca que este escritor está esperando
		
		try {
			//CONDICIÓN DE ESPERA
			//1. Si hay lectores dentro
			//2. Si hay otro escritor dentro
			//3. PRIORIDAD A: si hay cualquier hebra (L o E) de mayor prioridad esperando
			//4. PRIORIDAD B: si hay lectores de mi misma prioridad  el lector va primero
			while (numLectores > 0 || escribiendo || hayAlguienMayorPrioridad(p) || (lectoresEsperando[p.ordinal()]>0)) {
				 System.out.println("Escritor " + nombre + " (" + p + ") espera.");
	                wait();
			}
			
		} finally {
			escritoresEsperando[p.ordinal()]--;
		}
        escribiendo = true;
        System.out.println("Escritor " + nombre + " (" + p + ") entra.");
	}
	
    private synchronized void terminaEscritura(String nombre, Prioridad p) {
        escribiendo = false;
        notifyAll();
        System.out.println("Escritor " + nombre + " (" + p + ") sale.");
    }
    
    //MÉTODOS AUXILIARES DE PRIORIDAD
    
    //Se usa en lectores, si hay escritores con prioridad superior, deben esperar
    //Devuelve true si hay algún escritor con prioridad estricta mayor que 'p'
    private boolean hayEscritoresMayorPrioridad(Prioridad p) {
    	for (int i = p.ordinal() + 1; i < 3; i++) {
    		if(escritoresEsperando[i]>0) return true;
    	} 
    	return false;
    }
    
    //se usa es escritores, si hay cualquier lector/escritor de mayor prioridad, se bloquean
    //devuelve true si hay cualquier hebra (L o E) con prioridad estricta mayor que 'p'
    private boolean hayAlguienMayorPrioridad(Prioridad p) {
    	for(int i=p.ordinal()+1; i<3; i++) {
    		if(lectoresEsperando[i] > 0 || escritoresEsperando[i]>0) return true;
    	}
    	return false;
    }


	
}


