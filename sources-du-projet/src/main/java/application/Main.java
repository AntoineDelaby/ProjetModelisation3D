package application;
import Controllers.Controller;
import Model.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		Model model = new Model();
		new Controller(model);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
