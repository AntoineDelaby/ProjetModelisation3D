package application;
import javafx.application.Application;
import javafx.stage.Stage;
import vues.Vue;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		new Vue("Vue.fxml");
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
