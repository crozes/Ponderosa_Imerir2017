package les_mains;

import communication.*;
import outils.Global;

public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Debut du main");
		
		
		Communication connection = new Communication(Global.URL_SERVEUR);
		
		
		//connection.postEnvoyer("Plop_du_java");
		
		
		String testGet = " ";
		testGet = connection.getRecevoir();
		
		System.out.println("Voila le get ->"+testGet.toString());
		
		
		connection.finalize();

		System.out.println("fin du main");
	}

}
