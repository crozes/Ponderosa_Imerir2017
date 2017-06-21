package gestion_population;


/**
 * Simple classe qui à comme paramettres x et y.
 * Elle permet de localiser toute chose physique sur la carte
 * @author atila
 *
 */
public class Coordonnees {
	private int x;
	private int y;
	
	public Coordonnees(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	

}
