package mouvement;

import dessin.DessinFace;

public class Zoom extends Mouvement{

	public final static float FACTEUR_ZOOM = (float) 1.1;
	public final static float FACTEUR_DEZOOM = (float) 0.9;
	public final static float FACTEUR_NEUTRE = (float) 1;
	public final static float FACTEUR_ERREUR= (float) 0;
	
	private float facteur;

	public Zoom(DessinFace df, float facteur) {
		super(df);
		this.facteur = facteur;
	}

	public Zoom(float facteur) {
		this(null,facteur);
	}

	public Zoom() {
		this(null,FACTEUR_NEUTRE);
	}

	@Override
	public void effectuerMouvement(float[][] model) {
		zoom(model);
	}

	public void zoom(float[][]model) {
		if(model != null && facteur != FACTEUR_ERREUR) {
			for(int i = 0; i < model[0].length; i++) {
				model[0][i] = model[0][i] * this.facteur;
				model[1][i] = model[1][i] * this.facteur;
				model[2][i] = model[2][i] * this.facteur;
			}
		}
	}
}
