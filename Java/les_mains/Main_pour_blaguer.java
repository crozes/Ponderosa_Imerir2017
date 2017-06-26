package les_mains;

public class Main_pour_blaguer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(outils.OutilsCalculs.quelEstLaPeriodeDeLaJournee(13));
		}	
	}

}
