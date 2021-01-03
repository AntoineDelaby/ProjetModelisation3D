package controller;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import dessin.DessinFace;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import model.FileRead;
import model.Model;
import mouvement.Rotation;
import mouvement.Translation;
import mouvement.Zoom;

public class Controller implements Initializable {

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
	private GraphicsContext gc;
	private FileRead fr;
	private Model model;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.model = new Model();
		model.setDf(new DessinFace(canvas, this.model,face.getValue()));

		listView.getItems().addAll(model.filterList());
		listView.getSelectionModel().getSelectedItems().addListener(new openModel());
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}

	class openModel implements ListChangeListener<String> {
		public void onChanged(ListChangeListener.Change<? extends String> c) {
			model.getListeSommets().clear();
			model.getListeFaces().clear();
			model.getListeVectNorm().clear();
			gc = canvas.getGraphicsContext2D();

			model.setFile(listView.getSelectionModel().getSelectedItem());
			try {
				fr = new FileRead(model);
			} catch (IOException e) {
				e.printStackTrace();
			}
			fr.setFile();
			nameFile.setText(listView.getSelectionModel().getSelectedItem().substring(0,listView.getSelectionModel().getSelectedItem().length() - 4));
			dateFile.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date(model.getFile().lastModified())));
			fxml_nbFaces.setText("" + fr.getNbFaces());
			fxml_nbSommets.setText("" + fr.getNbSommets());
			model.initNorm ();
			ligne.setValue(Color.BLACK);
			face.setValue(Color.WHITE);
			slidx.setValue(slidx.getMin());
			slidy.setValue(slidy.getMin());
			slidz.setValue(slidz.getMin());
			model.affiche();
			affichageEclairage.setSelected(false);
		}
	}

	//Full method Movement
	@FXML
	public void rotateModelX() {
		model.setMouvement(new Rotation(model.getDf(), 'x', slidx.getValue()));
		model.effectuerMouvement();
	}

	@FXML
	public void rotateModelY() {
		model.setMouvement(new Rotation(model.getDf(), 'y', slidy.getValue()));
		model.effectuerMouvement();
	}

	@FXML
	public void rotateModelZ() {
		model.setMouvement(new Rotation(model.getDf(), 'z', slidz.getValue()));
		model.effectuerMouvement();
	}


	@FXML
	public void translateDroite() {
		model.setMouvement(new Translation(model.getDf(), 'd'));
		model.effectuerMouvement();
	}

	@FXML
	public void translateGauche() {
		model.setMouvement( new Translation(model.getDf(), 'g'));
		model.effectuerMouvement();
	}

	@FXML
	public void translateHaut() {
		model.setMouvement(new Translation(model.getDf(), 'h'));
		model.effectuerMouvement();
	}

	@FXML
	public void translateBas() {
		model.setMouvement( new Translation(model.getDf(), 'b'));
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
				this.model.setMouvement(new Zoom(model.getDf(), Zoom.FACTEUR_ZOOM));
			} else {
				this.model.setMouvement(new Zoom(model.getDf(), Zoom.FACTEUR_DEZOOM));
			}
			model.effectuerMouvement();
		});
	}

	@FXML
	public void zoomOnModel() throws IOException {
		model.setMouvement(new Zoom(model.getDf(), Zoom.FACTEUR_ZOOM));
		model.effectuerMouvement();
	}

	@FXML
	public void zoomButton() throws IOException {
		model.setMouvement( new Zoom(model.getDf(), Zoom.FACTEUR_ZOOM));
		model.effectuerMouvement();
	}

	@FXML
	public void deZoomButton() throws IOException {
		model.setMouvement(new Zoom(model.getDf(), Zoom.FACTEUR_DEZOOM));
		model.effectuerMouvement();
	}

	@FXML public void getColorLigne () {
		gc.setStroke(ligne.getValue());
		model.getDf().dessinerModele(null);
	}

	@FXML public void getColorFace() {
		model.getDf().dessinerModele(null);
		model.getDf().setColor(face.getValue());
	}

	@FXML public void activerEclairage() {
		model.getDf().setActiverEclairage(this.affichageEclairage.isSelected());
		model.getDf().dessinerModele(null);
	}
}