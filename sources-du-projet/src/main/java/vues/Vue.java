package vues;

import controller.Controller;
import controller.Subject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import model.Model;

public class Vue extends Controller implements Observer{
	
	public Vue(String vue) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(vue));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			this.setTitle("ProjetMode");
			this.setResizable(false);
			this.setScene(scene);
			this.show();
			Model.getInstance().attach(this);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Subject subj, Canvas object) {
		Model.getInstance().getDf().setGc(object.getGraphicsContext2D());
		//Model.getInstance().setDf(new DessinFace( object, Model.getInstance()));
		Model.getInstance().affiche();
	}

	@Override
	public void update(Subject subj) {
		Model.getInstance().affiche();
		
	}
}