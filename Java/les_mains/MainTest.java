package les_mains;

public class MainTest {

	public static void main(String[] args) {
		outils.ToString.ecrireDansFichier("plop", outils.ToString.urlFichier);
		
		System.out.println(outils.OutilsCalculs.calculerDistance(1, 1, 2, 2));
		
		
		
		System.out.println((float) Math.pow(200,outils.Global.poidCoutDeplacementPuissanceFormule2));
		
	}

}
