package asteroidAtari.domain;

import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

import asteroidAtari.GameGui;

/**
 *
 * @author amarzani
 */
public abstract class SpaceObject {

	private Polygon shape;
	private Point2D movement;

	public SpaceObject(Polygon shape, int x, int y) {
		this.shape = shape;
		this.shape.setTranslateX(x);
		this.shape.setTranslateY(y);
		this.movement = new Point2D(0, 0);
	}

	public Polygon getShape() {
		return shape;
	}

	public void turnLeft() {
		shape.setRotate(shape.getRotate() - 3);
	}

	public void turnRight() {
		shape.setRotate(shape.getRotate() + 3);
	}

	public void accelerate() {
		double slowingFactor = 0.03;
		// calc the direction of movement
		double rotation = shape.getRotate();
		// System.out.println("rotation: " + rotation);
		double degreeToRad = Math.PI * rotation / 180;
		// System.out.println("radiant: " + degreeToRad);
		double xToMove = Math.cos(degreeToRad) * slowingFactor;
		double yToMove = Math.sin(degreeToRad) * slowingFactor;
		// System.out.println("ToMove-> X: " + xToMove + " ,ToMove-> Y: " + yToMove);
		this.movement = movement.add(xToMove, yToMove);
	}

	public void move() {
		shape.setTranslateX(shape.getTranslateX() + movement.getX());
		shape.setTranslateY(shape.getTranslateY() + movement.getY());

		// keep items within game window
		if (shape.getTranslateX() < 0) {
			shape.setTranslateX(GameGui.WIDTH - shape.getBoundsInLocal().getWidth());
		}
		if (shape.getTranslateX() > GameGui.WIDTH) {
			shape.setTranslateX(shape.getBoundsInLocal().getWidth());
		}
		if (shape.getTranslateY() < 0) {
			shape.setTranslateY(GameGui.HEIGTH - shape.getBoundsInLocal().getHeight());
		}
		if (shape.getTranslateY() > GameGui.HEIGTH) {
			shape.setTranslateY(shape.getBoundsInLocal().getHeight());
		}

	}

	public boolean collide(SpaceObject other) {
		Shape collisionArea = Shape.intersect(this.shape, other.shape);
		return collisionArea.getBoundsInLocal().getWidth() != -1;
	}

	public Point2D getMovement() {
		return this.movement;
	}
	public void setMovement(Point2D newPos) {this.movement= newPos;}

}
