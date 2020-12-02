package mouvement;

import java.io.IOException;

import java.util.List;

import dessin.Face;
import dessin.Sommet;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Zoom extends Movement{
	private float factZoom;
	
	public Zoom(GraphicsContext gc ,Canvas c,List<Sommet> list,List<Face> list2 ) {
		super(gc,c, list, list2);
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
		factZoom = (float) 1.1;
		zoomOnModel();
	}

	public void deZoomButton() throws IOException {
		factZoom = (float) 0.9;
		zoomOnModel();
	}
	
	private void newCoordonZoom(float zoom) {
		for (Sommet s : listeSommets) {
			s.setX(s.getX() * zoom);
			s.setY(s.getY() * zoom);
			s.setZ(s.getZ() * zoom);
		}
	}

	
}
