package handler;
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
import javafx.scene.web.WebView;
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
    
   
    
    @FXML
    private Hyperlink btn_cancel;
    private FadeTransition fadeIn = new FadeTransition(
    	    Duration.millis(1000)
 
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
    	pane_login.setVisible(true);
      	fadeIn.play();
    	
    	
    }
    
    @FXML
    private void setLoginCancel(){
       	pane_login.setVisible(false);
    	fadeIn.play();
    	
    }

}
