package Controllers;
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

import Model.Model;
import application.FileRead;
import application.Vecteur;
import dessin.DessinFace;
import dessin.Face;
import dessin.Sommet;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mouvement.Matrice;
import mouvement.Mouvement;
import mouvement.Rotation;
import mouvement.Translation;
import mouvement.Zoom;

public class Controller extends Stage implements Initializable {
		
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
	private int CANVAS_WIDTH;
	private int CANVAS_HEIGHT;
	private List<Sommet> listeSommets;
	private List<Face> listeFaces;
	private List<Vecteur> listeVectNorm;
	private String pathRessources = "./ressources/";
	private GraphicsContext gc;
	private Mouvement mouvement;
	private Matrice matrice = new Matrice();
	private DessinFace df;
	private FileRead fr;
	private Model model;
	float norme = (float) Math.sqrt(1*1+(-1)*(-1)+1*1);
	Vecteur lumiere = new Vecteur(1/norme,(-1)/norme, 1/norme);
	
	public Controller(Model model) {
		this.model = model;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Vue.fxml"));
		
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			this.setScene(scene);
			this.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Controller() {
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listeSommets = new ArrayList<Sommet>();
		listeFaces = new ArrayList<Face>();
		listeVectNorm = new ArrayList<Vecteur>();
		this.df = new DessinFace(canvas, listeSommets, listeFaces);
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
			listeVectNorm.clear();
			gc = canvas.getGraphicsContext2D();
			gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
			setFile();
		}
	}

	private void setFile() {
		File f = new File(pathRessources + listView.getSelectionModel().getSelectedItem());
		nameFile.setText(listView.getSelectionModel().getSelectedItem().substring(0,listView.getSelectionModel().getSelectedItem().length() - 4));
		dateFile.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date(f.lastModified())));
		try {
			fr = new FileRead(f, listeSommets, listeFaces);
			fxml_nbFaces.setText("" + fr.getNbFaces());
			fxml_nbSommets.setText("" + fr.getNbSommets());
			initSommets(f);
			initFaces(f);
			initNorm();
			this.df = new DessinFace(canvas, listeSommets, listeFaces);
			affiche();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void affiche() {
		df.clearCanvas();
		if (listeSommets.get(0).getX() < 2.0 && listeSommets.get(0).getY() < 2.0) {
			this.mouvement = new Zoom(df, 1500);
			effectuerMouvement();
		} else if (listeSommets.get(0).getX() < 5.0 && listeSommets.get(0).getY() < 5.0) {
			this.mouvement = new Zoom(df, 120);
			effectuerMouvement();
		}
		df.dessinerModele();
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
			listeSommets.add(new Sommet(Float.parseFloat(coord[0]), Float.parseFloat(coord[1]), Float.parseFloat(coord[2])));
		}
		facteurDecalage = getMin(listeSommets);
		for (Sommet s : listeSommets) {
			s.setX(s.getX() - facteurDecalage);
			s.setY(s.getY() - facteurDecalage);
			s.setZ(s.getZ() - facteurDecalage);
		}

		br.close();
	}
	
	public void initNorm () {
		float tab1 [] = new float [3];
		float tab2 [] = new float [3];
		float tabF [] = new float [3];
		float norme ;
		for (int i = 0 ; i < listeFaces.size() ; i++) {		
			Sommet s1=listeSommets.get(listeFaces.get(i).getS1());
			Sommet s2=listeSommets.get(listeFaces.get(i).getS2());
			Sommet s3=listeSommets.get(listeFaces.get(i).getS3());
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
			listeVectNorm.add(new Vecteur(tabF[0]/norme, tabF[1]/norme, tabF[2]/norme));
			
		}
	}
	
	public float eclairage (int numFace) {				
		return (float) (listeVectNorm.get(numFace).getDirX()*lumiere.getDirX())+(listeVectNorm.get(numFace).getDirY()*lumiere.getDirY())+(listeVectNorm.get(numFace).getDirZ()*lumiere.getDirZ());
	}

	public float getMin(List<Sommet> liste) {
		float res = 0;
		for (Sommet s : liste) {
			if (s.getX() < res)
				res = s.getX();
			if (s.getY() < res)
				res = s.getY();
			if (s.getZ() < res)
				res = s.getZ();
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

	private void updateDessinFace() {
		this.df.setListeFaces(listeFaces);
		this.df.setListeSommets(listeSommets);
	}
	
	public void effectuerMouvement() {
		float[][]model = matrice.toMatrice(listeSommets);
		listeVectNorm.clear();		
		this.mouvement.mouvement(model);	
		this.listeSommets = matrice.toList(model);
		updateDessinFace();
		initNorm();
		this.df.dessinerModele();
		getColorFace();
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
		df.dessinerModele();
		
	}

	@FXML public void getColorFace () {
		df.clearCanvas();
		for (int i=0;i<this.listeFaces.size();i++) {		
			if (eclairage(i)<1 && eclairage(i)>0.8)
				gc.setFill(new Color(face.getValue().getRed(), face.getValue().getGreen(), face.getValue().getBlue(),1.0).darker().brighter());
			if (eclairage(i)<0.8 && eclairage(i)>0.6)
				gc.setFill(new Color(face.getValue().getRed(), face.getValue().getGreen(), face.getValue().getBlue(),1.0).darker().darker().brighter());
			if (eclairage(i)<0.6 && eclairage(i)>0.4)
				gc.setFill(new Color(face.getValue().getRed(), face.getValue().getGreen(), face.getValue().getBlue(),1.0).darker().darker().darker().brighter());
			if (eclairage(i)<0.4 && eclairage(i)>0.2)
				gc.setFill(new Color(face.getValue().getRed(), face.getValue().getGreen(), face.getValue().getBlue(),1.0).darker().darker().darker().darker().brighter());
			if (eclairage(i)<0.2 && eclairage(i)>0.0)
				gc.setFill(new Color(face.getValue().getRed(), face.getValue().getGreen(), face.getValue().getBlue(),1.0).darker().darker().darker().darker().darker().brighter());
			if (eclairage(i)<0.0)
				gc.setFill(new Color(face.getValue().getRed(), face.getValue().getGreen(), face.getValue().getBlue(),1.0).darker().darker().darker().darker().darker().darker().brighter());
				//gc.setFill(Color.DARKRED);
			df.dessinFace(listeFaces.get(i));
			System.out.println(eclairage(i) );
		
		}
		
	}
}
