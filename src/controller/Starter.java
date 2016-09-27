package controller;
/*
 * Name			: Starter.java
 * Author		: Sandro Guerotto
 * Describtion	: Starter class for Programm
 * Create on 	: 20.09.2016
 * Last modify  : 27.09.2016 Sandro Drag'n'drop
 */

import handler.EventhandlerDataScreen;
import handler.EventhandlerHomeScreen;
import handler.EventhandlerLogin;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.File;

public class Starter extends Application {

	public void start(Stage stage) {
		// Init stage position and size
//		final double ypos = Screen.getPrimary().getVisualBounds().getMinY();
//		final double xpos = Screen.getPrimary().getVisualBounds().getMinX();
//		final double width = Screen.getPrimary().getVisualBounds().getWidth();
//		final double height = Screen.getPrimary().getVisualBounds().getHeight();
		try {
//			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/DataScreen.fxml"));  // Sandro
//			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/HomeScreen.fxml"));  //Toshiki
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/Login.fxml"));  //Login
			Parent root = (Parent) loader.load();
			//setzen der Stage für die popup und mediachooser
//			EventhandlerDataScreen eventhandlerDataScreen = (EventhandlerDataScreen) loader.getController(); //Sandro
//			EventhandlerHomeScreen eventhandlerHomeScreen = (EventhandlerHomeScreen) loader.getController(); //Toshiki
			EventhandlerLogin eventhandlerLoginScreen = (EventhandlerLogin) loader.getController(); //Login
			eventhandlerLoginScreen.setStage(stage);// Sandro

			Scene scene = new Scene(root);
			scene.getStylesheets().add(this.getClass().getResource("../view/application.css").toExternalForm());
			//drag'n'drop
			scene.setOnDragOver(new EventHandler<DragEvent>() {
				@Override
				public void handle(DragEvent event) {
					Dragboard db = event.getDragboard();
					if (db.hasFiles()) {
						event.acceptTransferModes(TransferMode.COPY);
					} else {
						event.consume();
					}
				}
			});


			scene.setOnDragDropped(new EventHandler<DragEvent>() {
				@Override
				public void handle(DragEvent event) {
					Dragboard db = event.getDragboard();
					boolean success = false;
					if (db.hasFiles()) {
						success = true;
						String filePath = null;
						for (File file:db.getFiles()) {
							filePath = file.getAbsolutePath();
							System.out.println(filePath);
						}
					}
					event.setDropCompleted(success);
					event.consume();
				}
			});



			//set font style
//			Font.loadFont(getClass().getResourceAsStream("../font/Dosis-Light.ttf"), 14);
			Font.loadFont(getClass().getResourceAsStream("../font/Dosis-Bold.ttf"), 14);
			Font.loadFont(getClass().getResourceAsStream("../font/Dosis-Medium.ttf"), 14);
			
			
			stage.getIcons().add(new Image("@/../icons/logo/logo.png"));
			stage.setTitle("Secure Cloud"); // Titel
			stage.setScene(scene);
			// Set position and size
//			stage.setX(xpos);
//			stage.setY(ypos);
//			stage.setWidth(width);
//			stage.setHeight(height);
//			stage.setMaximized(true);;
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
