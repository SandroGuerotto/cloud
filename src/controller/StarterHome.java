package controller;

/*
 * Name			: StarterHome.java
 * Author		: Sandro Guerotto
 * Describtion	: Starter class für das Home GUI
 * Create on 	: 27.09.2016
 * Last modify  :
 */

import handler.EventhandlerHomeScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class StarterHome extends Application {

    public void start(Stage stage) {
        final double ypos = Screen.getPrimary().getVisualBounds().getMinY();
        final double xpos = Screen.getPrimary().getVisualBounds().getMinX();
        final double width = Screen.getPrimary().getVisualBounds().getWidth();
        final double height = Screen.getPrimary().getVisualBounds().getHeight();
        try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/HomeScreen.fxml"));
            Parent root = (Parent) loader.load();

            //setzen der Stage für die popup, mediachooser und fxml wechsel
			EventhandlerHomeScreen eventhandlerHomeScreen = (EventhandlerHomeScreen) loader.getController();
            eventhandlerHomeScreen.setStage(stage);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(this.getClass().getResource("../view/application.css").toExternalForm());

            //set font style
//			Font.loadFont(getClass().getResourceAsStream("../font/Dosis-Light.ttf"), 14);
            Font.loadFont(getClass().getResourceAsStream("../font/Dosis-Bold.ttf"), 14);
            Font.loadFont(getClass().getResourceAsStream("../font/Dosis-Medium.ttf"), 14);


            stage.getIcons().add(new Image("@/../icons/logo/logo.png"));
            stage.setTitle("Secure Cloud"); // Titel
            stage.setScene(scene);
            // Set position and size
            stage.setX(xpos);
            stage.setY(ypos);
            stage.setWidth(width);
            stage.setHeight(height);
            stage.setMaximized(true);
            ;
            stage.setResizable(true);
            stage.show();
        } catch (Exception var5) {
            var5.printStackTrace();
        }
    }
}