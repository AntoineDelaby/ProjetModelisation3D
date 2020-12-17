package mouvement;

import dessin.DessinFace;

public class Translation extends Mouvement {

	private char direction;
	
	public Translation(DessinFace df, char direction) {
		super(df);
		this.direction = direction;
	}
	
	public void effectuerMouvement(float[][] model) {
		if(direction == 'g') {
			translationGauche(model);
		}else if(direction == 'd') {
			translationDroite(model);
		}else if(direction == 'h') {
			translationHaut(model);
		}else if(direction == 'b') {
			translationBas(model);
		}
	}
	
	private void translationGauche(float[][]model) {
		for(int i = 0; i < model[0].length; i++) {
			model[0][i] = model[0][i] - 50;
		}
	}
	
	private void translationDroite(float[][]model) {
		for(int i = 0; i < model[0].length; i++) {
			model[0][i] = model[0][i] + 50;
		}
	}
	
	private void translationHaut(float[][]model) {
		for(int i = 0; i < model[0].length; i++) {
			model[1][i] = model[1][i] - 50;
		}
	}
	
	private void translationBas(float[][]model) {
		for(int i = 0; i < model[0].length; i++) {
			model[1][i] = model[1][i] + 50;
		}
	}
}
