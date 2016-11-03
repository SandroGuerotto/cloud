package controller;

/**
 * @author :   Sandro Guerotto
 * @Created :   27.09.2016
 * @Project :   cloud
 * @Package :   controller
 * @version :   1.0
 * @LastUpdated :
 * @Description :   Starter class für das Data GUI
 */

import handler.EventhandlerDataScreen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class StarterData {


    public void start(Stage stage, Controller controller) {
        final double ypos = Screen.getPrimary().getVisualBounds().getMinY();
        final double xpos = Screen.getPrimary().getVisualBounds().getMinX();
        final double width = Screen.getPrimary().getVisualBounds().getWidth();
        final double height = Screen.getPrimary().getVisualBounds().getHeight();
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../view/DataScreen.fxml"));
            Parent root = loader.load();
            //setzen der Stage für die popup und mediachooser
            EventhandlerDataScreen eventhandlerDataScreen = loader.getController();
            eventhandlerDataScreen.setStage(stage);
            eventhandlerDataScreen.setController(controller);


            Scene scene = new Scene(root);
            scene.getStylesheets().add(this.getClass().getResource("../view/application.css").toExternalForm());
            //drag'n'drop
            scene.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            });


            scene.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    controller.upload_data(db.getFiles(), eventhandlerDataScreen);
                }
                event.setDropCompleted(success);
                event.consume();
            });

            stage.setOnCloseRequest(t -> controller.kill());

            //set font style
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
            stage.setResizable(true);
            stage.show();
        } catch (Exception var5) {
            var5.printStackTrace();
        }
    }
}