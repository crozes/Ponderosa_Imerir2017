package gestion_population;

/**
 * Classe abstrete MapItem qui peut etre un stand ou une plublicite
 * 
 * @author atila
 *
 */
public abstract class MapItem {
	String nom;
	Joueur appartient_a;
	String kind;

	Coordonnees coordonnees;

	float influence;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Joueur getAppartient_a() {
		return appartient_a;
	}

	public void setAppartient_a(Joueur appartient_a) {
		this.appartient_a = appartient_a;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public Coordonnees getCoordonnees() {
		return coordonnees;
	}

	public void setCoordonnees(Coordonnees coordonnees) {
		this.coordonnees = coordonnees;
	}

	public float getInfluence() {
		return influence;
	}

	public void setInfluence(float influence) {
		this.influence = influence;
	}

	public void setCoordonnees(int x, int y) {
		this.coordonnees = new Coordonnees(x, y);
	}

	public int getX() {
		return this.coordonnees.getX();
	}

	public void setX(int x) {
		this.coordonnees.setX(x);
	}

	public int getY() {
		return this.coordonnees.getY();
	}

	public void getY(int y) {
		this.coordonnees.setY(y);
	}

}
