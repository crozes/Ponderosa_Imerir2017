package testPoubelle;

import gestion_population.Coordonnees;
public class ThreadProut extends Thread {
	float truc =0;
	Coordonnees plop;
	
	
	public ThreadProut(Coordonnees plop){
		this.plop = plop;
	}
	public void run(){
		
		while(true){
			try {
				Thread.sleep(500);
				System.out.println("j'existe");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("plop");
				e.printStackTrace();
			}
			
			truc+=1;
			this.plop.setLatitude(truc);
		}

	}
	
}


