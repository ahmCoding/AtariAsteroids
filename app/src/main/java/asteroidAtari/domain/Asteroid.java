package asteroidAtari.domain;

import java.util.Random;

public class Asteroid extends SpaceObject{
	public Asteroid(int x, int y) {
        super(new PentagonFactory().createPentagon(),x,y);
        Random rnd = new Random();
        super.getShape().setRotate(rnd.nextInt(360));
        
        for (int i = 0; i < 1 + rnd.nextInt(15); i++) {
            
            this.accelerate();
        }
    }

    @Override
    public void move() {
        super.move();
        //Random rnd = new Random();
        //double newDirection =  rnd.nextGaussian();
        //super.getShape().setRotate(super.getShape().getRotate()+newDirection );
    }
    

}
