package application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import dessin.Face;
import dessin.Sommet;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import mouvement.Rotation;
import mouvement.Translation;
import mouvement.Zoom;

public class Controller  implements Initializable {
	@FXML private ColorPicker ligne ;
	@FXML private ColorPicker face ;
	@FXML private Label nameFile;
	@FXML private Label fxml_nbFaces;
	@FXML private Label fxml_nbSommets;
	@FXML private Label dateFile;
	@FXML private Label description;
	@FXML private ListView<String> listView;
	@FXML private Canvas canvas;
	private int CANVAS_WIDTH;
	private int CANVAS_HEIGHT;
	private ArrayList<Sommet> listeSommets;
	private ArrayList<Face> listeFaces;
	private String pathRessources = "./ressources/";
	private GraphicsContext gc;
	private Zoom zoomMov; 
	private Translation translationMov;
	private Rotation rotateMov;
	private FileRead fr;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listeSommets = new ArrayList<Sommet>();
		listeFaces = new ArrayList<Face>();
		zoomMov = new Zoom(gc, canvas, listeSommets, listeFaces);
		translationMov = new Translation(gc, canvas, listeSommets, listeFaces);
		rotateMov = new Rotation(gc, canvas, listeSommets, listeFaces);
		CANVAS_WIDTH = (int) canvas.getWidth();
		CANVAS_HEIGHT = (int) canvas.getHeight();
		filterList();
	}

	private void filterList() {
		List<String> filteredFileList = new ArrayList<String>();
		String[] filelist = new File(pathRessources).list();
		if (filelist != null) {
			for (String fichier : filelist) 
				filteredFileList.add(fichier);
		}
		listView.getItems().addAll(filteredFileList);
		listView.getSelectionModel().getSelectedItems().addListener(new openModel());
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}

	class openModel implements ListChangeListener<String> {
		public void onChanged(ListChangeListener.Change<? extends String> c) {
			listeSommets.clear();
			listeFaces.clear();
			gc = canvas.getGraphicsContext2D();
			gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
			setFile();
		}
	}

	private void setFile() {
		File f = new File(pathRessources + listView.getSelectionModel().getSelectedItem());
		nameFile.setText(listView.getSelectionModel().getSelectedItem().substring(0,
				listView.getSelectionModel().getSelectedItem().length() - 4));
		dateFile.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date(f.lastModified())));
		try {
			fr = new FileRead(f, listeSommets, listeFaces);
			fxml_nbFaces.setText("" + fr.getNbFaces());
			fxml_nbSommets.setText("" + fr.getNbSommets());
			initSommets(f);
			initFaces(f);
			affiche();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void affiche() {
		if (listeSommets.get(0).getX() < 2.0 && listeSommets.get(0).getY() < 2.0) {
			newCoordonZoom(1500);
		} else if (listeSommets.get(0).getX() < 5.0 && listeSommets.get(0).getY() < 5.0) {
			newCoordonZoom(120);
		}
		for (int i = 0; i < fr.getNbFaces(); i++) {
			dessinFace(listeFaces.get(i));
		}
	}

	public void newCoordonZoom(double zoom) {
		for (Sommet s : listeSommets) {
			s.x *= zoom;
			s.y *= zoom;
			s.z *= zoom;
		}
	}



	public void initSommets(File f) throws IOException {
		float facteurDecalage = 0;
		int cptEspaces;
		int idx = 0;
		String temp = "";
		String[] coord = new String[3];
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		int lignesIntro = this.fr.getNbLineIntro();
		while (idx < lignesIntro) {
			br.readLine();
			idx++;
		}
		for (int x = idx; x < idx + this.fr.getNbSommets(); x++) {
			cptEspaces = 0;
			temp = br.readLine();
			for (int i = 0; i < temp.length(); i++) {
				if (temp.charAt(i) == ' ') {
					cptEspaces++;
					if (temp.charAt(i + 1) != ' ')
						break;
				}
			}
			if (cptEspaces == 3)
				coord = temp.split("   ");
			if (cptEspaces == 2)
				coord = temp.split("  ");
			if (cptEspaces == 1)
				coord = temp.split(" ");
			listeSommets.add(
					new Sommet(Float.parseFloat(coord[0]), Float.parseFloat(coord[1]), Float.parseFloat(coord[2])));
		}
		facteurDecalage = getMin(listeSommets);
		for (Sommet s : listeSommets) {
			s.x = (s.x - facteurDecalage);
			s.y = (s.y - facteurDecalage);
			s.z = (s.z - facteurDecalage);
		}

		br.close();
	}

	public float getMin(ArrayList<Sommet> liste) {
		float res = 0;
		for (Sommet s : liste) {
			if (s.x < res)
				res = s.x;
			if (s.y < res)
				res = s.y;
			if (s.z < res)
				res = s.z;
		}
		return res;
	}

	public void initFaces(File f) throws IOException {
		String[] listeSommets;
		int lignesAvantFaces = fr.getNbLineIntro() + fr.getNbSommets();
		BufferedReader br = new BufferedReader(new FileReader(f));

		for (int i = 0; i < lignesAvantFaces; i++) {
			br.readLine();
		}
		for (int i = 0; i < fr.getNbFaces(); i++) {
			Face face = new Face();
			listeSommets = br.readLine().split(" ");
			for (int j = 1; j < listeSommets.length; j++) {
				face.addSommet(Integer.parseInt(listeSommets[j]));
			}
			listeFaces.add(face);
		}
		br.close();
	}


	//Full method Movement

	@FXML
	public void rotateModelY() {
		rotateMov.rotateModelY();	
		color();
	}

	@FXML
	public void rotateModelZ() {
		rotateMov.rotateModelZ();		
		color();
	}

	@FXML
	public void rotateModelX() {
		rotateMov.rotateModelX();		
		color();
	}

	@FXML
	public void translateDroite() {
		translationMov.translateDroite();		
		color();
	}

	@FXML
	public void translateGauche() {
		translationMov.translateGauche();		
		color();
	}

	@FXML
	public void translateHaut() {
		translationMov.translateHaut();		
		color();
	}

	@FXML
	public void translateBas() {
		translationMov.translateBas();		
		color();
	}

	@FXML
	public void zoomMolette() {
		zoomMov.zoomMolette();		
		color();
	}

	@FXML
	public void zoomOnModel() throws IOException {
		zoomMov.zoomOnModel();		
		color();
	}

	@FXML
	public void zoomButton() throws IOException {
		zoomMov.zoomButton();		
		color();
	}

	@FXML
	public void deZoomButton() throws IOException {
		zoomMov.deZoomButton();
		color();
	}



	// A modifier avec la classe DessinFace

	@FXML public void getColorLigne () {
		gc.setStroke(ligne.getValue());
		for (Face f : listeFaces)
			dessinFace(f);
	}	

	public void color() {
		getColorFace();getColorLigne();
	}

	@FXML public void getColorFace () {
		gc.setFill(face.getValue());
		for (Face f : listeFaces)
			dessinFace(f);
	}

	public void dessinFace(Face f) {
		gc.beginPath();
		gc.moveTo(listeSommets.get(f.getSommets().get(0)).getX(), listeSommets.get(f.getSommets().get(0)).getY());
		gc.lineTo(listeSommets.get(f.getSommets().get(1)).getX(), listeSommets.get(f.getSommets().get(1)).getY());
		gc.lineTo(listeSommets.get(f.getSommets().get(2)).getX(), listeSommets.get(f.getSommets().get(2)).getY());
		gc.lineTo(listeSommets.get(f.getSommets().get(0)).getX(), listeSommets.get(f.getSommets().get(0)).getY());
		double [] x = new double [] {listeSommets.get(f.getSommets().get(0)).getX(),listeSommets.get(f.getSommets().get(1)).getX(),listeSommets.get(f.getSommets().get(2)).getX()};
		double [] y = new double [] {listeSommets.get(f.getSommets().get(0)).getY(),listeSommets.get(f.getSommets().get(1)).getY(),listeSommets.get(f.getSommets().get(2)).getY()};
		gc.fillPolygon(x, y, 3);
		gc.stroke();

	}

}
