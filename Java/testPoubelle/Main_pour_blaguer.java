package testPoubelle;

import java.util.HashMap;

import communication.Communication;
import communication.ManipulationJson;
import gestion_population.Coordonnees;
import gestion_population.TheGame;

public class Main_pour_blaguer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		Coordonnees plop = new Coordonnees(0f, 0f);
//		ThreadProut threadProut = new ThreadProut(plop);
//		threadProut.start();
//		
//		System.out.println("prout");
//		while(true){
//			try {
//				Thread.sleep(500);
//				System.out.println(plop.getLatitude());
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				System.out.println("proble");
//				e.printStackTrace();
//			}
//
//		}	
		String StringDeLaMapEnJson;
		
		String plop =outils.OutilsCalculs.fichierTxtVersString("C:\\Users\\atila\\Downloads\\Projet\\jsontest.txt");
		System.out.println(plop);
		//creation de la partie avec recuperation du jsonMap pour innitialiser la partie
		TheGame laPartie = new TheGame(); 
	//	StringDeLaMapEnJson = Communication.getRecevoir(outils.Global.URL_GET_MAP);
		ManipulationJson.jsonFromStringMap(plop, laPartie);
		
		System.out.println(laPartie.toString());

//		HashMap<String, Integer> plop = new HashMap<>();
//		plop.put("William", 10);
//		plop.put("Victor", 9);
//		
//		System.out.println(plop.toString());
	}

}
