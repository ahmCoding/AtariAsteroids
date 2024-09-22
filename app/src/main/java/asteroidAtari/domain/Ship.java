package asteroidAtari.domain;

import javafx.scene.shape.Polygon;

/**
*
* @author amarzani
*/
public class Ship extends SpaceObject {

   public Ship(int x, int y) {
       super(new Polygon(0, 0, 25, 10, 0, 15), x, y);
   }

}
