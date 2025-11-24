package es.upm.aled.tema3.Ejercicio4;

public class Almacen {
	//es un monitor sincornizado q gestiona un depósito con un máximo de 10 productos
	private int productos = 0;
	private final int CAPACIDAD = 10;
	
	//los productores llaman a almacenar() y los consumidores llaman a extraer()
	
	public synchronized void almacenar (String nombreProd) throws InterruptedException { //synchronized solo prod o consum puede ejecutar esre método a la vez
		//esperar si está lleno
		while(productos >= CAPACIDAD) { //while y no if porque cuando la hebra se despierta debe volver a comprobar la condición
			System.out.println("... Productir " + nombreProd + " espera (almacén LLENO).");
			wait();
		}
		
		productos ++; //almacena nuevo producto, incrementa stock
		System.out.println("+++ Productor " + nombreProd + " almacena. Stock: " + productos);
		notifyAll(); //Avisar a los consumidores (y otros productores)
	}
	
	public synchronized void extraer (String nombreCons) throws InterruptedException { 
		//esperar si está vacío
		while (productos == 0) {
			System.out.println("... Consumidor " + nombreCons + " espera (Almacén VACÍO).");
			wait();
		}
		
		productos--; //extrae un producto
		System.out.println("---Consumidor " + nombreCons + " extrae. Stock: " + productos);
		notifyAll(); //avisar a productores (y otros consumidores)
	}
	

}
