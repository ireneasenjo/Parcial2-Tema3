package es.upm.aled.tema3.Ejercicio4;

public class Main {

	//el programa crea 2 hebras productoras y 3 hebras consumidoras
	//todas trabajan sobre el mismo almacén compartido
	
	public static void main(String[] args) {
		Almacen almacen = new Almacen();
		
		//PRODUCTORES
		for (int i = 1; i<= 2; i++) { //crea 2 hebras
			new Thread(() -> { //crea objeto Thread y se le da nombre Prod-1 o Prod-2 según la iteración
				try {
					while(true) {
						almacen.almacenar(Thread.currentThread().getName()); //llama a almacenar()-> intenta meter un producto
						Thread.sleep((long)(Math.random()*300)); //duerme, simula producir algo
					}
				} catch (InterruptedException e) {}
			}, "Prod-" + i).start(); //arranca
		}
		
		//CONSUMIDORES
		for (int i=1; i<= 3; i++) {
			new Thread(()-> {
				try {
					while(true) {
						almacen.extraer(Thread.currentThread().getName());
						Thread.sleep((long)(Math.random() * 500));
					}
				} catch (InterruptedException e) {}
			}, "Cons-" + i).start();
		}

	}

}
