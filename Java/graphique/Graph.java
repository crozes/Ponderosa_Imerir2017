package graphique;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Graph extends Application {

    public void start(Stage primaryStage)  {
    	String time="bla";
    	String fric="lala";
    	String meteo="pluis";
    	/**creation de la fenetre*/
    	primaryStage.setTitle("limonade");
        Group root=new Group();
        Scene scene= new Scene(root,1920,640,Color.LIGHTBLUE);
        /**creation de panel*/
        BorderPane genPanel = new BorderPane();
        GridPane panelTop = new GridPane();
        panelTop.getColumnConstraints().add(new ColumnConstraints(scene.getWidth()-100));
        //panelTop.getColumnConstraints().add(new ColumnConstraints(scene.getWidth()));
        /**contenu de la page*/
        Label topLabel= new Label(); 
        String texte="\n"+time+"\n"+fric+"\n"+meteo;
        topLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); 
        topLabel.setStyle("-fx-background-color: gold; -fx-border-color: goldenrod;");
        topLabel.setText(texte);
        genPanel.setTop(panelTop);
        ComboBox listJoueur=new ComboBox();
        listJoueur.getItems().addAll(
        	"joueur 1",
        	"joueur 2",
        	"joueur 3"
       );
        panelTop.add(topLabel, 0, 0, 1, Integer.MAX_VALUE);
        panelTop.add(listJoueur, 2, 0);
        panelTop.setGridLinesVisible(true);
        /**affichage*/
        root.getChildren().add(genPanel);
        primaryStage.setScene(scene);
        primaryStage.show(); 
    }
    
	public static void main(String[] args) {
		launch(Graph.class,args);
   }
	
}
