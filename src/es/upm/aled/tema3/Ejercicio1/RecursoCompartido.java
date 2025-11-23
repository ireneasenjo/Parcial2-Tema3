package es.upm.aled.tema3.Ejercicio1;

public class RecursoCompartido {
	private Integer entero = null; //el recurso q se va a leer y escribir
	private int numLectores = 0; //cuántos lectores están leyendo en este momento, para impedir q un escritor entre si hay lectores activos
	private boolean escribiendo = false; //indica si hay un escritor trabajando
	
	//LECTURA
	public void leer(String nombreLector) throws InterruptedException {
		this.accesoLectura(nombreLector); //solicita permiso de lectura
		Integer valorLeido = this.entero; //lee el valor fuera del bloque synchronized
		Thread.sleep((long) (Math.random() * 500));
		this.terminaEscritura(nombreLector); //libera permiso de lectura 
	}
	
	//el lector debe esperar si escribiendo es true (un escritor está dentro) o si entero==null (aún no hay nada que leer)
	private synchronized void accesoLectura (String nombreLector) throws InterruptedException{
		while (escribiendo || this.entero == null) {
			wait();
		} //si puede leer incrementa número de lectores y entra en zona de lectura
		numLectores++;
	}
	
	private synchronized void terminaLectura (String nombreLector) throws InterruptedException {
		numLectores--;
		if(numLectores == 0) {
			notifyAll(); //cuando el último lector termina notifica a todos porque quizá haya escritores esperando
		}
	}
	
	//ESCRITURA
	public void escribir (String nombreEscritor) throws InterruptedException {
		this.accesoEscritura(nombreEscritor); //1. solicita acceso
		Integer valorAntiguo = this.entero; //2. acción de escritura
		this.entero = (int)(Math.random()*10000); //cambia valor aleatoriamente
		Thread.sleep((long)(Math.random()* 500));
		this.terminaEscritura(nombreEscritor); //libera
	}
	
	//el lector debe esperar si escribiendo es true (un escritor está dentro) o si hay lectores activos
	private synchronized void accesoEscritura(String nombreEscritor) throws InterruptedException {
		while (numLectores>0 || escribiendo) {
			wait();
		}
		escribiendo = true; //cuando entra marca escribiendo true
	}
	
	private synchronized void terminaEscritura(String nombreEscritor) throws InterruptedException {
		escribiendo = false;
		notifyAll(); //libera su exclusividad y despierta a lectores bloqueados y escritores bloqueados
	}

}
