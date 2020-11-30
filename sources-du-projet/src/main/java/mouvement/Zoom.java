package mouvement;

import java.io.IOException;
import java.util.ArrayList;

import dessin.Face;
import dessin.Sommet;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Zoom extends Movement{
	private double factZoom;
	
	public Zoom(GraphicsContext gc ,Canvas c,ArrayList<Sommet> listeSommets,ArrayList<Face> listeFaces ) {
		super(gc,c, listeSommets, listeFaces);
		factZoom = 1;
	}
	
	public void zoomMolette() {
		canvas.setOnScroll(e -> {
			e.consume();
			if (e.getDeltaY() == 0) {
				return;
			}
			if (e.getDeltaY() > 0)
				try {
					zoomButton();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			else
				try {
					deZoomButton();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		});
	}
	
	public void zoomOnModel() throws IOException {
		gc.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		newCoordonZoom(factZoom);
		for (int i = 0; i < listeFaces.size(); i++) {
			dessinFace(listeFaces.get(i));
		}
		
	}
	
	public void zoomButton() throws IOException {
		factZoom = 1.1;
		zoomOnModel();
	}

	public void deZoomButton() throws IOException {
		factZoom = 0.9;
		zoomOnModel();
	}
	
	private void newCoordonZoom(double zoom) {
		for (Sommet s : listeSommets) {
			s.x *= zoom;
			s.y *= zoom;
			s.z *= zoom;
		}
	}

	
}
