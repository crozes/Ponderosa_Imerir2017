package outils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class ToString {

	private static boolean activerToStringDebug = false;
	private static boolean activerToStringListe = false;
	private static boolean activerToStringMath = false;
	private static boolean activerToStringHTTP = false;
	private static boolean activerToStringJSON = false;
	private static boolean activerToStringVousEtesIci = false;
	private static boolean activerToStringDivers = false;
	private static boolean activerToStringMeteo = false;
	
	private static boolean activerTraceFichier = true;
	
	public static void toStringDebug (Object o){
		if (activerToStringDebug == true){
			 System.out.println(o.toString());
		 }
	}
	
	public static void toStringListe (Object o){
		if (activerToStringListe == true){
			 System.out.println(o.toString());
		 }
	}
	
	public static void toStringMath (Object o){
		if (activerToStringMath == true){
			 System.out.println(o.toString());
		 }
	}
	
	public static void toStringHTTP (Object o){
		if (activerToStringHTTP == true){
			 System.out.println(o.toString());
		 }
	}
	
	public static void toStringJSON (Object o){
		if (activerToStringJSON == true){
			 System.out.println(o.toString());
		 }
	}
	
	public static void toStringVousEtesIci (Object o){
		if (activerToStringVousEtesIci == true){
			 System.out.println(o.toString());
		 }
	}
	
	public static void toStringDiver (Object o){
		if (activerToStringDivers == true){
			 System.out.println(o.toString());
		 }
	}
	
	public static void toStringMeteo (Object o){
		if (activerToStringMeteo== true){
			 System.out.println(o.toString());
		 }
	}
	
	

	public static final String urlFichier = "Files/traceAction.txt"; 
	
	public static void ecrireUneTrace(String trace){
		if (activerTraceFichier == true){
			ecrireDansFichier(trace,"Files/traceAction.txt" );
		}
		
	}
	
	public static void ecrireDansFichier(String information, String urlFichier){
		
		BufferedWriter fWriter = null;
		try {
			fWriter = new BufferedWriter(new FileWriter(urlFichier, true));
			
			fWriter.write(information+"\n");
		} catch (IOException e) {
			System.out.println("Probleme a l'ouverture du fichier trace");
			e.printStackTrace();
		}
		finally {
			try{
				fWriter.close();
			}catch (Exception e) {
				// TODO: handle exception
			}
		}

		
	}
	
	
}
