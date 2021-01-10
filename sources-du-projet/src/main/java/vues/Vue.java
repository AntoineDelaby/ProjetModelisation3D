package vues;

import controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * La Vue est la classe permettant l'affichage de la Sc�ne.
 * De plus, Vue h�rite de la classe Controller.
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Math�o Gallego
 */
public class Vue extends Controller{

	/**
	 * Constructeur Vue.
	 * <p>Va instancier une nouvelle Sc�ne avec un fichier fxml du nom de la ressource pass�e en param�tre.</p>
	 * @param vue
	 * 				Le nom de la ressource du fichier fxml.
	 */
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