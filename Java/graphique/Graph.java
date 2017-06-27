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
import testPoubelle.Main_pour_blaguer;

public  class Graph extends Application {
	Label label2= new Label();
	int carte_x=800;
	int carte_y=500;
	Canvas canvas=new Canvas(carte_x,carte_y);
	TheGame game=Main_pour_blaguer.laPartie;
	
	/////////////////////////////instalation des citoyen
	private GraphicsContext migration(int nbPop){
		
		GraphicsContext gc=canvas.getGraphicsContext2D();
		
		for(int i=0;i<nbPop;i++){
			gc.setFill(Color.rgb(0, 0, 0, 1));
        	gc.fillOval(Math.random()*(carte_x-0), Math.random()*(carte_y-0), 10, 10);
        }
        gc.setFill(Color.rgb(255, 100, 100, 0.7));
        gc.fillOval(150, 200, 100, 100);
        return gc;
	}

	/////////////////////////////////////////start///////////////////////////////////
				/**affichage*/
	
    public void start(Stage primaryStage ) { 
 
    	/*creation de la fenetre*/
    	primaryStage.setTitle("limonade.io");
        Group root=new Group();//arriere de la fenetre
        Scene scene= new Scene(root,1000,600,Color.LIGHTBLUE);//scene
        
        System.out.println(game.getLaMapDesObjets().toString());
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
	    genPanel.setGridLinesVisible(true);
	    
	    ////////////integration des panel dans la scene
        page.getChildren().add(upPanel);
        page.getChildren().add(genPanel);
        
        /*contenu de la page*/
        
        /////////////////case1////////////////
        String MJB="meteo : "+game.getMeteoDuJour()+"\nnb de player : "+game.getRanking().size()+"\npopulation : ??";
        Label topLabel= new Label(); 
        topLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
        topLabel.setStyle("-fx-background-color: white; -fx-border-color: black;-fx-alignment:CENTER; -fx-font-size: 15pt;");
        topLabel.setText(MJB);
        upPanel.add(topLabel, 0, 0);
        
        /////////////////case2///////////////
        label2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
        label2.setStyle("-fx-background-color: white; -fx-border-color: black;-fx-alignment:CENTER; -fx-font-size: 20pt ;");
        int jour=game.getHeureDepuisDebutJeu()%24;
        int heure=game.getHeureDepuisDebutJeu()-(24*jour);
        label2.setText(String.valueOf("jour : "+jour+ "\nheure : "+heure));
        upPanel.add(label2, 1, 0);
        
        /////////////////////////////////case carte/////////////////////////////
        Group carte=new Group();
        genPanel.add(carte, 0, 0);
        ////////////////////////////////case infoJoueur/////////////////////////////////////////
        TreeItem<String> listJoueur = new TreeItem<String>("id joueur");
        for(int i=0;i<game.getRanking().size();i++){
            TreeItem<String> infoJoueur = new TreeItem<String>(game.getRanking().get(i));
            TreeItem<String> drinkInfo = new TreeItem<String>("Drink Info");
           	listJoueur.setExpanded(true);
            infoJoueur.getChildren().addAll(
                new TreeItem<String>("infoJoueur(cash)"),
                new TreeItem<String>("infoJoueur(vente)"),
                new TreeItem<String>("infoJoueur(profit)")
            );
            drinkInfo.getChildren().addAll(
                    new TreeItem<String>("nom"),
                    new TreeItem<String>("prix"),
                    new TreeItem<String>("alchool"),
                    new TreeItem<String>("froid")
                );
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
    		while(true)
    		{
    			try { 
    				  Thread.sleep(1000);
    				}
    				catch (InterruptedException exception) {
    				  exception.printStackTrace();
    				}
    			Platform.runLater(()->label2.setText(String.valueOf("jour\nheure:"+game.getHeureDepuisDebutJeu())));
    			boolean refresh=true;
    			if((game.getHeureDepuisDebutJeu()%12)==0){
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
    
    //////////////////////////////////main///////////////////////////////////////////////
	public static void main(String[] args) {
		launch(Graph.class,args);
   }
	
}
