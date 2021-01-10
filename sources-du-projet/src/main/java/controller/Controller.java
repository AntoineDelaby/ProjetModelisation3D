package controller;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import dessin.DessinFace;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.FileRead;
import model.Model;
import model.Subject;
import mouvement.Rotation;
import mouvement.Translation;
import mouvement.Zoom;
import vues.Observer;
import vues.Vue;

/**
 * Controller est la classe de gestion entre le Modèle et la Vue.
 * <p> Un Controller est caractérisé par les informations suivantes :</p>
 * <ul><li>Un "ColorPicker" pour  les lignes, avec lequel l'utilisateur intéragit.</li>
 * <li>Un "ColorPicker" pour  les faces, avec lequel l'utilisateur intéragit.</li>
 * <li>Un Label correspondant au nom du Fichier, suceptible d'être changé.</li>
 * <li>Un Label correspondant au nombre de Faces du fichier, suceptible d'être changé.</li>
 * <li>Un Label correspondant au nombre de Sommets du Fichier, suceptible d'être changé.</li>
 * <li>Un Label correspondant à la Date du Fichier, suceptible d'être changé.</li>
 * <li>Un Label correspondant à la Description du Fichier, suceptible d'être changé.</li>
 * <li>Une "ListView" correspondant à la liste des Fichiers disponibles, unique attribuée définitivement.</li>
 * <li>Un Canvas, unique attribué définitivement.</li>
 * <li>Un Slider de Rotation X, avec lequel l'utilisateur intéragit et fait varier ses valeurs.</li>
 * <li>Un Slider de Rotation Y, avec lequel l'utilisateur intéragit et fait varier ses valeurs.</li>
 * <li>Un Slider de Rotation Z, avec lequel l'utilisateur intéragit et fait varier ses valeurs.</li>
 * <li>Une "CheckBox" pour l'affichage de l'Éclairage, avec lequel l'utilisateur intéragit.</li>
 * <li>Une "CheckBox" pour l'affichage des Lignes, avec lequel l'utilisateur intéragit.</li>
 * <li>Une "CheckBox" pour l'affichage des Faces, avec lequel l'utilisateur intéragit.</li>
 * <li>Un "FileRead" pour récupérer toutes les infos du fichier PLY, unique suceptible d'être changé.</li>
 * <li>Un Modèle.</li>
 * <li>Un Dessinateur.</li></ul>
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Mathéo Gallego
 */
public class Controller extends Stage implements Initializable,Observer {
	/**
	 * Palette de Couleur pour les lignes.
	 * @see Controller#getLigne()
	 */
	@FXML private ColorPicker ligne ;
	/**
	 * Palette de Couleur pour les Faces.
	 * @see Controller#getFace()
	 */
	@FXML private ColorPicker face ;
	/**
	 * Le Nom du fichier.
	 * @see Controller#getNameFile()
	 */
	@FXML private Label nameFile;
	/**
	 * Le Nombre de Faces.
	 */
	@FXML private Label fxml_nbFaces;
	/**
	 * Le Nombre de Sommets.
	 */
	@FXML private Label fxml_nbSommets;
	/**
	 * La Date du fichier.
	 */
	@FXML private Label dateFile;
	/**
	 * La Description du fichier.
	 */
	@FXML private Label description;
	/**
	 * La liste des fichiers disponibles.
	 */
	@FXML private ListView<String> listView;
	/**
	 * Le Canvas.
	 */
	@FXML private Canvas canvas;
	/**
	 * Le Slider de Rotation X.
	 */
	@FXML private Slider slidx;
	/**
	 * Le Slider de Rotation Y.
	 */
	@FXML private Slider slidy;
	/**
	 * Le Slider de Rotation Z.
	 */
	@FXML private Slider slidz;
	/**
	 * La CheckBox pour l'affichage de l'Eclairage.
	 * @see Controller#getAffichageEclairage()
	 */
	@FXML private CheckBox affichageEclairage;
	/**
	 * La CheckBox pour l'affichage des Lignes.
	 * @see Controller#getAffichageLignes()
	 */
	@FXML private CheckBox affichageLignes;
	/**
	 * La CheckBox pour l'affichage des Faces.
	 * @see Controller#getAffichageFaces()
	 */
	@FXML private CheckBox affichageFaces;
	/**
	 * Un FileRead.
	 */
	private FileRead fr;
	/**
	 * Un Modèle.
	 */
	protected Model model;
	/**
	 * Un Dessinateur.
	 * @see Controller#getDf()
	 */
	private DessinFace df;
	
	/**
	 * Initialise de Controller.
	 * @param arg0
	 * 				Une URL.
	 * @param arg1
	 * 				Un RessourceBundle.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.model = Model.getInstance();
		this.model.attach(this);
		this.df = new DessinFace(canvas);
		if(listView != null) {
			listView.getItems().addAll(model.filterList());
			listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		}
		Controller principalControl = this.model.getAttached().get(0);
		if(!this.equals(principalControl) && principalControl.getNameFile() != null) {
			this.affiche();
			this.update();
		}
		if(this.listView == null && this.nameFile != null) {
			this.affiche();
		}
		for(Controller c : this.model.getAttached()) {
			System.out.println(c + "|" + c.getDf() + "|" + c.equals(null));
		}
		System.out.println("-------------------------------------------------------------------");
	}

	/**
	 * Change la Couleur des Faces et des Lignes du Modèle.
	 */
	public void changeLineAndFacesColor() {
		Controller principalControl = this.model.getAttached().get(0);
		if(!this.equals(principalControl)) {
			if(principalControl.getAffichageLignes().isSelected()) {
				this.df.setAfficherLignes(true);
				this.df.setColorLigne(principalControl.getDf().getColorLigne());
				this.canvas.getGraphicsContext2D().setStroke((principalControl.getLigne().getValue()));
			}else {
				this.df.setAfficherLignes(false);
			}
			if(principalControl.getAffichageFaces().isSelected()) {
				this.df.setAfficherFaces(true);
				this.df.setColorFace(principalControl.getDf().getColorFace());
				this.canvas.getGraphicsContext2D().setFill(principalControl.getFace().getValue());
			}else {
				this.df.setAfficherFaces(false);
			}
			if(principalControl.getAffichageEclairage().isSelected()) {
				this.df.setActiverEclairage(true);
			}else {
				this.df.setActiverEclairage(false);
			}
		}
	}


	/**
	 * Permet l'affichage du Modèle sur le Canvas.
	 * <p>Effectue les mouvements sur le modèle et appelle le Dessinateur pour dessiner ce dernier.</p>
	 */
	public void affiche() {
		this.df.clearCanvas();
		if (model.getListeSommets().get(0).getX() < 2.0 && model.getListeSommets().get(0).getY() < 2.0) {
			model.setMouvement(new Zoom(this.df, 1500));
			model.effectuerMouvement();
		} else if (model.getListeSommets().get(0).getX() < 5.0 && model.getListeSommets().get(0).getY() < 5.0) {
			model.setMouvement(new Zoom(this.df, 120));
			model.effectuerMouvement();
		}
		this.df.setActiverEclairage(false);
		this.df.dessinerModele(null);
	}

	/**
	 * Cette méthode est appellée lorsqu'un nouveau Fichier est selectionné dans la liste des Fichiers.
	 * <p>Va tout simplement remettre les paramètre du Modèle et de la Vue par défaut et ouvrir le nouveau Fichier.</p>
	 */
	@FXML
	public void openModel() {
		model.getListeSommets().clear();
		model.getListeFaces().clear();
		model.getListeVectNorm().clear();
		this.df.resetCentrage();
		model.setFile(listView.getSelectionModel().getSelectedItem());
		try {
			fr = new FileRead(model);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		fr.setFile();
		this.df.centrageEtendu();
		nameFile.setText(listView.getSelectionModel().getSelectedItem().substring(0,listView.getSelectionModel().getSelectedItem().length() - 4));
		dateFile.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date(model.getFile().lastModified())));
		fxml_nbFaces.setText("" + fr.getNbFaces());
		fxml_nbSommets.setText("" + fr.getNbSommets());
		model.initNorm ();
		ligne.setValue(Color.BLACK);
		this.df.setColorLigne(ligne.getValue());
		face.setValue(Color.WHITE);
		this.df.setColorFace(face.getValue());
		slidx.setValue(slidx.getMin());
		slidy.setValue(slidy.getMin());
		slidz.setValue(slidz.getMin());
		affichageEclairage.setSelected(false);
		affichageFaces.setSelected(true);
		this.df.setAfficherFaces(true);
		this.df.setAfficherLignes(true);
		affichageLignes.setSelected(true);
		affiche();
		this.model.updateForNoMovment();
	}
	
	/**
	 * Instancie une nouvelle Vue.
	 */
	@FXML
	public void newVue() {
		if(this.df.getColorFace() != null) {
			new Vue("Vue2.fxml");
		}
	}

	/**
	 * Créé une nouvelle instance de Rotation X qu'il applique au Modèle.
	 */
	@FXML
	public void rotateModelX() {
		model.setMouvement(new Rotation(this.df, 'x'));
		model.effectuerMouvement();
	}
	/**
	 * Créé une nouvelle instance de Rotation Y qu'il applique au Modèle.
	 */
	@FXML
	public void rotateModelY() {
		model.setMouvement(new Rotation(this.df, 'y'));
		model.effectuerMouvement();
	}
	/**
	 * Créé une nouvelle instance de Rotation Z qu'il applique au Modèle.
	 */
	@FXML
	public void rotateModelZ() {
		model.setMouvement(new Rotation(this.df, 'z'));
		model.effectuerMouvement();
	}

	/**
	 * Créé une nouvelle instance de Translation vers la Droite qu'il applique au Modèle.
	 */
	@FXML
	public void translateDroite() {
		model.setMouvement(new Translation(this.df, 'd'));
		model.effectuerMouvement();
	}
	/**
	 * Créé une nouvelle instance de Translation vers la Gauche qu'il applique au Modèle.
	 */
	@FXML
	public void translateGauche() {
		model.setMouvement( new Translation(this.df, 'g'));
		model.effectuerMouvement();
	}
	/**
	 * Créé une nouvelle instance de Translation vers le Haut qu'il applique au Modèle.
	 */
	@FXML
	public void translateHaut() {
		model.setMouvement(new Translation(this.df, 'h'));
		model.effectuerMouvement();
	}
	/**
	 * Créé une nouvelle instance de Translation vers le Bas qu'il applique au Modèle.
	 */
	@FXML
	public void translateBas() {
		model.setMouvement( new Translation(this.df, 'b'));
		model.effectuerMouvement();
	}

	/**
	 * Va creer une nouvelle instance de Zoom qu'il applique au Modèle.
	 * <ul><li>Si la molette est scrollée vers le haut alors on utilise le facteur de Zoom.</li>
	 * <li>Si la molette est scrollée vers le bas alors on utilise le facteur de DeZoom.</li></ul>
	 */
	@FXML
	public void zoomMolette() {
		canvas.setOnScroll(e -> {
			e.consume();
			if (e.getDeltaY() == 0) {
				return;
			}
			if (e.getDeltaY() > 0) {
				this.model.setMouvement(new Zoom(this.df, Zoom.FACTEUR_ZOOM));
			} else {
				this.model.setMouvement(new Zoom(this.df, Zoom.FACTEUR_DEZOOM));
			}
			model.effectuerMouvement();
		});
	}

	/**
	 * Créé une nouvelle instance de Zoom qu'il applique au Modèle.
	 * <p> En utilisant le facteur de Zoom.</p>
	 * @throws IOException
	 * 				Peut renvoyer mais ne traite pas les possibles IOException.
	 */
	@FXML
	public void zoomButton() throws IOException {
		model.setMouvement(new Zoom(this.df, Zoom.FACTEUR_ZOOM));
		model.effectuerMouvement();
	}
	
	/**
	 * Créé une nouvelle instance de Zoom qu'il applique au Modèle.
	 * <p> En utilisant le facteur de DeZoom.</p>
	 * @throws IOException
	 * 				Peut renvoyer mais ne traite pas les possibles IOException.
	 */
	@FXML
	public void deZoomButton() throws IOException {
		model.setMouvement(new Zoom(this.df, Zoom.FACTEUR_DEZOOM));
		model.effectuerMouvement();
	}

	/**
	 * Met à jour la Couleur des lignes en récupérant la valeur de la Palette de Couleur des Lignes.
	 */
	@FXML public void getColorLigne () {
		this.df.setColorLigne(ligne.getValue());
		this.model.updateForNoMovment();
	}
	/**
	 * Met à jour la Couleur des lignes en récupérant la valeur de la Palette de Couleur des Faces.
	 */
	@FXML public void getColorFace() {
		this.df.setColorFace(face.getValue());
		this.model.updateForNoMovment();
	}

	/**
	 * Change la valeur de l'activation de l'eclairage en fonction de la valeur de la CheckBox correspondante.
	 */
	@FXML public void activerEclairage() {
		this.df.setActiverEclairage(this.affichageEclairage.isSelected());
		this.model.updateForNoMovment();
	}

	/**
	 * Change la valeur de l'activation des Lignes en fonction de la valeur de la CheckBox correspondante.
	 */
	@FXML public void activerLignes() {
		this.df.setAfficherLignes(this.affichageLignes.isSelected());
		this.model.updateForNoMovment();
	}

	/**
	 * Change la valeur de l'activation des Faces en fonction de la valeur de la CheckBox correspondante.
	 */
	@FXML public void activerFaces() {
		this.df.setAfficherFaces(this.affichageFaces.isSelected());
		this.model.updateForNoMovment();
	}
	
	/**
	 * Met à jour le Modèle.
	 */
	public void update() {
		Translation temp = null;
		if((model.getMouvement() instanceof Translation)) {
			temp = (Translation) this.model.getMouvement();
		}
		changeLineAndFacesColor();
		this.df.dessinerModele(temp);
	}
	
	/**
	 * Retourne le Dessinateur.
	 * @return Le Dessinateur, sous forme d'un DessinFace.
	 */
	public DessinFace getDf() {
		return df;
	}
	/**
	 * Retourne le Nom du Fichier.
	 * @return Le Nom du fichier, sous forme d'un Label.
	 */
	public Label getNameFile() {
		return nameFile;
	}
	/**
	 * Retourne la Palette de Couleurs des Lignes.
	 * @return La Palette de Couleurs des Lignes, sous forme d'un ColorPicker.
	 */
	public ColorPicker getLigne() {
		return ligne;
	}
	/**
	 * Retourne la Palette de Couleurs des Faces.
	 * @return La Palette de Couleurs des Faces, sous forme d'un ColorPicker.
	 */
	public ColorPicker getFace() {
		return face;
	}
	/**
	 * Retourne la CheckBox de l'affichage de l'Eclairage.
	 * @return La CheckBox de l'affichage de l'Eclairage, sous forme d'une CheckBox.
	 */
	public CheckBox getAffichageEclairage() {
		return affichageEclairage;
	}
	/**
	 * Retourne la CheckBox de l'affichage des Lignes.
	 * @return La CheckBox de l'affichage des Lignes, sous forme d'une CheckBox.
	 */
	public CheckBox getAffichageLignes() {
		return affichageLignes;
	}
	/**
	 * Retourne la CheckBox de l'affichage des Faces.
	 * @return La CheckBox de l'affichage des Faces, sous forme d'une CheckBox.
	 */
	public CheckBox getAffichageFaces() {
		return affichageFaces;
	}
	
}