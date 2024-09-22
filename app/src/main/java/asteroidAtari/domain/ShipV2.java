package asteroidAtari.domain;

import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.List;

public class ShipV2 extends SpaceObject{
    private List<Projectile> kugeln;
    private int kugelnLimit;
    public ShipV2(int x, int y ) {
        super(new Polygon(0, 0, 25, 10, 0, 15), x, y);
        kugeln = new ArrayList<>();
        kugelnLimit = 3;
    }


    /**
     * fires a new projectils
     * @return null if no projectile is fired
     */
    public Projectile fire(){
        Projectile fired=null;
        if(kugeln.size() <= kugelnLimit){
            Projectile proj = new Projectile((int) this.getShape().getTranslateX() + 25,
                    (int) this.getShape().getTranslateY() +10 );
            proj.getShape().setRotate(this.getShape().getRotate());
            proj.accelerate();
            proj.setMovement(proj.getMovement().normalize().multiply(3));
            kugeln.add(proj);
            fired = proj;
        }
        return fired;
    }

    public List<Projectile> getKugeln(){
        return kugeln;
    }






}
