package mouvement;

import dessin.DessinFace;
import dessin.Sommet;

public class Translation extends Mouvement {

	private char direction;
	private int decalage;
	private final static  int FACTEURDECALAGE = 50;

	public Translation(DessinFace df, char direction, int decalage) {
		super(df);
		this.direction = direction;
		this.decalage = decalage;
	}

	public Translation(DessinFace df, char direction) {
		this(df,direction,FACTEURDECALAGE);
	}

	public Translation(char direction) {
		this(null,direction,FACTEURDECALAGE);
	}

	public Translation() {
		this(null,' ',FACTEURDECALAGE);
	}

	public void effectuerMouvement(float[][] model) {
		if(model != null) {
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
	}

	public void modifCentre(Sommet sommet) {
		if(sommet != null) {
			if(direction == 'g') {
				sommet.setX(sommet.getX()-FACTEURDECALAGE);
			}else if(direction == 'd') {
				sommet.setX(sommet.getX()+FACTEURDECALAGE);
			}else if(direction == 'h') {
				sommet.setY(sommet.getY()-FACTEURDECALAGE);
			}else if(direction == 'b') {
				sommet.setY(sommet.getY()+FACTEURDECALAGE);
			}
		}
	}

	private void translationGauche(float[][]model) {
		for(int i = 0; i < model[0].length; i++) {
			model[0][i] = model[0][i] - decalage;
		}
	}

	private void translationDroite(float[][]model) {
		for(int i = 0; i < model[0].length; i++) {
			model[0][i] = model[0][i] + decalage;
		}
	}

	private void translationHaut(float[][]model) {
		for(int i = 0; i < model[0].length; i++) {
			model[1][i] = model[1][i] - decalage;
		}
	}

	private void translationBas(float[][]model) {
		for(int i = 0; i < model[0].length; i++) {
			model[1][i] = model[1][i] + decalage;
		}
	}

	public int getDecalage() {
		return decalage;
	}

	public char getDirection() {
		return direction;
	}

	public void setDirection(char direction) {
		this.direction = direction;
	}

	public void setDecalage(int decalage) {
		this.decalage = decalage;
	}

}
