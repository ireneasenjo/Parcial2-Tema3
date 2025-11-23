package es.upm.aled.tema3.Ejercicio2;

public class RecursoCompartido {
	//Ahora hay 2 enteros. Para cada entero necesitamos un monitor que controle el número de lectores y si alguien está escribiendo
	
	private Integer entero = null;
	private int numLectores = 0;
	private boolean escribiendo = false;
	
	//LECTURA
	public void leer(String nombreLector) throws InterruptedException {
		//Adquirir permiso de lectura
		this.accesoLectura(nombreLector);
		//Acción de lectura
		Integer valorLeido = this.entero;
		System.out.println("Lector" + nombreLector + "está leyendo. Valor = " + valorLeido);
		Thread.sleep((long)(Math.random()*500));
		//Libera permiso de lectura
		this.terminaLectura(nombreLector);
	}
	
	private synchronized void accesoLectura(String nombreLector) throws InterruptedException {
		//si el entero es null debe esperar
		while (escribiendo || this.entero == null) {
			System.out.println("Lector" + nombreLector + "espera para leer.");
			wait();
		}
		numLectores ++;
		System.out.println("Lector "+ nombreLector + " ha empezado a leer.");
		}
	
	private synchronized void terminaLectura (String nombreLector) throws InterruptedException {
		numLectores--;
		if (numLectores == 0) {
			notifyAll();
		}
		System.out.println("Lector "+ nombreLector + " ha terminado de leer.");
	}
	
	//ESCRITURA
	public void escribir(String nombreEscritor) throws InterruptedException {
		//Adquirir permiso de lectura
		this.accesoEscritura(nombreEscritor);
		//Acción de escritura
		Integer valorAntiguo = this.entero;
		this.entero = (int)(Math.random()*10000); //el entero se genera aleatoriamente
		System.out.println("Escritor " + nombreEscritor + " escribe. Valor antiguo = " + String.valueOf(valorAntiguo)+ ", nuevo= " + this.entero);
		Thread.sleep((long)(Math.random()*500));
		//Liberar permiso de escritura
		this.terminaEscritura(nombreEscritor);
	}
	
	private synchronized void accesoEscritura(String nombreEscritor) throws InterruptedException {
		//el lector debe esperar si escribiendo es true (un escritor está dentro) o si hay lectores activos
		while (numLectores>0 || escribiendo) {
			System.out.println("Escritor " + nombreEscritor + "espera para escribir.");
			wait();
		}
		escribiendo = true;
		System.out.println("Escritor " + nombreEscritor + " ha empezado a escribir.");
	}
	
	private synchronized void terminaEscritura(String nombreEscritor) throws InterruptedException {
		escribiendo = false;
		notifyAll();
		System.out.println("Escritor " + nombreEscritor + " ha terminado de escribir");
	}
}

