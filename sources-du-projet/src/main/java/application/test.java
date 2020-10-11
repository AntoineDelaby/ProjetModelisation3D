package application;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class test extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Group root3D = new Group();
			Pane pane = new Pane(root3D);
			
			Box cube = new Box(1,1,1);
			
		/*	//Create Material
			PhongMaterial blueMaterial = new PhongMaterial();
			blueMaterial.setDiffuseColor(Color.BLUE);
			blueMaterial.setSpecularColor(Color.BLUE);
			//Set it to the cube
			cube.setMaterial(blueMaterial);*/
			
			//Add the cube to this node
			root3D.getChildren().add(cube);
			
			//Add a camera group
			PerspectiveCamera camera = new PerspectiveCamera(true);
			Group cameraGroup = new Group(camera);
			root3D.getChildren().add(cameraGroup);
			
			//Rotate then move the camera
			Rotate ry = new Rotate();
			ry.setAxis(Rotate.Y_AXIS);
			ry.setAngle(-15);
			
			Translate tz = new Translate();
			tz.setZ(-10);
			tz.setY(-1);

			cameraGroup.getTransforms().addAll(ry,tz);
			
		/*	//Add point light
			PointLight light = new PointLight(Color.WHITE);
			light.setTranslateX(-20);
			light.setTranslateY(-20);
			light.setTranslateZ(-15);
			light.getScope().addAll(root3D);
			root3D.getChildren().add(light);*/
			
			Scene scene = new Scene(pane, 600, 600,true);
			scene.setCamera(camera);
			scene.setFill(Color.GREY);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
