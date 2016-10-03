package handler;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import controller.StarterData;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
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

	Calendar cal;
    @FXML
    private Label lbl_title, lbl_username, lbl_time;

    LocalTime midnight = LocalTime.MIDNIGHT;
    
    @FXML
    private Button btn_dropbox;

    @FXML
    private Hyperlink btn_logout;
    
    @FXML
    private WebView wv_dropbox;
    
    @FXML
    private StackPane pane_login;
    
   private Stage stage;
    
   SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    @FXML
    private Hyperlink btn_cancel;
    private FadeTransition fadeIn = new FadeTransition(
    	    Duration.millis(700)
 
    );
    
    Timer timer = new Timer();
    
   
    public void initialize(){
    	
    	fadeIn.setNode(pane_login);
   	    fadeIn.setFromValue(0.0);
   	    fadeIn.setToValue(1.0);
   	    fadeIn.setCycleCount(1);
   	    fadeIn.setAutoReverse(true);
   	 timer.scheduleAtFixedRate(new TimerTask() {
         @Override
         public void run() {
        	 Platform.runLater(()->  { 
        		cal = Calendar.getInstance();
        	 lbl_time.setText("" + sdf.format(cal.getTime()) );   });
        
     }
         }, 0, 2000);
      
       
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
