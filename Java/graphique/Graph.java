package graphique;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import communication.Communication;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
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
import javafx.stage.Stage;
import les_mains.MainTest;
import outils.Global;

public class Graph extends Application  {
	String texte;
	int nbcol=10;
	int nblign=1;
	public String recup(){
	
		texte = Communication.getRecevoir(Global.URL_TEST_JSON_GET);

		JsonElement jelement = new JsonParser().parse(texte);
		JsonObject json = jelement.getAsJsonObject();



		System.out.println(json.toString());

		String weather = json.get("weather").getAsString();
		int day = json.get("day").getAsInt();
		float budget = json.get("budget").getAsFloat();
		System.out.println("Afficher string : " + weather + " int : " + day + " float :" + budget);
		return "meteo : " + weather + "\n jour : " + day + "\n budjet : " + budget;
	}
	public void gridPanel(int nbcol,int nblign,GridPane panel){
		for (int i = 0; i < nbcol; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / nbcol);
            panel.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < nblign; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / nblign);
            panel.getRowConstraints().add(rowConst);         
        }
        panel.setGridLinesVisible(true);
	}

    public void start(Stage primaryStage) {
    	/**creation de la fenetre*/
    	primaryStage.setTitle("limonade");
        Group root=new Group();//arriere de la fenetre
        Scene scene= new Scene(root,700,700,Color.LIGHTBLUE);//scene
        /**creation de panel*/
        VBox page=new VBox();
        GridPane upPanel = new GridPane();
        gridPanel(10,1,upPanel);
        GridPane genPanel = new GridPane();
        gridPanel(2,1,genPanel);
        page.getChildren().add(upPanel);
        page.getChildren().add(genPanel);
        /**contenu de la page*/
        String text="meteo : \n jour : \n budjet : ";//recup();
        Label topLabel= new Label(); 
        topLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
        topLabel.setStyle("-fx-background-color: white; -fx-border-color: white;");
        topLabel.setText(text);
        
        ///////////////////////////////////////contenu de la page///////////////////////////////////////
        for(int i=0;i<10;i++){
        	Circle rond=new Circle();
        	rond.setCenterX(1000f);
        	rond.setCenterY(1000f);
        	rond.centerXProperty();
        	rond.centerYProperty();
        	rond.setRadius(10*i);
        	rond.setFill(Color.rgb(100, 100, 100, 0.2));
        	genPanel.add(rond,0, 0);
        }
        ComboBox listJoueur=new ComboBox();
        listJoueur.getItems().addAll(
        	"joueur 1",
        	"joueur 2",
        	"joueur 3"
       );
        upPanel.add(topLabel, 0, 0);
        upPanel.add(listJoueur, 5, 0);
        
        
        /*Circle pnj =new Circle();
        pnj.setCenterX(1500);
        pnj.setCenterY(1500);
        pnj.setRadius(50);*/
        
        /**affichage*/
        
        root.getChildren().add(page);
        primaryStage.setScene(scene);
        primaryStage.show(); 
    }
    
	public static void main(String[] args) {
		launch(Graph.class,args);
   }
	
}