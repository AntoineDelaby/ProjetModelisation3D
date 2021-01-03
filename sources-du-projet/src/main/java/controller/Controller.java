package controller;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import application.FileRead;
import application.Vecteur;
import dessin.DessinFace;
import dessin.Sommet;
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
import model.Model;
import mouvement.Matrice;
import mouvement.Mouvement;
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
	private Mouvement mouvement;
	private DessinFace df;
	private FileRead fr;
	private Model model;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.model = new Model();
		this.df = new DessinFace(canvas, this.model);
	
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
			initNorm ();
			affiche();
		}
	}

	public void affiche() {
		df.clearCanvas();
		if (model.getListeSommets().get(0).getX() < 2.0 && model.getListeSommets().get(0).getY() < 2.0) {
			this.mouvement = new Zoom(df, 1500);
			effectuerMouvement();
		} else if (model.getListeSommets().get(0).getX() < 5.0 && model.getListeSommets().get(0).getY() < 5.0) {
			this.mouvement = new Zoom(df, 120);
			effectuerMouvement();
		}
		this.affichageEclairage.setSelected(false);
		df.setActiverEclairage(false);
		df.dessinerModele(null, face.getValue());
	}


	
	public void initNorm () {
		float tab1 [] = new float [3];
		float tab2 [] = new float [3];
		float tabF [] = new float [3];
		float norme ;
		for (int i = 0 ; i < model.getListeFaces().size() ; i++) {		
			Sommet s1=model.getListeSommets().get(model.getListeFaces().get(i).getS1());
			Sommet s2=model.getListeSommets().get(model.getListeFaces().get(i).getS2());
			Sommet s3=model.getListeSommets().get(model.getListeFaces().get(i).getS3());
			tab1 [0] = s2.getX() - s1.getX(); 
			tab1 [1] = s2.getY() - s1.getY(); 
			tab1 [2] = s2.getZ() - s1.getZ();
			tab2 [0] = s3.getX() - s1.getX(); 
			tab2 [1] = s3.getY() - s1.getY(); 
			tab2 [2] = s3.getZ() - s1.getZ();
			tabF[0]= tab1[1]*tab2[2]-tab1[2]*tab2[1];
			tabF[1]= tab1[2]*tab2[0]-tab1[0]*tab2[2];
			tabF[2]= tab1[0]*tab2[1]-tab1[1]*tab2[0];
			norme = (float) Math.sqrt(tabF[0]*tabF[0]+tabF[1]*tabF[1]+tabF[2]*tabF[2]);
			model.getListeVectNorm().add(new Vecteur(tabF[0]/norme, tabF[1]/norme, tabF[2]/norme));
		}
	}
	
	public void effectuerMouvement() {
		float[][]model = Matrice.toMatrice(this.model.getListeSommets());
		this.model.getListeVectNorm().clear();		
		this.mouvement.mouvement(model);	
		this.model.setListeSommets(Matrice.toList(model));
		initNorm();
		if(!(mouvement instanceof Translation))	this.df.dessinerModele(null, face.getValue());
		else this.df.dessinerModele((Translation) mouvement, face.getValue());
	}

	//Full method Movement
	@FXML
	public void rotateModelX() {
		this.mouvement = new Rotation(df, 'x', slidx.getValue());
		effectuerMouvement();
		
	}
	
	@FXML
	public void rotateModelY() {
		this.mouvement = new Rotation(df, 'y', slidy.getValue());
		effectuerMouvement();
		
	}

	@FXML
	public void rotateModelZ() {
		this.mouvement = new Rotation(df, 'z', slidz.getValue());
		effectuerMouvement();
		
	}
	

	@FXML
	public void translateDroite() {
		this.mouvement = new Translation(df, 'd');
		effectuerMouvement();
		
	}

	@FXML
	public void translateGauche() {
		this.mouvement = new Translation(df, 'g');
		effectuerMouvement();	
	}

	@FXML
	public void translateHaut() {
		this.mouvement = new Translation(df, 'h');
		effectuerMouvement();
		
	}

	@FXML
	public void translateBas() {
		this.mouvement = new Translation(df, 'b');
		effectuerMouvement();
		
	}

	@FXML
	public void zoomMolette() {
		this.canvas.setOnScroll(e -> {
			e.consume();
			if (e.getDeltaY() == 0) {
				return;
			}
			if (e.getDeltaY() > 0) {
				this.mouvement = new Zoom(df, Zoom.FACTEUR_ZOOM);
			} else {
				this.mouvement = new Zoom(df, Zoom.FACTEUR_DEZOOM);
			}
			effectuerMouvement();
			
		});
	}

	@FXML
	public void zoomOnModel() throws IOException {
		this.mouvement = new Zoom(df, Zoom.FACTEUR_ZOOM);
		effectuerMouvement();
		
	}

	@FXML
	public void zoomButton() throws IOException {
		this.mouvement = new Zoom(df, Zoom.FACTEUR_ZOOM);
		effectuerMouvement();
		
	}

	@FXML
	public void deZoomButton() throws IOException {
		this.mouvement = new Zoom(df, Zoom.FACTEUR_DEZOOM);
		effectuerMouvement();
		
	}
	
	@FXML public void getColorLigne () {
		gc.setStroke(ligne.getValue());
		df.dessinerModele(null, face.getValue());
		
	}
	
	@FXML public void getColorFace() {
		df.dessinerModele(null, face.getValue());
	}
	
	@FXML public void activerEclairage() {
		this.df.setActiverEclairage(this.affichageEclairage.isSelected());
		this.df.dessinerModele(null, face.getValue());
	}
}
