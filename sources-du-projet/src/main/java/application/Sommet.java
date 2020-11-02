package application;

public class Sommet {
	private float x;
	private float y;
	private float z;
	
	public Sommet (float x , float y , float z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
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

	@Override
	public String toString() {
		return "Sommet [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	

}