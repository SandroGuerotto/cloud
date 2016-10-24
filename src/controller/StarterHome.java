package controller;

/**
 * @author           :   Sandro Guerotto
 * @Created          :   27.09.2016
 * @Project          :   cloud
 * @Package          :   controller
 * @version          :   1.0
 * @LastUpdated      :
 * @Description      :   Starter class für das Home GUI
 */

import handler.EventhandlerHomeScreen;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StarterHome {
	
	protected static Controller controller;

    public void start(Stage stage, Controller controller) {
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
            eventhandlerHomeScreen.setController(controller);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(this.getClass().getResource("../view/application.css").toExternalForm());

            //set font style
//			Font.loadFont(getClass().getResourceAsStream("../font/Dosis-Light.ttf"), 14);
            Font.loadFont(getClass().getResourceAsStream("../font/Dosis-Bold.ttf"), 14);
            Font.loadFont(getClass().getResourceAsStream("../font/Dosis-Medium.ttf"), 14);

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            @Override
	            public void handle(WindowEvent t) {
	                controller.kill();
	            }
	        });
            
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
    
//
//	public void setController(Controller controller){
//		this.controller = controller;
//	}
	
}