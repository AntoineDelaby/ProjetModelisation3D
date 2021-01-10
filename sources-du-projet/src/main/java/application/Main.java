package application;
import javafx.application.Application;
import javafx.stage.Stage;
import vues.Vue;

/**
 * Le Main.
 * <p>Appel� en premier au d�marrage de l'application.</p>
 * <ul><li>Va cr�er la sc�ne et la vue.</li></ul>
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Math�o Gallego
 */
public class Main extends Application {
	
	/**
	 * Va Cr�er la Sc�ne.
	 */
	@Override
	public void start(Stage primaryStage) {
		new Vue("Vue.fxml");
	}
	
	/**
	 * Va D�marrer la Sc�ne
	 * @param args
	 * 				Param�tres de d�marrage de la Sc�ne.
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
}
