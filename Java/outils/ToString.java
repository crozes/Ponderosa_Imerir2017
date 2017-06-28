package outils;

public class ToString {

	private static boolean activerToStringDebug = true;
	private static boolean activerToStringListe = true;
	private static boolean activerToStringMath = true;
	private static boolean activerToStringHTTP = false;
	private static boolean activerToStringJSON = true;
	private static boolean activerToStringVousEtesIci = true;
	private static boolean activerToStringDivers = true;
	private static boolean activerToStringMeteo = false;
	
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
	
	
	
}
