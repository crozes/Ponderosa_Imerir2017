package graphique;



import java.util.ArrayList;

import com.sun.org.apache.xpath.internal.Arg;

import gestion_population.Agent;
import gestion_population.TheGame;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
import javafx.stage.Stage;
import les_mains.Main_de_test_final_parceque_jaime_bien_faire_des_main;





public class Graph extends Application {
	TheGame game=Main_de_test_final_parceque_jaime_bien_faire_des_main.laPartie ;
	
	float carte_x=800;//game.getRegion().getLongitudeMax();
	float carte_y=500;//game.getRegion().getLatitudeMax();
	Canvas canvas=new Canvas(carte_x,carte_y);
	
	/*public Graph(TheGame partie){
		this.game=partie;
	}
	
	public void init(String[] args) {
		//game=partie;
		launch(Graph.class,args);
		
   }*/
	
	/////////////////////////////instalation des citoyen
	public GraphicsContext migration(){
		
		//game.getMapDeLaPopulation().genererPopulation(carte_x, 0, carte_y, 0, nbPop, game.getMeteoDuJour(), Meteo.soir, game.getListeDesStand());
		GraphicsContext gc=canvas.getGraphicsContext2D();
		//population
		for(int i=0;i<game.getMapDeLaPopulation().getPopulation().size();i++){
			gc.setFill(Color.rgb(0, 0, 0, 1));
        	gc.fillOval(game.getMapDeLaPopulation().getPopulation().get(i).getLatitude(), game.getMapDeLaPopulation().getPopulation().get(i).getLongitude(), 10, 10);
        	
        }
		//joueur
		for(int j=0;j<game.getRanking().size();j++){
			gc.setFill(Color.rgb(100, 255, 100, 0.7));
	        gc.fillOval(game.getListeDesStand().get(game.getRanking().get(j)).getCoordonnees().getLatitude()+400, game.getListeDesStand().get(game.getRanking().get(j)).getCoordonnees().getLongitude()+250,100, 100);
	        
		}
		//pub
		for(int k=0;k<game.getRanking().size();k++){
			for(int l=0;l<game.getListeMapItemJoueur().get(game.getRanking().get(k)).size();l++){
				gc.setFill(Color.rgb(255, 100, 100, 0.7));
				//gc.fillOval(400,/***/250, 50,50);//game.getListeDesStand().get(l).getInfluence(), game.getListeDesStand().get(l).getInfluence())
				double latitude=Double.parseDouble(String.valueOf(game.getListeMapItemJoueur().get(game.getRanking().get(k)).get(l).getLatitude()+400));
				double longitude=Double.parseDouble(String.valueOf(game.getListeMapItemJoueur().get(game.getRanking().get(k)).get(l).getLongitude()+250));
				double influence=Double.parseDouble(String.valueOf(game.getListeMapItemJoueur().get(game.getRanking().get(k)).get(l).getInfluence()));
				gc.fillOval(latitude,longitude,influence*10,influence*10);
				}
		}
		return gc;
	}
	
	/////////////////////////////////////////info player/////////////////////////////
	private TreeItem<String> infoPlayer(){
		TreeItem<String> listJoueur = new TreeItem<String>("id joueur");
        for(int i=0;i<game.getRanking().size();i++){
        	 TreeItem<String> infoJoueur = new TreeItem<String>(game.getRanking().get(i));
            listJoueur.setExpanded(true);
            TreeItem<String> posJoueur = new TreeItem<String>("position");
            	posJoueur.getChildren().addAll(
                        new TreeItem<String>("pos X : "+game.getListeDesStand().get(game.getRanking().get(i)).getCoordonnees().getLatitude()),
                        new TreeItem<String>("pos Y : "+game.getListeDesStand().get(game.getRanking().get(i)).getCoordonnees().getLongitude())
                    );
            
            TreeItem<String> posPub =new TreeItem<String>("pub");
            	for(int k=0;k<game.getListeMapItemJoueur().get(game.getRanking().get(i)).size();k++){
            		
            			TreeItem<String> pub = new TreeItem<String>("pub"+k);
            	        pub.getChildren().addAll(
            	        		new TreeItem<String>("pos X : "+game.getListeMapItemJoueur().get(game.getRanking().get(i)).get(k).getLatitude()),
                                new TreeItem<String>("pos Y : "+game.getListeMapItemJoueur().get(game.getRanking().get(i)).get(k).getLatitude())
            	                );
            	        
                        posPub.getChildren().addAll(pub);
            	}
            
            infoJoueur.getChildren().addAll(
            	new TreeItem<String>("rank :"+(i+1)),
                new TreeItem<String>("budjet : "+game.getListePlayerInfo().get(game.getRanking().get(i)).getCash()+"€"),
                new TreeItem<String>("vente : "+ game.getListePlayerInfo().get(game.getRanking().get(i)).getSales()+"€"),
                new TreeItem<String>("profit : "+game.getListePlayerInfo().get(game.getRanking().get(i)).getProfit()+"€")
            );
            for(int k=0;k<game.getListeDesDrinkInfo().get(game.getRanking().get(i)).size();k++){
            	TreeItem<String> drinkInfo = new TreeItem<String>(game.getListeDesDrinkInfo().get(game.getRanking().get(i)).get(k).getName());
                drinkInfo.getChildren().addAll(
                        new TreeItem<String>("prix : "+game.getListeDesDrinkInfo().get(game.getRanking().get(i)).get(k).getPrice()+"€"),//affichage
                        new TreeItem<String>("alchool : "+String.valueOf(game.getListeDesDrinkInfo().get(game.getRanking().get(i)).get(k).getIsHasAlcohol())),
                        new TreeItem<String>("froid : "+String.valueOf(game.getListeDesDrinkInfo().get(game.getRanking().get(i)).get(k).getIsCold()))
                    );
                infoJoueur.getChildren().addAll(drinkInfo);
            }
            infoJoueur.getChildren().addAll(posJoueur);
            infoJoueur.getChildren().addAll(posPub);
            listJoueur.getChildren().addAll(infoJoueur);
        }
		return listJoueur;

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
        topLabel.setText("meteo : "+game.getMeteoDuJour()+"\nnb de player :" +game.getRanking().size()+"\npopulation : "+(int)game.getMapDeLaPopulation().getNombreDeClient());
        upPanel.add(topLabel, 0, 0);
        
        ////////////////////////////////////case2///////////////////////////////
        Label label2= new Label();
        label2.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
        label2.setStyle("-fx-background-color: white; -fx-border-color: black;-fx-alignment:CENTER; -fx-font-size: 20pt ;");
        int jour=game.getHeureDepuisDebutJeu()/24;
        int heure=game.getHeureDepuisDebutJeu()%24;
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
        BackgroundImage myBI= new BackgroundImage(new Image("image/map.png",carte_x,carte_y,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                  BackgroundSize.DEFAULT);
        genPanel.setBackground(new Background(myBI));
        
        
        ////////////////////////////////case infoJoueur/////////////////////////////////////////
        
        TreeView<String> treeView = new TreeView<String>(infoPlayer());
        genPanel.add(treeView, 1, 0);
        //creation de la population
        migration();
    	carte.getChildren().add(canvas);
        threadGraph miseAJour = new threadGraph(label2);
        miseAJour.start();
        
        /////////////////affichage carte et information gaphique////////////////////
        
        /*affichage*/
        root.getChildren().add(page);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
   
    private class threadGraph extends Thread{
    	Label label2;
    	threadGraph(Label label){
    		this.label2=label;
    	}
    	public void run(){
    		while(true)
    		{
    			try { 
    				  Thread.sleep(1000);
    				}
    				catch (InterruptedException exception) {
    				  exception.printStackTrace();
    				}
    	        int jour=game.getHeureDepuisDebutJeu()/24;
    	        int heure=game.getHeureDepuisDebutJeu()%24;
    			Platform.runLater(()->label2.setText(String.valueOf("jour : "+jour+ "\nheure : "+heure)));    			
    			if(outils.Global.uneFoisAfficherLaCarte==true){
    				migration().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    				migration();
    				infoPlayer();
    				outils.Global.uneFoisAfficherLaCarte=false;
    			}else{
    				
    			}		
    		}
    	}
    }
}