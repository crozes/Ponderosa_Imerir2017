package outils;

public class ToString {

	public static void toString (Object o){
		if (outils.Global.activerLesSystemOut == true){
			 System.out.println(o.toString());
		 }
		
	}
}
