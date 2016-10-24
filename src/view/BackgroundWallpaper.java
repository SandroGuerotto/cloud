package view;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class BackgroundWallpaper{

	Random r = new Random();
	int low = 1;
	int high = 18;
	int result;

	private BackgroundSize size;
	private Image image;
	private Background background;

	public BackgroundWallpaper() {
		result = r.nextInt(high-low) + low;
//		result = 17;
		size = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true);
		image = new Image("images/" +result+".jpg");
		background = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size));

	}

	public Background getBackground(){
		return background;
	}

	public void setResult(int newValue){
		if (newValue == 18){
			this.result = 1;
		}else{
			this.result = newValue;
		}
		Image image = new Image("images/" +result+".jpg");
		background = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size));

	}

	public int getResult(){
		return this.result;
	}
}