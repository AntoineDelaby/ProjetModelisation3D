package mouvement;

import dessin.DessinFace;

public abstract class Mouvement {
	
	protected DessinFace df;
	
	public Mouvement(DessinFace df) {
		this.df = df;
	}
	
	public void mouvement(float[][]model) {
		effectuerMouvement(model);
		verifInCanvas();
	}
	
	public abstract void effectuerMouvement(float[][]model);
	
	public void verifInCanvas() {
		if (this.df.getMinX() < 0)
			this.df.decalagePoints(-(int) (this.df.getMinX() - 1), 0, 0);
		if (this.df.getMinY() < 0)
			this.df.decalagePoints(0, -(int) (this.df.getMinY() - 1), 0);
		if (this.df.getMaxX() > this.df.getGcWidth())
			this.df.decalagePoints(-(int) this.df.getMaxX() + this.df.getGcWidth(), 0, 0);
		if (this.df.getMaxY() > this.df.getGcHeigth())
			this.df.decalagePoints(0, -(int) this.df.getMaxY() + this.df.getGcHeigth(), 0);
	}
}
