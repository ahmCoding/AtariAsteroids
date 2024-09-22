package asteroidAtari;

import java.util.*;

import asteroidAtari.domain.SpaceObject;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import asteroidAtari.domain.Ship;
import asteroidAtari.domain.Asteroid;
import asteroidAtari.domain.Projectile;


public class GameGui extends Application {

	public static int WIDTH = 500;
	public static int HEIGTH = 500;

	@Override
	public void start(Stage stage) throws Exception {
		Pane pane = new Pane();
		pane.setPrefSize(WIDTH, HEIGTH);
		Ship ship = new Ship(WIDTH / 2, HEIGTH / 2);
		List<SpaceObject> spaceObjects = new ArrayList<>();
		List<Projectile> projectils = new ArrayList<>();
		int projectilsLimit = 2;

		Random rnd = new Random();
		for (int i = 0; i < rnd.nextInt(10)+10; i++) {
			spaceObjects.add(new Asteroid(rnd.nextInt(WIDTH / 3), rnd.nextInt(HEIGTH / 2)));
		}
		pane.getChildren().add(ship.getShape());
		spaceObjects.forEach(spaceObj -> pane.getChildren().add(spaceObj.getShape()));

		Scene view = new Scene(pane);
		stage.setScene(view);
		stage.show();
		Map<KeyCode, Boolean> keys = new HashMap<>();

		view.setOnKeyPressed((event) -> {
			// fire projectils
			if (event.getCode() == KeyCode.S) {
				if (projectils.size() <= projectilsLimit) {
					Projectile proj = new Projectile((int) ship.getShape().getTranslateX() + 25,
							(int) ship.getShape().getTranslateY() +10 );
					proj.getShape().setRotate(ship.getShape().getRotate());
					proj.accelerate();
					proj.setMovement(proj.getMovement().normalize().multiply(3));
					projectils.add(proj);
					pane.getChildren().add(proj.getShape());
				}
				return;
			}
			// if another key das S
			keys.put(event.getCode(), Boolean.TRUE);
		});

		view.setOnKeyReleased((event) -> {
			keys.put(event.getCode(), Boolean.FALSE);

		});

		new AnimationTimer() {
			@Override
			public void handle(long l) {
				if (keys.getOrDefault(KeyCode.LEFT, Boolean.FALSE)) {
					ship.turnLeft();
				}
				if (keys.getOrDefault(KeyCode.RIGHT, Boolean.FALSE)) {
					ship.turnRight();
				}
				if (keys.getOrDefault(KeyCode.UP, Boolean.FALSE)) {
					ship.accelerate();
				}
				if (keys.getOrDefault(KeyCode.DOWN, Boolean.FALSE)) {
					ship.accelerate();
				}

				ship.move();
				spaceObjects.forEach(asteroid -> asteroid.move());
				projectils.forEach(projectil -> projectil.move());

				// Collision
				// ship with spaceObjects
				spaceObjects.forEach(asteroid -> {
					if (ship.collide(asteroid)) {
						stop();
					}
				});
				// spaceObjects with projectils
				List<SpaceObject> collided = new ArrayList<>();
				List<Projectile> usedProjectils = new ArrayList<>();
				spaceObjects.forEach(spaceObj -> {
					projectils.stream().filter(projectil -> spaceObj.collide(projectil)).forEach(projectill -> {
						collided.add(spaceObj);
						usedProjectils.add(projectill);
					});
				});
				// projectils collided with ship
				Iterator<Projectile> iProjectills  = projectils.iterator();
				while(iProjectills.hasNext()){
					Projectile tempProj = iProjectills.next();
					if(ship.collide(tempProj)){
						collided.add(ship);
						usedProjectils.add(tempProj);
						break;
					}
				}

				// remove collided spaceObjects
				collided.forEach(destroyedSpaceObj -> {
					spaceObjects.remove(destroyedSpaceObj);
					pane.getChildren().remove(destroyedSpaceObj.getShape());
				});
				// remove used projectils
				usedProjectils.forEach(projectil -> {
					projectils.remove(projectil);
					pane.getChildren().remove(projectil.getShape());
				});
			}
		}.start();

	}

}
