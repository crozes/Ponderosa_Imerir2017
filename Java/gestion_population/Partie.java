package gestion_population;

import java.util.ArrayList;
import java.util.HashMap;
import outils.*;

public class Partie {

	private MapItem laMap[][];
	private HashMap<String, MapItem> listeDesMapItem;
	private Population mapDeLaPopulation;
	private HashMap<String, PlayerInfo> listePlayerInfo;
	private Meteo meteoDuJour;
	private Meteo meteoDeDemain;
	private int heureDepuisDebutJeu;
	private HashMap<String, DrinkInfo> listeDesRecipes;
	private ArrayList<String> ranking;


	public HashMap<String, PlayerInfo> getListePlayerInfo() {
		return listePlayerInfo;
	}

	public void setListePlayerInfo(HashMap<String, PlayerInfo> listePlayerInfo) {
		this.listePlayerInfo = listePlayerInfo;
	}



	public ArrayList<String> getRanking() {
		return ranking;
	}

	public void setRanking(ArrayList<String> ranking) {
		this.ranking = ranking;
	}

	public Meteo getMeteoDuJour() {
		return meteoDuJour;
	}

	public void setMeteoDuJour(Meteo meteoDuJour) {
		this.meteoDuJour = meteoDuJour;
	}

	public Meteo getMeteoDeDemain() {
		return meteoDeDemain;
	}

	public void setMeteoDeDemain(Meteo meteoDeDemain) {
		this.meteoDeDemain = meteoDeDemain;
	}

	public int getHeureDepuisDebutJeu() {
		return heureDepuisDebutJeu;
	}

	public void setHeureDepuisDebutJeu(int heureDepuisDebutJeu) {
		this.heureDepuisDebutJeu = heureDepuisDebutJeu;
	}

}
