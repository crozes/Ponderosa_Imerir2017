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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import les_mains.MainTest;
import outils.Global;

public class Graph extends Application  {
	Label Label2= new Label(); 
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
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100/nbcol);
            panel.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < nblign; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100/nblign);
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
        Scene scene= new Scene(root,700,700,Color.LIGHTBLUE);//scene
        /*creation de panel*/
        VBox page=new VBox();
        GridPane upPanel = new GridPane();
        gridPanel(6,1,upPanel);
        GridPane genPanel = new GridPane();
        ////////////seconde ligne du grid
	    ColumnConstraints colConst = new ColumnConstraints(500);
	    //colConst.setPercentWidth(70);
	    genPanel.getColumnConstraints().add(colConst);
	    ColumnConstraints colConst1 = new ColumnConstraints(150);
	    //colConst1.setPercentWidth(30);
	    genPanel.getColumnConstraints().add(colConst);
	    RowConstraints rowConst = new RowConstraints(500);
	    //rowConst.setPercentHeight(100);
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
        topLabel.setStyle("-fx-background-color: white; -fx-border-color: black;");
        topLabel.setText(MJB);
        upPanel.add(topLabel, 0, 0);
        /////////////////case2///////////////
        String heure=recupHour();
        
        Label2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
        Label2.setStyle("-fx-background-color: white; -fx-border-color: black;-fx-alignment:CENTER; -fx-font-size: 20pt ;");
        Label2.setText(heure);
        upPanel.add(Label2, 1, 0);
        /////////////////case5//////////////
        ComboBox listJoueur=new ComboBox();
        
         ArrayList joueur=new ArrayList();
         for(int i=0;i<=recupNbJoueur/*()*/;i++){
        	
     		joueur.add("joueur"+i);
     	}
		listJoueur.getItems().addAll(
        	joueur
       );
		listJoueur.setValue("joueur");
        upPanel.add(listJoueur, 3, 0);
        //case L2 1
        for(int i=0;i<10;i++){
        	Circle rond=new Circle();
        	rond.setCenterX(1000f);
        	rond.setCenterY(1000f);
        	rond.centerXProperty();//
        	rond.centerYProperty();
        	rond.setRadius(10*i);
        	rond.setFill(Color.rgb(100, 100, 100, 0.2));
        	genPanel.add(rond,0, 0);
        }
        MAJ lol = new MAJ();
        lol.start();
        ////////////////////////////////////////////affichage carte et information gaphique////////
        BackgroundImage myBI= new BackgroundImage(new Image("http://www.cartesfrance.fr/cartes/carte_france_simple.png",500,500,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                  BackgroundSize.DEFAULT);
        //then you set to your node
        genPanel.setBackground(new Background(myBI));
        /*Image image4 = new Image("http://www.cartesfrance.fr/cartes/carte_france_simple.png", 0, 100, false, false);
        //genPanel.add(image4, 0,0);
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
    			Platform.runLater(()->Label2.setText(heure));
    		}//*/
    	}
    }
    
    //////////////////////////////////main///////////////////////////////////////////////
	public static void main(String[] args) {
		 
		launch(Graph.class,args);
   }
	
	
}
