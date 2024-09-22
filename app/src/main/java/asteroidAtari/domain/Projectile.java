package asteroidAtari.domain;

import javafx.scene.shape.Polygon;

public class Projectile extends SpaceObject{
	
	public Projectile(int x , int y) {
		super(new Polygon(0,0,6,0,6,3,0,3),x,y);
	}
	
}
