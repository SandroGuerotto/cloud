package controller;
/**
 * @author          :   Sandro Guerotto
 * Created          :   04.10.2016
 * Project          :   cloud
 * Package          :   controller
 * @version         :   2.0
 * LastUpdated      :	02.11.2016
 * Description      :   Starter class for login gui
 */

import handler.EventhandlerLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StarterLogin {
	

	public void start(Stage stage, Controller controller) {
		try {

			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Login.fxml"));
			Parent root = loader.load();
			//setzen der Stage für die popup und mediachooser

			EventhandlerLogin eventhandlerLoginScreen = loader.getController();
			eventhandlerLoginScreen.setStage(stage);
			eventhandlerLoginScreen.setController(controller);

			Scene scene = new Scene(root);
			scene.getStylesheets().add(this.getClass().getResource("../view/application.css").toExternalForm());

			//set font style
			Font.loadFont(getClass().getResourceAsStream("../font/Dosis-Bold.ttf"), 14);
			Font.loadFont(getClass().getResourceAsStream("../font/Dosis-Medium.ttf"), 14);
			
			
			stage.setOnCloseRequest(t -> controller.kill());
			
			stage.getIcons().add(new Image("@/../icons/logo/logo.png"));
			stage.setTitle("Secure Cloud"); // Titel
			stage.setScene(scene);

			stage.setResizable(false);
			stage.show();
		} catch (Exception var5) {
			var5.printStackTrace();
		}

	}

}
