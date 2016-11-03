package handler;

import com.dropbox.core.DbxException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;

import controller.Controller;
import exception.AddServiceFailException;
import exception.LoadSupportedServicesException;
import exception.LoginFailedException;
import exception.NoServicesFoundException;
import exception.NoUserLoggedInException;

import exception.UpdateUserPwErrorException;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.CloudService;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.ServiceType;
import view.BackgroundWallpaper;
import view.ServiceButton;
import view.Time;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/*
 * @Name			: EventhandlerHomeScreen.java
 * @Author		    : Sandro Guerotto & Toshiki Hennig
 * @Describtion	    : Handler class for all Event from the Home Screen
 * @Create on 	    : 20.09.2016
 * @Last modify     : 06.10.2016 Sandro Zeit angepasst und controllmethoden eingefÃ¼gt
 */

public class EventhandlerHomeScreen {
	//@BLUR_AMOUNT is a variable that declares how blurry the picture should be
    private static final double BLUR_AMOUNT = 10;

    //@frostEffect is the blur effect that is called while we load the WebView
    private static final Effect frostEffect = new BoxBlur(BLUR_AMOUNT, BLUR_AMOUNT, 3);

    @FXML
    private VBox pane_pwreset;
   
    
    @FXML
    private JFXPasswordField txt_newPw, txt_oldPw;
    
    @FXML
    private GridPane pane_List;
    
    @FXML
    private AnchorPane pane_mainPane;

    @FXML
    private ListView<String> list_services;
    
    @FXML
    private JFXButton btn_back, btn_pwchange, btn_services, btn_ServiceSave, btn_serviceChange, btn_ServiceDelete;
    
    @FXML
    private StackPane pane_properties, stackpane_pw;
    
    @FXML
    private Label lbl_title, lbl_username, lbl_time, errorlabel, lbl_noService;

    @FXML
    private ProgressIndicator progress;

    @FXML
    private Button btn_settings, btn_newService;

    @FXML
    private Hyperlink btn_logout;

    @FXML
    private WebView wv_services;

    @FXML
    private StackPane pane_login, pane_homeScreen, stackpane_newService;

    @FXML
    private Hyperlink btn_cancel;

    @FXML
    private JFXButton btn_background;

    @FXML
    private FlowPane pane_service;
    
    //@FadeTransition is the Effect that is called while opening the WebView
    private FadeTransition fadeIn;
    private Stage stage;
    private Controller controller;
    //@BackgroundWallpaper has all wallpaper that is loaded in the main_paine
    private BackgroundWallpaper customBackground;

    private WebEngine webEngine;

    Timer timer = new Timer();
    Time clock = new Time();


    //loads everything that is needed at the start
    public void initialize() {

        customBackground = new BackgroundWallpaper();
        list_services.setEditable(true);
    	list_services.setCellFactory(TextFieldListCell.forListView());

        pane_homeScreen.setBackground(customBackground.getBackground());

        progress.setVisible(false);
        
        Platform.runLater(() -> {
            loadservice();
        });
        

        // Von Anfang an Zeit setzen und danach im 2 Sekundentakt
        lbl_title.setText(clock.getText());
        lbl_time.setText(clock.getTime());
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            	//@Platform.runLater is a method that is still running after the call of initialize
                Platform.runLater(() -> {
                    lbl_title.setText(clock.getText());
                    lbl_time.setText(clock.getTime());
                });
            }
        }, 0, 2000);

        //Name setzen
        Platform.runLater(() -> {
            lbl_username.setText(controller.getUsername());
            pane_mainPane.requestFocus(); // Fokus holen, damit am anfangen nichts selektiert ist
        });
    }

    /**
     * 
     * Sets everything visible that is needed to show in the WebView
     * For testing we call Username Sandro, that we can load another stage while we're implementing the dropbox-service
     * @param type is the website that we need as a string
     * getUsername().equals() looks if the Username is available
     * progress.serVisible() shows the progressindicator while loading the Website
     * webEngine.load() loads the Site that we need
     * setWVProps() shows the Webview only if the site is loaded
     * 
     */
    @FXML
    private void setLoginVisible(ServiceType type) {

        if (controller.getUsername().equals("StarLord")) {
            webEngine = wv_services.getEngine();
            progress.setVisible(true);
            progress.setStyle(" -fx-progress-color: white;");

//            webEngine.load("https://www.dropbox.com/1/oauth2/authorize?locale=de_DE&client_id=4ib2r751sawik1x&response_type=code");

            webEngine.load(controller.getLink(type));
            setWVProps();

            Platform.runLater(() -> {
            	controller.setCloudTypeInUse(type);
            });
            

        }

    }
    
    @FXML
    private void getNewService(){
    	if(stackpane_pw.isVisible()){
    		stackpane_newService.setVisible(false);

    	}
    	else{
    		
    		stackpane_newService.setVisible(true);
    	}
    	
    }
    
    @FXML
    private void hideNewService(){
    	stackpane_newService.setVisible(false);
    }
    
    
    @FXML
    private void showServices(){
    	stackpane_pw.setVisible(false);
    	list_services.setVisible(true);
    	pane_List.setVisible(true);
    	btn_ServiceDelete.setVisible(false);
    	btn_serviceChange.setVisible(false);
    	btn_ServiceSave.setVisible(false);
    	ObservableList<String> items;
//		try {
			items = FXCollections.observableArrayList("Test1", "test2");
			 list_services.setItems(items);
//		} catch (NoUserLoggedInException e) {
//			lbl_noService.setText("Es ist kein User angemeldet");
//		}
       
        list_services.refresh();
        list_services.setOnEditStart(new EventHandler<ListView.EditEvent<String>>()
		{
		  	@Override
			public void handle(EditEvent<String> event) {
				editStart(event);
				
			}
		});

    	
    }
    
   
    @FXML
    private void selectServices(){
    	
    	btn_ServiceDelete.setVisible(true);
        btn_ServiceSave.setVisible(true);
        btn_serviceChange.setVisible(true);
    	list_services.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    	if(list_services.getSelectionModel().getSelectedItems().size() >= 2){
    		btn_serviceChange.setVisible(false);
            btn_ServiceSave.setVisible(false);
    	}
    		
             

    }
    
    @FXML
    public void editStart(ListView.EditEvent<String> e)
    {

    	list_services.setOnEditStart(new EventHandler<ListView.EditEvent<String>>()
    			{
    			  	@Override
					public void handle(EditEvent<String> event) {
						editStart(event);
						
					}
    			});

    }


    @FXML
    private void changePw(){
    
    	stackpane_pw.setVisible(true);
    	list_services.setVisible(false);
    	pane_List.setVisible(false);
    }
    
    @FXML
    private void goToHome(){
    	pane_properties.setVisible(false);
    	pane_homeScreen.setEffect(null);
    	btn_settings.setVisible(true);
    	stackpane_pw.setVisible(false);
    	list_services.setVisible(false);
    	pane_List.setVisible(false);
    }
    
    @FXML
    private void setPw(){
    	  if (txt_newPw.getText().isEmpty() || txt_oldPw.getText().isEmpty()){
              errorlabel.setText("Beide Felder ausfüllen");
          }else{
             try {
				controller.setUserPw(txt_oldPw.getText(), txt_newPw.getText());
				pane_pwreset.setVisible(false);
			} catch (UpdateUserPwErrorException e) {
				errorlabel.setText("Falsches Passwort");
			}
              }
              
          }
    
    @FXML
    private void onEnter(KeyEvent ke){
    	 if (ke.getCode().equals(KeyCode.ENTER))
         {
             setPw();
         }
    	
    }
 
    
    @FXML 
    private void showProperties(){
    	
    	pane_properties.setVisible(true);
    	pane_homeScreen.setEffect(new GaussianBlur());
    	btn_settings.setVisible(false);
    	
    }



    @FXML
    private void logout() {

        controller.kill();

    }

    @FXML
    private void setLoginCancel() {
        pane_login.setVisible(false);
        fadeIn.play();
        pane_homeScreen.setEffect(null);
        btn_logout.setVisible(true);
        lbl_title.setVisible(true);
        lbl_time.setVisible(true);
        lbl_username.setVisible(true);

    }

    @FXML
    private void changebackground() {
        customBackground.setResult(customBackground.getResult() + 1);
        pane_homeScreen.setBackground(customBackground.getBackground());
    }


    private void createfadeIn() {
        fadeIn = new FadeTransition(
                Duration.millis(800)
        );
        fadeIn.setNode(pane_login);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setCycleCount(1);
        fadeIn.setAutoReverse(true);
    }

    private void setWVProps() {
        //fadeIn erstellen
        createfadeIn();

        // scrollbar verstecken
        wv_services.getChildrenUnmodifiable().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> change) {
                Set<Node> scrolls = wv_services.lookupAll(".scroll-bar");
                for (Node scroll : scrolls) {
                    scroll.setVisible(false);
                }
            }
        });

        webEngine.getLoadWorker().stateProperty().addListener(
                (ov, oldState, newState) -> {
                    if (newState == State.SUCCEEDED) {

                        pane_login.setVisible(true);
                        fadeIn.play();
                        pane_homeScreen.setEffect(new GaussianBlur());
                        progress.setVisible(false);
                        btn_logout.setVisible(false);
                        lbl_title.setVisible(false);
                        lbl_time.setVisible(false);
                        lbl_username.setVisible(false);
                    }
                });
    }

    private void loadservice(){
        try {

            for (ServiceType service : controller.getServices()) {
                System.out.print(service.getName());
                ServiceButton serviceButton = new ServiceButton(service.getName());
                serviceButton.setOnAction((event) -> setLoginVisible(service));
                pane_service.getChildren().add(serviceButton);
            }
        } catch (LoadSupportedServicesException e) {

            // TODO Nachrichten handlen
        } catch (NoServicesFoundException e) {
            pane_service.getChildren().add(new ServiceButton("plus"));
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

}
