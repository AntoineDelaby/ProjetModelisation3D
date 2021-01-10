package application;
import javafx.application.Application;
import javafx.stage.Stage;
import vues.Vue;

/**
 * Le Main.
 * <p>Appelé en premier au démarrage de l'application.</p>
 * <ul><li>Va créer la scène et la vue.</li></ul>
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Mathéo Gallego
 */
public class Main extends Application {
	
	/**
	 * Va Créer la Scène.
	 */
	@Override
	public void start(Stage primaryStage) {
		new Vue("Vue.fxml");
	}
	
	/**
	 * Va Démarrer la Scène
	 * @param args
	 * 				Paramètres de démarrage de la Scène.
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
}
