package Data;

public class Collisions{
	
	private int X1, X2, Y1, Y2;		//define
	
	public Collisions(int X1, int X2, int Y1, int Y2){
		this.X1 = X1;			//left
		this.X2 = X2;			//right
		this.Y1 = Y1;			//top
		this.Y2 = Y2;			//bottom
	}
	
	public boolean isColliding(Collisions otherObj) {		//returns boolean if two objects collide
		return X1 > otherObj.X2 || X2 < otherObj.X1 || Y1 > otherObj.Y2 || Y2 < otherObj.Y1;
	}
	
	public boolean inSign(Collisions sign) {
		return X1 > sign.X2 || X2 < sign.X1 || Y1 > sign.Y2 || Y2 < sign.Y1;	//created for interact range with sign
	}
	
	public boolean inKnife(Collisions knife) {
		return X1 > knife.X2 || X2 < knife.X1 || Y1 > knife.Y2 || Y2 < knife.Y1;	//interact range for knives
	}
	
}