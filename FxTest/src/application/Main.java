package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	BorderPane rootLayout;
	
	@Override
	public void start(Stage primaryStage) {
	
		try {
			
			FXMLLoader loader = new FXMLLoader();
			
			loader.setLocation(Main.class.getResource("RootUI.fxml"));
			
			rootLayout = (BorderPane)loader.load();
			
			Scene scene = new Scene(rootLayout);
			
			primaryStage.setScene(scene);
			
			primaryStage.setResizable(false);
			primaryStage.setTitle("너흥다깨 테스트 클라이언트");
			
			//primaryStage.getIcons().add(new Image("icon.png"));
			
			primaryStage.show();
			
			showAnchor();
			
		} catch(Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public void showAnchor() {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("MainStage.fxml"));
		
		try {
			
			AnchorPane localLayout = (AnchorPane) loader.load();
			rootLayout.setCenter(localLayout);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
}
