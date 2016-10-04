package controller;
/**
 * @author          :   Sandro Guerotto
 * Created          :   04.10.2016
 * Project          :   cloud
 * Package          :   controller
 * @version         :   1.0
 * LastUpdated      :
 * Description      :   Starter class für das Login GUI
 */

import handler.EventhandlerLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StarterLogin extends Application {

	public void start(Stage stage) {
		try {

			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Login.fxml"));
			Parent root = (Parent) loader.load();
			//setzen der Stage für die popup und mediachooser

			EventhandlerLogin eventhandlerLoginScreen = (EventhandlerLogin) loader.getController();
			eventhandlerLoginScreen.setStage(stage);

			Scene scene = new Scene(root);
			scene.getStylesheets().add(this.getClass().getResource("../view/application.css").toExternalForm());

			//set font style
//			Font.loadFont(getClass().getResourceAsStream("../font/Dosis-Light.ttf"), 14);
			Font.loadFont(getClass().getResourceAsStream("../font/Dosis-Bold.ttf"), 14);
			Font.loadFont(getClass().getResourceAsStream("../font/Dosis-Medium.ttf"), 14);
			
			
			stage.getIcons().add(new Image("@/../icons/logo/logo.png"));
			stage.setTitle("Secure Cloud"); // Titel
			stage.setScene(scene);

			stage.setResizable(true);
			stage.show();
		} catch (Exception var5) {
			var5.printStackTrace();
		}

	}

	public void show() {
		launch();
	}
}
