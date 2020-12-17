package mouvement;

import dessin.DessinFace;

public class Zoom extends Mouvement{

	public final static float FACTEUR_ZOOM = (float) 1.1;
	public final static float FACTEUR_DEZOOM = (float) 0.9;
	
	private float facteur;
	
	public Zoom(DessinFace df, float facteur) {
		super(df);
		this.facteur = facteur;
	}

	@Override
	public void effectuerMouvement(float[][] model) {
		zoom(model);
	}
	
	public void zoom(float[][]model) {
		for(int i = 0; i < model[0].length; i++) {
			model[0][i] = model[0][i] * this.facteur;
			model[1][i] = model[1][i] * this.facteur;
			model[2][i] = model[2][i] * this.facteur;
		}
	}
}
