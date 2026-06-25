package es.upm.aled.tema3.Ejercicio6;

public class Main {

	public static void main(String[] args) {
		Marmita marmita = new Marmita(5); //marmita de 5 raciones
		
		//Hebra cocinero
		new Thread(() -> {
			try {
				while(true) {
					marmita.rellenar();
				}
			} catch (InterruptedException e) {}
		}, "Cocinero").start();
		
		//hebras canibales
		for (int i=1; i<=4; i++) {
			new Thread(() -> {
				try {
					while(true) {
						marmita.comer(Thread.currentThread().getName());
						//tiempo para digerir antes de volver a tener hambre
						Thread.sleep((long)(Math.random()*2000));
					}
				} catch (InterruptedException e) {}
		
			},"Canibal-" +i).start();
						
		}

	}

}
