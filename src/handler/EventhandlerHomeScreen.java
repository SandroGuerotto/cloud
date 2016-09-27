package handler;
import controller.StarterData;
import controller.StarterHome;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.HyperlinkBuilder;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

/*
 * Name			: EventhandlerHomeScreen.java
 * Author		: Sandro Guerotto
 * Describtion	: Handler class for all Event from the Home Screen
 * Create on 	: 20.09.2016
 * Last modify  : 26.29.2016 Sandro Erstellung
 */

public class EventhandlerHomeScreen {

    @FXML
    private Label lbl_title, lbl_username, lbl_time;

    @FXML
    private Button btn_dropbox;

    @FXML
    private Hyperlink btn_logout;
    
    @FXML
    private WebView wv_dropbox;
    
    @FXML
    private StackPane pane_login;
    
   private Stage stage;
    
    @FXML
    private Hyperlink btn_cancel;
    private FadeTransition fadeIn = new FadeTransition(
    	    Duration.millis(700)
 
    );
    
  
    
   
    public void initialize(){
    	
    	fadeIn.setNode(pane_login);
   	    fadeIn.setFromValue(0.0);
   	    fadeIn.setToValue(1.0);
   	    fadeIn.setCycleCount(1);
   	    fadeIn.setAutoReverse(true);
      
       
    }
    
    @FXML
    private void setLoginVisible(){

        stage.close();
        StarterData starterData = new StarterData();
        starterData.start(new Stage());

//    	pane_login.setVisible(true);
//      	fadeIn.play();
//        WebEngine webEngine = wv_dropbox.getEngine();
//     	webEngine.load("https://www.dropbox.com/login");
    	
    	
    }
    
    @FXML
    private void setLoginCancel(){
       	pane_login.setVisible(false);
    	fadeIn.play();
    	
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

}
