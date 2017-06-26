package testPoubelle;

import gestion_population.Coordonnees;

public class Main_pour_blaguer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Coordonnees plop = new Coordonnees(0f, 0f);
		ThreadProut threadProut = new ThreadProut(plop);
		threadProut.start();
		
		System.out.println("prout");
		while(true){
			try {
				Thread.sleep(500);
				System.out.println(plop.getLatitude());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("proble");
				e.printStackTrace();
			}

		}	
	}

}
