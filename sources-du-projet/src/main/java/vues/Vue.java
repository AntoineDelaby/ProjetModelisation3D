package vues;

import controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Vue extends Controller{
		
	public Vue(String vue) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(vue));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			this.setTitle("ProjetMode");
			this.setResizable(false);
			this.setScene(scene);
			this.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}