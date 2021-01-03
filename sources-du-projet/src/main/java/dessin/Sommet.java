package dessin;

public class Sommet {
	private float x;
	private float y;
	private float z;

	public Sommet (float x , float y , float z)	{
		this.x=x;
		this.y=y;
		this.z=z;
	}
	
	public Sommet() {
		this(1,1,0);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z; 
	}


	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setZ(float z) {
		this.z = z;
	}

	@Override
	public String toString() {
		return "Sommet [x=" + x + ", y=" + y + ", z=" + z + "]";
	}


}