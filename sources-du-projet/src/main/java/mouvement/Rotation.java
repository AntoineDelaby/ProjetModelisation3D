package mouvement;

import dessin.DessinFace;

public class Rotation extends Mouvement{
	
	public final static int FACTEURROTATION = 8;

	@SuppressWarnings("unused")
	private double facteurRotation;
	private float[][]rotAxe;
	public final static double PI = Math.PI;
		
	public Rotation(DessinFace df,char rotAxe, double facteurRotation) {
		super(df);
		this.facteurRotation = facteurRotation;
		if(rotAxe == 'x') {
			this.rotAxe = new float[][]{{1,0,0,0}, {0,(float) Math.cos(PI/facteurRotation),(float) - Math.sin(PI/facteurRotation),0},{0,(float) Math.sin(PI/facteurRotation),(float) Math.cos(PI/facteurRotation),0},{0,0,0,1}};
		}else if(rotAxe == 'y') {
			this.rotAxe = new float[][]{{(float)Math.cos(PI/facteurRotation),0,(float)-Math.sin(PI/facteurRotation),0},{0,1,0,0},{(float)Math.sin(PI/facteurRotation),0,(float)Math.cos(PI/facteurRotation),0},{0,0,0,1}};
		}else if(rotAxe == 'z'){
			this.rotAxe = new float[][]{{(float)Math.cos(PI/facteurRotation),(float)-Math.sin(PI/facteurRotation),0,0},{(float)Math.sin(PI/facteurRotation),(float)Math.cos(PI/facteurRotation),0,0},{0,0,1,0},{0,0,0,1}};
		}
	}

	public void effectuerMouvement(float[][] model) {
		float[][]res = new float[model.length][model[0].length];
		for(int i = 0; i < model.length; i++) {
			for(int j = 0; j < model[0].length; j++) {
				res[i][j] = this.rotAxe[i][0] * model[0][j] + this.rotAxe[i][1] * model[1][j] + this.rotAxe[i][2] * model[2][j] + this.rotAxe[i][3] * model[3][j];
			}
		}
		for(int i = 0; i < model.length; i++) {
			for(int j = 0; j < model[0].length; j++) {
				model[i][j] = res[i][j];
			}
		}
	}
}