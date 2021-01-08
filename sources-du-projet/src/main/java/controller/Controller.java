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
import mouvement.Rotation;
import mouvement.Translation;
import mouvement.Zoom;
import vues.Vue;


public class Controller extends Stage  implements Initializable {

	@FXML private ColorPicker ligne ;
	@FXML private ColorPicker face ;
	@FXML private Label nameFile;
	@FXML private Label fxml_nbFaces;
	@FXML private Label fxml_nbSommets;
	@FXML private Label dateFile;
	@FXML private Label description;
	@FXML private ListView<String> listView;
	@FXML private Canvas canvas;
	@FXML private Slider slidx;
	@FXML private Slider slidy;
	@FXML private Slider slidz;
	@FXML private CheckBox affichageEclairage;
	@FXML private CheckBox affichageLignes;
	@FXML private CheckBox affichageFaces;
	private FileRead fr;
	protected Model model;
	private DessinFace df;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.model = Model.getInstance();
		this.model.getListControlleurs().add(this);
		this.df = new DessinFace(canvas);
		if(listView != null) {
			listView.getItems().addAll(model.filterList());
			listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		}
		Controller principalControl = this.model.getListControlleurs().get(0);
		if(!this.equals(principalControl) && principalControl.getNameFile() != null) {
			this.affiche();
			this.update();
		}
		if(this.listView == null && this.nameFile != null) {
			this.affiche();
		}
		for(Controller c : this.model.getListControlleurs()) {
			System.out.println(c + "|" + c.getDf() + "|" + c.equals(null));
		}
		System.out.println("-------------------------------------------------------------------");
	}
	
	public DessinFace getDf() {
		return df;
	}

	public Label getNameFile() {
		return nameFile;
	}

	public ColorPicker getLigne() {
		return ligne;
	}

	public ColorPicker getFace() {
		return face;
	}
	
	public CheckBox getAffichageEclairage() {
		return affichageEclairage;
	}

	public CheckBox getAffichageLignes() {
		return affichageLignes;
	}

	public CheckBox getAffichageFaces() {
		return affichageFaces;
	}

	public void changeLineAndFacesColor() {
		Controller principalControl = this.model.getListControlleurs().get(0);
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

	public void update() {
		Translation temp = null;
		if((model.getMouvement() instanceof Translation)) {
			temp = (Translation) this.model.getMouvement();
		}
		changeLineAndFacesColor();
		this.df.dessinerModele(temp);
	}

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

	@FXML
	public void newVue() {
		if(this.df.getColorFace() != null) {
			new Vue("Vue2.fxml");
		}
	}

	//Full method Movement
	@FXML
	public void rotateModelX() {
		model.setMouvement(new Rotation(this.df, 'x'));
		model.effectuerMouvement();
	}

	@FXML
	public void rotateModelY() {
		model.setMouvement(new Rotation(this.df, 'y'));
		model.effectuerMouvement();
	}

	@FXML
	public void rotateModelZ() {
		model.setMouvement(new Rotation(this.df, 'z'));
		model.effectuerMouvement();
	}


	@FXML
	public void translateDroite() {
		model.setMouvement(new Translation(this.df, 'd'));
		model.effectuerMouvement();
	}

	@FXML
	public void translateGauche() {
		model.setMouvement( new Translation(this.df, 'g'));
		model.effectuerMouvement();
	}

	@FXML
	public void translateHaut() {
		model.setMouvement(new Translation(this.df, 'h'));
		model.effectuerMouvement();
	}

	@FXML
	public void translateBas() {
		model.setMouvement( new Translation(this.df, 'b'));
		model.effectuerMouvement();
	}

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

	@FXML
	public void zoomOnModel() throws IOException {
		model.setMouvement(new Zoom(this.df, Zoom.FACTEUR_ZOOM));
		model.effectuerMouvement();
	}

	@FXML
	public void zoomButton() throws IOException {
		model.setMouvement( new Zoom(this.df, Zoom.FACTEUR_ZOOM));
		model.effectuerMouvement();
	}

	@FXML
	public void deZoomButton() throws IOException {
		model.setMouvement(new Zoom(this.df, Zoom.FACTEUR_DEZOOM));
		model.effectuerMouvement();
	}

	@FXML public void getColorLigne () {
		this.df.setColorLigne(ligne.getValue());
		this.model.updateForNoMovment();
	}

	@FXML public void getColorFace() {
		this.df.setColorFace(face.getValue());
		this.model.updateForNoMovment();
	}

	@FXML public void activerEclairage() {
		this.df.setActiverEclairage(this.affichageEclairage.isSelected());
		this.model.updateForNoMovment();
	}

	@FXML public void activerLignes() {
		this.df.setAfficherLignes(this.affichageLignes.isSelected());
		this.model.updateForNoMovment();
	}

	@FXML public void activerFaces() {
		this.df.setAfficherFaces(this.affichageFaces.isSelected());
		this.model.updateForNoMovment();
	}
}