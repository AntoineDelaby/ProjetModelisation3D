package application;
import javafx.application.Application;
import javafx.stage.Stage;
import vues.Vue;

/**
 * Le Main.
 * <p><ul><li>Appel� en premier au d�marrage de l'application.</li>
 * <li>Va cr�er la sc�ne et la vue.</li></ul></p>
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
