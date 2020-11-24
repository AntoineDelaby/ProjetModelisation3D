package application;

public class Sommet {
	public float x;
	public float y;
	public float z;

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