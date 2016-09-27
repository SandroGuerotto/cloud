package controller;
/*
 * Name			: Starter.java
 * Author		: Sandro Guerotto
 * Describtion	: Starter class for Programm
 * Create on 	: 20.09.2016
 * Last modify  : 27.09.2016 Sandro Drag'n'drop
 */

import handler.EventhandlerLogin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Starter extends Application {

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

	public static void main(String[] args) {
		launch(args);
	}
}
