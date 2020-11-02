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

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;

public class Controller implements Initializable{
	private String pathRessources = "./ressources/";
	@FXML	private Label nameFile;
	@FXML	private Label NBfaces;
	@FXML	private Label NBsommets;
	@FXML	private Label dateFile;
	@FXML	private Label description;
	@FXML	private ListView<String> listView;
	@FXML	private Pane pane;
	@FXML	private Button rotateNegative;
	@FXML	private Button rotatePositive;
	@FXML	private Slider zoom;
	@FXML	private Slider translation;
	@FXML	private Canvas canvas;
	private ArrayList <Sommet> listeSommets = new ArrayList<Sommet>();
	private ArrayList <Face> listeFaces = new ArrayList<Face>();
	private GraphicsContext gc;
	
	List<String> filteredFileList;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		File pathT = new File(pathRessources);
		String[] filelist = pathT.list();
		filteredFileList = new ArrayList<String>();
		if (filelist != null) {
			for (String fichier:filelist) {
				filteredFileList.add(fichier);
			}
		}		
		listView.getItems().addAll(filteredFileList);
		listView.getSelectionModel().getSelectedItems().addListener(new openModel());
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		File f = new File ("./apple.ply");
		try {
			initSommets(f);
			initFaces(f);
			
			gc = canvas.getGraphicsContext2D();
			for(int i = 0; i < listeFaces.size(); i++) {
				System.out.println(listeFaces.get(i).toString());
				dessinFace(listeFaces.get(i));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Event select task in listView
		class openModel implements ListChangeListener<String> {
			public void onChanged( ListChangeListener.Change<? extends String> c) {
				nameFile.setText(listView.getSelectionModel().getSelectedItem().substring(0, listView.getSelectionModel().getSelectedItem().length()-4));
				File f = new File (listView.getSelectionModel().getSelectedItem());
				File fbis= new File (pathRessources+f);
				Date date = new Date(fbis.lastModified());
				SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
				String date2 = sf.format(date);
				dateFile.setText(date2);
				try {
					NBfaces.setText(""+getNbFaces(f));
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					NBsommets.setText(""+getNBSommets(f));
				} catch (IOException e) {
					e.printStackTrace();  
				}
				
			}
		}
		
		public void initSommets(File f) throws IOException{
			int idx = 0;
			String temp = "";
			String[]coord;
			FileReader fr= new FileReader (pathRessources+f);
			BufferedReader br = new BufferedReader(fr);
			int lignesIntro = getNbLineIntro(f);
			while(idx < lignesIntro) {
				br.readLine();
				idx ++;
			}
			for(int x = idx; x < idx + getNBSommets(f); x ++) {
				temp = br.readLine();
				coord = temp.split("   ");
				listeSommets.add(new Sommet(Float.parseFloat(coord[0]), Float.parseFloat(coord[1]), Float.parseFloat(coord[2])));
			}
			br.close();
		}
		
		public void initFaces(File f) throws IOException{
			int idx = 0;
			String temp = "";
			String[]sommetsListe;
			FileReader fr= new FileReader (pathRessources+f);
			BufferedReader br = new BufferedReader(fr);
			int lignesAvantFaces = getNbLineIntro(f) + getNBSommets(f);
			while(idx < lignesAvantFaces) {
				br.readLine();
				idx ++;
			}
			for(int x = idx; x <= idx + getNbFaces(f); x ++) {
				Face face = new Face();
				temp = br.readLine();
				sommetsListe = temp.split(" ");
				for(int j = 1; j < sommetsListe.length; j++) {
					face.addSommet(Integer.parseInt(sommetsListe[j]));
				}
				listeFaces.add(face);
			}
			br.close();
		}

		public float getMinZ() {
			float min = listeSommets.get(0).getZ();
			for(int i = 1; i < listeSommets.size(); i++) {
				if(listeSommets.get(i).getZ() < min) {
					min = listeSommets.get(i).getZ();
				}
			}
			return min;
		}
		
		public void dessinFace(Face f) {
			gc.beginPath();
			gc.moveTo(listeSommets.get(f.getSommets().get(0)).getX(), listeSommets.get(f.getSommets().get(0)).getY());
			gc.lineTo(listeSommets.get(f.getSommets().get(1)).getX(), listeSommets.get(f.getSommets().get(1)).getY());
			gc.lineTo(listeSommets.get(f.getSommets().get(2)).getX(), listeSommets.get(f.getSommets().get(2)).getY());
			gc.lineTo(listeSommets.get(f.getSommets().get(0)).getX(), listeSommets.get(f.getSommets().get(0)).getY());
			gc.stroke();
		}

		public int getNbFaces (File f) throws IOException{
			int nbLines=-1;
			FileReader fr= new FileReader (pathRessources+f);
			BufferedReader br = new BufferedReader(fr);
			String temp = "";
			while((temp = br.readLine())!=null) {
				if(temp.substring(0,2).equals("3 ")) nbLines ++;
			}
			fr.close();
			return nbLines;
		}

		public int getNBSommets (File f) throws IOException{
			int nbLines=-1;
			FileReader fr = new FileReader (pathRessources+f);
			BufferedReader br = new BufferedReader (fr);
			while ((br.readLine())!=null) {
				nbLines++;
			}
			fr.close();
			return nbLines-getNbLineIntro(f)-getNbFaces(f);
		}
		
		public int getNbLineIntro(File f) throws IOException {
			int nbLines = 0;
			FileReader fr = new FileReader (pathRessources + f);
			BufferedReader br = new BufferedReader(fr);
			while(!br.readLine().equals("end_header")) {
				nbLines ++;
			}
			br.close();
			return ++nbLines;
		}
}