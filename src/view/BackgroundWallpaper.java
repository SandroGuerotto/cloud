package view;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class BackgroundWallpaper {

	Random r = new Random();
	int low = 1;
	int high = 15;
	int result = r.nextInt(high-low) + low;
	BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true);
	Image image = new Image("images/" +result+".jpg");
	protected Background background = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size));

	public BackgroundWallpaper() {
		super();
	}

}