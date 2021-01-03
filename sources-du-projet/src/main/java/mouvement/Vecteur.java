package mouvement;

public class Vecteur {
	private float dirX;
	private float dirY;
	private float dirZ;
	
	public Vecteur(float dirX, float dirY, float dirZ) {
		
		this.dirX = dirX;
		this.dirY = dirY;
		this.dirZ = dirZ;
	}

	public String toString() {
		return this.dirX+","+this.dirY+","+this.dirZ;
				}

	public float getDirX() {
		return dirX;
	}

	public void setDirX(float dirX) {
		this.dirX = dirX;
	}

	public float getDirY() {
		return dirY;
	}

	public void setDirY(float dirY) {
		this.dirY = dirY;
	}

	public float getDirZ() {
		return dirZ;
	}

	public void setDirZ(float dirZ) {
		this.dirZ = dirZ;
	}
	
}