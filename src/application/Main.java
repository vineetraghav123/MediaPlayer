package application;
	
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	 private double xOffset = 0;
	    private double yOffset = 0;
	    public static Stage s;
	@Override
	public void start(Stage primaryStage) {
		s=primaryStage;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Latest.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			root.setOnMousePressed(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	
	                xOffset = event.getSceneX();
	                yOffset = event.getSceneY();
	            }
	        });
			
			
	        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
	        	 
	            @Override
	            public void handle(MouseEvent event) {
	            	primaryStage.setOpacity(0.7);
	                primaryStage.setX(event.getScreenX() - xOffset);
	                primaryStage.setY(event.getScreenY() - yOffset);
	            }
	        });
	        root.setOnMouseReleased(new EventHandler<MouseEvent>() {
	        	
	            @Override
	            public void handle(MouseEvent event) {
	            	
	              primaryStage.setOpacity(1.0);
	            }
	        });
	       
			
			 primaryStage.initStyle(StageStyle.UNDECORATED);
				
			 primaryStage.getIcons().add(new Image("/photo/musiclogo1.png"));
			primaryStage.setScene(scene);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
