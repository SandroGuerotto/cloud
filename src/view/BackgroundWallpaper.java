package view;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class BackgroundWallpaper {

    private Random r = new Random();
    private int low = 1;
    private int high = 17;
    private int result;

    private BackgroundSize size;
    private Image image;
    private Background background;

    public BackgroundWallpaper() {
        result = r.nextInt(high - low) + low;
        size = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true);
        image = new Image("images/" + result + ".jpg");
        background = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size));

    }

    public Background getBackground() {
        return background;
    }

    public void setResult(int newValue) {
        if (newValue == 17) {
            this.result = 1;
        } else {
            this.result = newValue;
        }
        Image image = new Image("images/" + result + ".jpg");
        background = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size));

    }

    public int getResult() {
        return this.result;
    }
}