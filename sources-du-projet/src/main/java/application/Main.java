package application;
import javafx.application.Application;
import javafx.stage.Stage;
import vues.vue;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		new vue();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
