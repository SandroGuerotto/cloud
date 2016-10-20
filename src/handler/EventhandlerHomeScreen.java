package handler;

import javafx.concurrent.Worker.State;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import view.BackgroundWallpaper;
import view.Time;
import controller.Controller;
import controller.StarterData;
import controller.StarterLogin;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
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
 * Last modify  : 06.10.2016 Sandro Zeit angepasst und controllmethoden eingefügt
 */

public class EventhandlerHomeScreen extends BackgroundWallpaper {

	@FXML
	private AnchorPane pane_mainPane;
	
    @FXML
    private Label lbl_title, lbl_username, lbl_time;

    @FXML
    private ProgressIndicator progress;
    Time clock = new Time();
    
    @FXML
    private Button btn_dropbox;

    @FXML
    private Hyperlink btn_logout;

    @FXML
    private WebView wv_dropbox;

    @FXML
    private StackPane pane_login;
    
    private Stage stage;
    private Controller controller;

    @FXML
    private Hyperlink btn_cancel;
    
    private FadeTransition fadeIn = new FadeTransition(
            Duration.millis(800)
    );

    Timer timer = new Timer();
    private String username = "Toshiki";
   
    
   

    public void initialize() {
    	pane_mainPane.setBackground(background);
    	progress.setVisible(false);
    	lbl_username.setText(username);
        fadeIn.setNode(pane_login);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setCycleCount(1);
        fadeIn.setAutoReverse(true);
        // Von Anfang an Zeit setzen und danach im 2 Sekundentakt
        lbl_title.setText(clock.getText());
        lbl_time.setText(clock.getTime());
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    
                    lbl_title.setText(clock.getText());
                    lbl_time.setText(clock.getTime());

                });


            }
        }, 0, 2000);


    }

    @FXML
    private void setLoginVisible() {

//    	 controller.gotoData(stage);
    	 
    	
      	WebEngine webEngine = wv_dropbox.getEngine();
      	webEngine.load("https://www.dropbox.com/login");
     	progress.setVisible(true);
      	progress.setStyle(" -fx-progress-color: white;");
      	
      	webEngine.getLoadWorker().stateProperty().addListener(
      	        new ChangeListener<State>() {
      	            public void changed(ObservableValue ov, State oldState, State newState) {
      	                if (newState == State.SUCCEEDED) {
      	                	pane_login.setVisible(true);
      	                	fadeIn.play();
      	                	progress.setVisible(false);
      	                	
      	                }
      	            }
      	        });


    }
    
    @FXML
    private void logout(){
      
//      Stage stage = (Stage) btn_cancel.getScene().getWindow();
//      image = null;
//      background = null;
//      stage.close();
//      this.stage = null;
//      StarterLogin starterLogin = new StarterLogin();
//      starterLogin.start(new Stage());
    	System.exit(0);
    	
    }

    @FXML
    private void setLoginCancel() {
        pane_login.setVisible(false);
        fadeIn.play();

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void setController(Controller controller){
    	this.controller = controller;
    }

}
