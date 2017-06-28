package graphique;

import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import gestion_population.TheGame;

import communication.Communication;
import gestion_population.TheGame;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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
import outils.Meteo;
import testPoubelle.Main_pour_blaguer;




public  class Graph extends Application {
	Label label2= new Label();
	int carte_x=800;
	int carte_y=500;
	Canvas canvas=new Canvas(carte_x,carte_y);
	TheGame game=Main_pour_blaguer.laPartie;
	
	/////////////////////////////instalation des citoyen
	private GraphicsContext migration(int nbPop){
		
		//game.getMapDeLaPopulation().genererPopulation(carte_x, 0, carte_y, 0, nbPop, game.getMeteoDuJour(), Meteo.soir, game.getListeDesStand());
		GraphicsContext gc=canvas.getGraphicsContext2D();
		//population
		for(int i=0;i<nbPop;i++){
			gc.setFill(Color.rgb(0, 0, 0, 1));
        	gc.fillOval(Math.random()*(carte_x-0), Math.random()*(carte_y-0), 10, 10);
        }
		//joueur
		for(int j=0;j<game.getRanking().size();j++){
			gc.setFill(Color.rgb(100, 255, 100, 0.7));
	        gc.fillOval(Math.random()*(carte_x-0),/* game.getLaMapDesObjets().getCoordonnees().getLatitude()*/Math.random()*(carte_y-0), 100, 100);
	        
		}
		//pub
		for(int j=0;j<game.getRanking().size();j++){
			gc.setFill(Color.rgb(255, 100, 100, 0.7));
			gc.fillOval(/*game.getLaMapDesObjets().getCoordonnees().getLatitude()*/Math.random()*(carte_x-0),/* game.getLaMapDesObjets().getCoordonnees().getLatitude()*/Math.random()*(carte_y-0), 50, 50);
	        
		}
		return gc;
	}

	/////////////////////////////////////////start///////////////////////////////////
				/**affichage*/
	
    public void start(Stage primaryStage ) { 
 
    	/*creation de la fenetre*/
    	primaryStage.setTitle("limonade.io");
        Group root=new Group();//arriere de la fenetre
        Scene scene= new Scene(root,1000,600,Color.LIGHTBLUE);//scene
        
        
        /*creation du premier panel*/
        VBox page=new VBox();
        GridPane upPanel = new GridPane();
        ColumnConstraints colConstUp = new ColumnConstraints(carte_x/2);
        upPanel.getColumnConstraints().add(colConstUp);
        ColumnConstraints colConstUp1 = new ColumnConstraints(carte_x/2);
	    upPanel.getColumnConstraints().add(colConstUp1);
        RowConstraints rowConst = new RowConstraints(100);
        upPanel.getRowConstraints().add(rowConst);    
        GridPane genPanel = new GridPane();
        
        ////////////seconde ligne du grid
	    ColumnConstraints colConstGen = new ColumnConstraints(carte_x);
	    genPanel.getColumnConstraints().add(colConstGen);
	    ColumnConstraints colConstGen1 = new ColumnConstraints(200);
	    genPanel.getColumnConstraints().add(colConstGen1);
	    RowConstraints rowConstGen = new RowConstraints(carte_y);
	    genPanel.getRowConstraints().add(rowConstGen);
	    //genPanel.setGridLinesVisible(true);
	    
	    ////////////integration des panel dans la scene
        page.getChildren().add(upPanel);
        page.getChildren().add(genPanel);
        
        /*contenu de la page*/
        
        //////////////////////////////////////case1///////////////////////////
        Label topLabel= new Label(); 
        topLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
        topLabel.setStyle("-fx-background-color: white; -fx-border-color: black;-fx-alignment:CENTER; -fx-font-size: 15pt;");
        topLabel.setText("meteo : "+game.getMeteoDuJour()+"\nnb de player : "+game.getRanking().size()+"\npopulation : ");//+game.getMapDeLaPopulation());//affichage
        upPanel.add(topLabel, 0, 0);
        
        ////////////////////////////////////case2///////////////////////////////
        label2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
        label2.setStyle("-fx-background-color: white; -fx-border-color: black;-fx-alignment:CENTER; -fx-font-size: 20pt ;");
        int jour=game.getHeureDepuisDebutJeu()%24;
        int heure=game.getHeureDepuisDebutJeu()-(24*jour);
        label2.setText(String.valueOf("jour : "+jour+ "\nheure : "+heure));//affichage
        upPanel.add(label2, 1, 0);
        
        /////////////////////////////////case carte/////////////////////////////
        Group carte=new Group();
        genPanel.add(carte, 0, 0);
        /**carte :
         * http://poj.b3dgs.com/images/course_engine/map.png
         * http://cartography.oregonstate.edu/index_files/stacks_image_3198.jpg
         * http://jeuxserieux.ac-creteil.fr/wp-content/uploads/2011/12/Zelda-Map.png
         */
        BackgroundImage myBI= new BackgroundImage(new Image("http://cartography.oregonstate.edu/index_files/stacks_image_3198.jpg",carte_x,carte_y,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                  BackgroundSize.DEFAULT);
        genPanel.setBackground(new Background(myBI));
        
        
        ////////////////////////////////case infoJoueur/////////////////////////////////////////
        TreeItem<String> listJoueur = new TreeItem<String>("id joueur");//affichage
        for(int i=0;i<game.getRanking().size();i++){
        	 TreeItem<String> infoJoueur = new TreeItem<String>(game.getRanking().get(i));//affichage
            listJoueur.setExpanded(true);
            TreeItem<String> posJoueur = new TreeItem<String>("position");//affichage
            	posJoueur.getChildren().addAll(
                        new TreeItem<String>("pos X : "+game.getListeMapItemJoueur().get("location")),//affichage
                        new TreeItem<String>("pos Y")//affichage
                    );
            
            TreeItem<String> posPub =new TreeItem<String>("pub");
            	int nbrPub=game.getListeMapItemJoueur().size();
            	for(int k=0;k<nbrPub;k++){
            		
            			TreeItem<String> pub = new TreeItem<String>("pub"+k);//affichage
            	        pub.getChildren().addAll(
            	        		new TreeItem<String>("pos X"),//affichage
                                new TreeItem<String>("pos Y")//affichage
            	                );
                        posPub.getChildren().addAll(pub);
            	}
            
            infoJoueur.getChildren().addAll(
            	new TreeItem<String>("rank :"+(i+1)),
                new TreeItem<String>("budjet : "+game.getListePlayerInfo().get(game.getRanking().get(i)).getCash()),//affichage
                new TreeItem<String>("vente : "+ game.getListePlayerInfo().get(game.getRanking().get(i)).getSales()),//affichage
                new TreeItem<String>("profit : "+game.getListePlayerInfo().get(game.getRanking().get(i)).getProfit())//affichage
            );
            TreeItem<String> drinkInfo = new TreeItem<String>("Drink Info");//affichage
            drinkInfo.getChildren().addAll(
                    new TreeItem<String>("nom : "),//affichage
                    new TreeItem<String>("prix : "),//affichage
                    new TreeItem<String>("alchool : "),//affichage
                    new TreeItem<String>("froid : ")//affichage
                );
            infoJoueur.getChildren().addAll(posJoueur);
            infoJoueur.getChildren().addAll(posPub);
            infoJoueur.getChildren().addAll(drinkInfo);
            listJoueur.getChildren().addAll(infoJoueur);
        }

        TreeView<String> treeView = new TreeView<String>(listJoueur);
        genPanel.add(treeView, 1, 0);
        //creation de la population
        migration(150);
    	carte.getChildren().add(canvas);
        MAJ miseAJour = new MAJ();
        miseAJour.start();
        /////////////////affichage carte et information gaphique////////////////////
        
        
        /*affichage*/
        root.getChildren().add(page);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /////////////////////////////////threadRun///////////////////////////////////////////
    /**met a jour l'heure et la date*/
    private class MAJ extends Thread{
    	public void run(){
    		while(true)
    		{
    			try { 
    				  Thread.sleep(1000);
    				}
    				catch (InterruptedException exception) {
    				  exception.printStackTrace();
    				}
    	        int jour=game.getHeureDepuisDebutJeu()%24;
    	        int heure=game.getHeureDepuisDebutJeu()-(24*jour);
    			Platform.runLater(()->label2.setText(String.valueOf("jour : "+jour+ "\nheure : "+heure)));
    			boolean refresh=true;
    			if((game.getHeureDepuisDebutJeu()%12+1)==0){
    				migration(150).clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    				if(refresh==true){
    					migration(150);
    				}
    
    			}else{
    				refresh=false;
    			}	
    		}
    	}
    }
}
