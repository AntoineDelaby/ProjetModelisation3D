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
		
		root3D = new Group();
		//pane = new Pane(root3D);
		TriangleMesh pyramidMesh = new TriangleMesh();
		pyramidMesh.getTexCoords().addAll(0,0);
		float h = 150;                    // Height
		float s = 300;                    // Side
		pyramidMesh.getPoints().addAll(
		        0,    0,    0,            // Point 0 - Top
		        0,    h,    -s/2,         // Point 1 - Front
		        -s/2, h,    0,            // Point 2 - Left
		        s/2,  h,    0,            // Point 3 - Back
		        0,    h,    s/2           // Point 4 - Right
		    );
		pyramidMesh.getFaces().addAll(
		        0,0,  2,0,  1,0,          // Front left face
		        0,0,  1,0,  3,0,          // Front right face
		        0,0,  3,0,  4,0,          // Back right face
		        0,0,  4,0,  2,0,          // Back left face
		        4,0,  1,0,  2,0,          // Bottom rear face
		        4,0,  3,0,  1,0           // Bottom front face
		    );
		MeshView pyramid = new MeshView(pyramidMesh);
		pyramid.setDrawMode(DrawMode.FILL);
		//pyramid.setMaterial(new PhongMaterial(Color.BLUE));
		pyramid.setTranslateX(200);
		pyramid.setTranslateY(100);
		pyramid.setTranslateZ(200);
		pane.getChildren().add(pyramid);
		Box cube = new Box(1,1,1);
		
		PhongMaterial blueMaterial = new PhongMaterial();
		blueMaterial.setDiffuseColor(Color.BLUE);
		blueMaterial.setSpecularColor(Color.BLUE);
		
	//	root3D.getChildren().add(cube);
		
		PerspectiveCamera camera = new PerspectiveCamera(true);
		Group cameraGroup = new Group(camera);
		pane.getChildren().add(cameraGroup);
		
		Rotate ry = new Rotate();
		ry.setAxis(Rotate.Y_AXIS);
		ry.setAngle(-15);
		
		Translate tz = new Translate();
		tz.setZ(-10);
		tz.setY(-1);

		cameraGroup.getTransforms().addAll(ry,tz);
		
		PointLight light = new PointLight(Color.WHITE);
		light.setTranslateX(-20);
		light.setTranslateY(-20);
		light.setTranslateZ(-15);
		light.getScope().addAll(pane);
		pane.getChildren().add(light);
		
		
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
			while (( br.readLine())!=null ) {
				
				if (br.readLine().substring(0,2).equals("3 "))
					nbLines=nbLines+2;
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
		return nbLines-10-getNbFaces(f);
		
		}
		
		
}