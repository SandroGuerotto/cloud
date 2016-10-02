package controller;

/*
 * Name			: StarterHome.java
 * Author		: Sandro Guerotto
 * Describtion	: Starter class für das Data GUI
 * Create on 	: 27.09.2016
 * Last modify  :
 */

import handler.EventhandlerDataScreen;
import handler.EventhandlerHomeScreen;
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

public class StarterData extends Application {

    public void start(Stage stage) {
        final double ypos = Screen.getPrimary().getVisualBounds().getMinY();
        final double xpos = Screen.getPrimary().getVisualBounds().getMinX();
        final double width = Screen.getPrimary().getVisualBounds().getWidth();
        final double height = Screen.getPrimary().getVisualBounds().getHeight();
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/DataScreen.fxml"));  // Sandro
            Parent root = (Parent) loader.load();
            //setzen der Stage für die popup und mediachooser
            EventhandlerDataScreen eventhandlerDataScreen = (EventhandlerDataScreen) loader.getController(); //Sandro
            eventhandlerDataScreen.setStage(stage);// Sandro

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