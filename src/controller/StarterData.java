package controller;

/**
 * @author           :   Sandro Guerotto
 * @Created          :   27.09.2016
 * @Project          :   cloud
 * @Package          :   controller
 * @version          :   1.0
 * @LastUpdated      :
 * @Description      :   Starter class für das Data GUI
 */

import com.dropbox.core.DbxException;
import exception.ConnectionErrorException;
import exception.UploadException;
import handler.EventhandlerDataScreen;
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
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;

public class StarterData{

	protected static Controller controller;
	
    public void start(Stage stage, Controller controller) {
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
                    String filePath = null;
                    try {
                        controller.upload_data(db.getFiles());
                    } catch (UploadException e) {
                        e.printStackTrace();
                    } catch (ConnectionErrorException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DbxException e) {
                        e.printStackTrace();
                    }
//                    for (File file:db.getFiles()) {
//                        filePath = file.getAbsolutePath();
//
//                    }
                }
                event.setDropCompleted(success);
                event.consume();
            });
            
            stage.setOnCloseRequest(t -> controller.kill());
            
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
//	public void setController(Controller controller){
//		this.controller = controller;
//	}
}