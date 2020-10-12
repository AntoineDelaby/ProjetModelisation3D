package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class Controller implements Initializable{
	private String pathRessources = "./ressources/";
	@FXML	private Label nameFile;
	@FXML	private Label NBfaces;
	@FXML	private Label NBsommets;
	@FXML	private Label dateFile;
	@FXML	private Label description;
	@FXML	private ListView<String> listView;
	@FXML	private Pane pane;
	
	private Group root3D;
	
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
		
		File f = new File ("./ledeatintinNB.ply");
		TriangleMesh test = new TriangleMesh();
		addPoints(f, test);
		addFaces(f, test);
		
		MeshView pyramid = new MeshView(test);
		pyramid.setDrawMode(DrawMode.FILL);
		pyramid.setTranslateX(200);
		pyramid.setTranslateY(100);
		pyramid.setTranslateZ(200);
		pane.getChildren().add(pyramid);
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					NBsommets.setText(""+getNBSommets(f));
				} catch (IOException e) {
					e.printStackTrace();  
				}
				
			}
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
		
		public void addPoints(File f, TriangleMesh tr)  {
			try {
			FileReader fr = new FileReader(pathRessources+f);
			BufferedReader br = new BufferedReader(fr);
			while(!br.readLine().equals("end_header"));
			for (int j=0;j<getNBSommets(f)-1;j++) {
				String ligne = br.readLine();
				String [] tab=ligne.split("   ");
				for(int x = 0; x < tab.length; x++) {
					tr.getPoints().addAll(Float.parseFloat(tab [x]));
				}
			}
			fr.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void addFaces(File f, TriangleMesh tr)  {
			try {
			FileReader fr = new FileReader(pathRessources+f);
			BufferedReader br = new BufferedReader(fr);
			while(!br.readLine().equals("end_header"));
			for(int i = 0; i < getNBSommets(f); i++) {
				br.readLine();
			}
			for (int j = 0; j < getNbFaces(f) - 1; j++) {
				String ligne = br.readLine();
				String [] tab = ligne.split(" ");
				for(int x = 0; x < tab.length; x++) {
					tr.getFaces().addAll(Integer.parseInt(tab [x]));
					System.out.print(tab[x]);
				}
				System.out.println();
			}
			fr.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public int getNbLineIntro(File f) throws IOException {
			int nbLines = 0;
			FileReader fr = new FileReader (pathRessources + f);
			BufferedReader br = new BufferedReader(fr);
			while(!br.readLine().equals("end_header")) {
				nbLines ++;
			}
			return nbLines;
		}
}