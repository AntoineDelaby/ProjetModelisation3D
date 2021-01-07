package mouvement;

import dessin.DessinFace;

/**
 * Rotation est la classe de gestion des Mouvements de Rotation sur le modèle.
 * <p> Une Rotation est caractérisée par les informations suivantes :
 * <ul><li>Une direction, suceptible d'être changé.</li>
 * <li>Un décalage, suceptible d'être changé. Dépend de {@linkplain Translation#FACTEURDECALAGE}.</li>
 * <li>Un Facteur de décalage unique attribué définitivement à {@value Translation#FACTEURDECALAGE}.</li></ul>
 * De plus, Translation hérite de la classe Mouvement.</p>
 * @see Mouvement
 * @author Antoine Delaby, Yanis Vroland, Quentin Gillot, Mathéo Gallego
 */
public class Rotation extends Mouvement{

	public final static int FACTEURROTATION = 8;
	private float[][] rotAxe;
	public final static double PI = Math.PI;

	public Rotation(DessinFace df,char axeDeRotation, double facteurRotation) {
		super(df);
		if(facteurRotation > 0) {
			if(axeDeRotation == 'x') {
				rotAxe = new float[][]{{1,0,0,0}, {0,(float) Math.cos(PI/facteurRotation),(float) - Math.sin(PI/facteurRotation),0},{0,(float) Math.sin(PI/facteurRotation),(float) Math.cos(PI/facteurRotation),0},{0,0,0,1}};
			}else if(axeDeRotation == 'y') {
				rotAxe = new float[][]{{(float)Math.cos(PI/facteurRotation),0,(float)-Math.sin(PI/facteurRotation),0},{0,1,0,0},{(float)Math.sin(PI/facteurRotation),0,(float)Math.cos(PI/facteurRotation),0},{0,0,0,1}};
			}else if(axeDeRotation == 'z'){
				rotAxe = new float[][]{{(float)Math.cos(PI/facteurRotation),(float)-Math.sin(PI/facteurRotation),0,0},{(float)Math.sin(PI/facteurRotation),(float)Math.cos(PI/facteurRotation),0,0},{0,0,1,0},{0,0,0,1}};
			}
		}
	}
	
	public Rotation(DessinFace df, char axeDeRotation) {
		this(df, axeDeRotation, FACTEURROTATION);
	}
	

	public void effectuerMouvement(float[][] model) {
		float[][] res = new float[model.length][model[0].length];
		for(int i = 0; i < model.length; i++) {
			for(int j = 0; j < model[0].length; j++) {
				res[i][j] = rotAxe[i][0] * model[0][j] + rotAxe[i][1] * model[1][j] + rotAxe[i][2] * model[2][j] + rotAxe[i][3] * model[3][j];
			}
		}
		for(int i = 0; i < model.length; i++) {
			for(int j = 0; j < model[0].length; j++) {
				model[i][j] = res[i][j];
			}
		}
	}
}