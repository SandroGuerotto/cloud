package controller;
/*
 * Name			: Starter.java
 * Author		: Sandro Guerotto
 * Describtion	: Starter class for Programm
 * Create on 	: 20.09.2016
 * Last modify  : dd.mm.yyyy name reason
 */

import handler.EventhandlerDataScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Starter extends Application {

	public void start(Stage stage) {
		// Init stage position and size
		final double ypos = Screen.getPrimary().getVisualBounds().getMinY();
		final double xpos = Screen.getPrimary().getVisualBounds().getMinX();
		final double width = Screen.getPrimary().getVisualBounds().getWidth();
		final double height = Screen.getPrimary().getVisualBounds().getHeight();
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/DataScreen.fxml"));
			Parent root = (Parent) loader.load();
			//setzen der Stage für die popup und mediachooser
			EventhandlerDataScreen eventhandlerDataScreen = (EventhandlerDataScreen) loader.getController();
			eventhandlerDataScreen.setStage(stage);

			Scene scene = new Scene(root);
			scene.getStylesheets().add(this.getClass().getResource("../view/application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} catch (Exception var5) {
			var5.printStackTrace();
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
