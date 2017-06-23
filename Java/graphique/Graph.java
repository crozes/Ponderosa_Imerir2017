package graphique;


import java.util.ArrayList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import communication.Communication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import outils.Global;

public class Graph extends Application  {
	Label label2= new Label();
	int carte_x=800;
	int carte_y=500;
	/**recuperation d'info depuis le serveur*/
	////////////////////////////////////////recupMJB//////////////////////////////////////
	/**recuperation de la meteo du jour et du budjet*/
	private String recupMJB(){
		String texte = Communication.getRecevoir(Global.URL_TEST_JSON_GET);
		JsonElement jelement = new JsonParser().parse(texte);
		JsonObject json = jelement.getAsJsonObject();
		String weather = json.get("weather").getAsString();
		int day = json.get("day").getAsInt();
		float budget = json.get("budget").getAsFloat();
		return "meteo : " + weather + "\n jour : " + day + "\n budjet : " + budget;
	}
	/////////////////////////////////////////recupNbJoueur//////////////////////////////
	private int recupNbJoueur(){
		String texte = Communication.getRecevoir("https://ponderosaproject.herokuapp.com/nbrPlayer");
		JsonElement jelement = new JsonParser().parse(texte);
		JsonObject json = jelement.getAsJsonObject();
		int nbJoueur = json.get("nbrPlayer").getAsInt();
		return nbJoueur;
	
	}
	////////////////////////////////////////recupHour//////////////////////////////////
	/**recuperation de l'heure*/
	private String recupHour(){
		String texte = Communication.getRecevoir("https://ponderosaproject.herokuapp.com/getHour");
		JsonElement jelement = new JsonParser().parse(texte);
		JsonObject json = jelement.getAsJsonObject();
		int time = json.get("time").getAsInt();
		String date=hourJour(time);
		return date;
	}
	////////////////////////////////////////hourJour/////////////////////////////////////
	/**trensformation time en jour et heure*/
	private String hourJour(int time){
		int jour=time/24;
		int heure=time-(24*jour);
		String.valueOf(jour);
		String.valueOf(heure);
		return jour+"jour \n\n"+heure+"h00";
	}
	/////////////////////////////////////////gridpanel/////////////////////////////////
	/**crée les case du gridpane*/
	private void gridPanel(int nbcol,int nblign,GridPane panel){
		for (int i = 0; i < nbcol; i++) {
            ColumnConstraints colConst = new ColumnConstraints(carte_x/nbcol);
            panel.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < nblign; i++) {
            RowConstraints rowConst = new RowConstraints(100);
            panel.getRowConstraints().add(rowConst);         
        }
        panel.setGridLinesVisible(true);
	}
	/////////////////////////////////////////start///////////////////////////////////
				/**affichage*/
    public void start(Stage primaryStage) {  
    	/*variable de test*/
    	int recupNbJoueur=10;
    	/*creation de la fenetre*/
    	primaryStage.setTitle("limonade");
        Group root=new Group();//arriere de la fenetre
        Scene scene= new Scene(root,1000,600,Color.LIGHTBLUE);//scene
        /*creation de panel*/
        VBox page=new VBox();
        GridPane upPanel = new GridPane();
        gridPanel(2,1,upPanel);
        ColumnConstraints colConstup = new ColumnConstraints(200);
	    upPanel.getColumnConstraints().add(colConstup);
        GridPane genPanel = new GridPane();
        ////////////seconde ligne du grid
	    ColumnConstraints colConst = new ColumnConstraints(carte_x);
	    genPanel.getColumnConstraints().add(colConst);
	    ColumnConstraints colConst1 = new ColumnConstraints(200);
	    genPanel.getColumnConstraints().add(colConst1);
	    RowConstraints rowConst = new RowConstraints(carte_y);
	    genPanel.getRowConstraints().add(rowConst);
	    genPanel.setGridLinesVisible(true);
	    ////////////integration des panel dans la scene
        page.getChildren().add(upPanel);
        page.getChildren().add(genPanel);
        /*contenu de la page*/
        /////////////////case1////////////////
        String MJB=recupMJB();
        Label topLabel= new Label(); 
        topLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
        topLabel.setStyle("-fx-background-color: white; -fx-border-color: black;-fx-alignment:CENTER; -fx-font-size: 15pt;");
        topLabel.setText(MJB);
        upPanel.add(topLabel, 0, 0);
        /////////////////case2///////////////
        String heure="heure";//recupHour();
        
        label2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
        label2.setStyle("-fx-background-color: white; -fx-border-color: black;-fx-alignment:CENTER; -fx-font-size: 20pt ;");
        label2.setText(heure);
        upPanel.add(label2, 1, 0);
        /////////////////case4//////////////
        ComboBox listJoueur=new ComboBox();
        
         ArrayList joueur=new ArrayList();
         for(int i=0;i<=recupNbJoueur/*()*/;i++){/////////////////////////////	////////////////////////////////////////
     		joueur.add("joueur"+i);
     	}
		listJoueur.getItems().addAll(
        	joueur
       );
		listJoueur.setValue("joueur");
        upPanel.add(listJoueur, 2, 0);
        /////////////////////////////////case carte/////////////////////////////
        Group carte=new Group();
        genPanel.add(carte, 0, 0);
        Canvas canvas=new Canvas(carte_x,carte_y);
        GraphicsContext gc=canvas.getGraphicsContext2D();
        //creation de la population
        for(int i=0;i<100;i++){
        	gc.fillOval(Math.random()*(carte_x-0), Math.random()*(carte_y-0), 10, 10);
        }
        Circle rond=new Circle(100,100,50);
        rond.setCenterX(800);
        rond.setCenterY(500);
        rond.setFill(Color.rgb(1, 1, 1, 0.4));
        genPanel.add(rond, 0, 0);
    	carte.getChildren().add(canvas);
        MAJ miseAJour = new MAJ();
        miseAJour.start();
        ////////////////////////////////////////////affichage carte et information gaphique////////
        BackgroundImage myBI= new BackgroundImage(new Image("http://cartography.oregonstate.edu/index_files/stacks_image_3198.jpg",carte_x,carte_y,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                  BackgroundSize.DEFAULT);
        genPanel.setBackground(new Background(myBI));
        
        /*affichage*/
        root.getChildren().add(page);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /////////////////////////////////threadRun///////////////////////////////////////////
    /**met a jour l'heure et la date*/
    private class MAJ extends Thread{
    	public void run(){
    		System.out.println("coucou");
    		/*while(true)
    		{
    			try { 
    				  Thread.sleep(1000);
    				}
    				catch (InterruptedException exception) {
    				  exception.printStackTrace();
    				}
    			String heure=recupHour();
    			Platform.runLater(()->label2.setText(heure));
    		}//*/
    	}
    }
    
    //////////////////////////////////main///////////////////////////////////////////////
	public static void main(String[] args) {
		 
		launch(Graph.class,args);
   }
	
	
}
