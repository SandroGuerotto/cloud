package handler;

import com.dropbox.core.DbxException;
import com.jfoenix.controls.JFXButton;

import com.jfoenix.controls.JFXPasswordField;


import controller.Controller;
import exception.AddServiceFailException;
import exception.LoadSupportedServicesException;

import exception.NoServicesFoundException;
import exception.NoUserLoggedInException;
import exception.UpdateServiceErrorException;
import exception.UpdateUserPwErrorException;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker.State;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.CloudService;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.ServiceType;
import view.BackgroundWallpaper;
import view.ServiceButton;
import view.Time;

import java.awt.MouseInfo;
import java.awt.Point;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Name			: EventhandlerHomeScreen.java
 * @Author		    : Sandro Guerotto & Toshiki Hennig
 * @Describtion	    : Handler class for all Event from the Home Screen
 * @project			: cloud
 * @package			: handler
 * @Create on 	    : 20.09.2016
 * @Last modify     : 07.11.2016 Toshiki Resizing verbessert
 **/

public class EventhandlerHomeScreen {
	//@BLUR_AMOUNT is a variable that declares how blurry the picture should be
    private static final double BLUR_AMOUNT = 10;
    
    Point p = MouseInfo.getPointerInfo().getLocation();
    //@frostEffect is the blur effect that is called while we load the WebView
    private static final Effect frostEffect = new BoxBlur(BLUR_AMOUNT, BLUR_AMOUNT, 3);

    @FXML
    private VBox pane_pwreset;
   
    
    @FXML
    private JFXPasswordField txt_newPw, txt_oldPw;
    
    @FXML
    private GridPane pane_List, grid_mainPane, grid_Service, pane_settings, gridpane, pane_properties;
    
    @FXML
    private AnchorPane pane_mainPane, anchorpane;

    @FXML
    private ListView<CloudService> list_services, list_serviceChooser;
    
    @FXML
    private JFXButton btn_back, btn_pwchange, btn_services, btn_ServiceDelete, btn_SaveNewService;
    
    @FXML
    private StackPane stackpane_pw, pane_createSettings;
    
    @FXML
    private Label lbl_title, lbl_username, lbl_time, errorlabel, lbl_Service, lbl_mainError, lbl_mainErrorMessage;

    @FXML
    private ProgressIndicator progress;

    @FXML
    private Button btn_settings, btn_newService;

    @FXML
    private Hyperlink btn_logout;

    @FXML
    private WebView wv_services;

    @FXML
    private StackPane pane_login, pane_serviceChooser, pane_btnServices;

    Stage stage;
    ServiceButton serviceButton;
    
    @FXML
    private Hyperlink btn_cancel;

    @FXML
    private JFXButton btn_background;

    @FXML
    private FlowPane pane_service;
    
    //@FadeTransition is the Effect that is called while opening the WebView
    private FadeTransition fadeIn;
    private Controller controller;
    //@BackgroundWallpaper has all wallpaper that is loaded in the main_paine
    private BackgroundWallpaper customBackground;
    private boolean buttonClicked = false;
    private WebEngine webEngine;

    Timer timer = new Timer();
    Time clock = new Time();

    /**
     * Initializes all necessary elements after opening HomeScreen.fxml
     * @throws NoUserLoggedInException
     * 
     */
    //loads everything that is needed at the start
    public void initialize() {
    	
    	
        list_services.refresh();
        customBackground = new BackgroundWallpaper();
        list_services.setEditable(true);
//    	list_services.setCellFactory(TextFieldListCell.forListView());
        list_services.setCellFactory(lv -> {
            TextFieldListCell<CloudService> cell = new TextFieldListCell<CloudService>();
            cell.setConverter(new StringConverter<CloudService>() {
                public String toString(CloudService cloud) {
                    return cloud.getName();
                }
                public CloudService fromString(String string) {
                    CloudService service = cell.getItem();
                    service.setName(string);
                    return service ;
                }
            });
            return cell;
        });
        
        list_serviceChooser.setCellFactory(lv -> {
            TextFieldListCell<CloudService> cell = new TextFieldListCell<CloudService>();
            cell.setConverter(new StringConverter<CloudService>() {
                public String toString(CloudService cloud) {
                    return cloud.getName();
                }
                public CloudService fromString(String string) {
                    CloudService service = cell.getItem();
                    service.setName(string);
                    return service ;
                }
            });
            return cell;
        });

        grid_mainPane.setBackground(customBackground.getBackground());

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
      
            Thread loadUser = new Thread(new Runnable() {
            public void run() {
            	 Platform.runLater(() -> {
                     lbl_username.setText(controller.getUsername());
                     pane_mainPane.requestFocus(); // Fokus holen, damit am anfangen nichts selektiert ist
                     ObservableList<CloudService> items;
             		
     				try {
     					items = FXCollections.observableArrayList(controller.getLoggedInUser().getServices());
     					list_services.setItems(items);
     					list_serviceChooser.setItems(items);
     				} catch (NoUserLoggedInException e) {
     					// TODO Auto-generated catch block
     					e.printStackTrace();
     				}
                 });
            }
       });  
        loadUser.start();
        //Name setzen
       
    }

    
   
    /**
     * 
     * Method that redirects to the DataScreen
     * @param type is the website that we need as a string
     * @throws NoUserLoggedInException
     * 
     */
    @FXML
    private void setLoginVisible(ServiceType type) {
    	
    	
    	Bounds bound = serviceButton.localToScreen(serviceButton.getBoundsInLocal());
    	if(buttonClicked){
    		pane_serviceChooser.setVisible(false);
    		buttonClicked = false;
    	
    	}
    	else{
    		pane_serviceChooser.setVisible(true);
        	pane_serviceChooser.setLayoutX(bound.getMaxX());
        	pane_serviceChooser.setLayoutY(bound.getMinY());
        	buttonClicked = true;
        	try {
    			if(controller.getLoggedInUser().getServices().length == 0){
    				showLoginWithNoServices(type);
    				pane_serviceChooser.setVisible(false);
    			}
    		} catch (NoUserLoggedInException e) {
    			lbl_mainError.setText(e.getMessage());
    		}
        	
    	}
    	list_serviceChooser.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                System.out.println("clicked on " + list_serviceChooser.getSelectionModel().getSelectedItem());
               pane_serviceChooser.setVisible(false);
                   webEngine = wv_services.getEngine();
                    progress.setVisible(true);
                    progress.setStyle(" -fx-progress-color: white;");

//                    webEngine.load("https://www.dropbox.com/1/oauth2/authorize?locale=de_DE&client_id=4ib2r751sawik1x&response_type=code");

                    webEngine.load(controller.getLink(type));
                    setWVProps();
                    
                    Platform.runLater(() -> {
                    	controller.setCloudTypeInUse(type);
                    });
             
            }
        });	
    	
       
       

    }
    /**
     * 
     * opens the Webview if the User has no Services
     * @param type is the website that we need as a string
     * @throws AddServiceFailException
     * @throws NoUserLoggedInException
     * 
     */
    @FXML
    private void showLoginWithNoServices(ServiceType type){
    	pane_serviceChooser.setVisible(false);
    	 if (controller.getUsername().equals("StarLord")) {
             webEngine = wv_services.getEngine();
             progress.setVisible(true);
             progress.setStyle(" -fx-progress-color: white;");


//             webEngine.load("https://www.dropbox.com/1/oauth2/authorize?locale=de_DE&client_id=4ib2r751sawik1x&response_type=code");

             webEngine.load(controller.getLink(type));
             setWVProps();
             
             Platform.runLater(() -> {
             	controller.setCloudTypeInUse(type);
             });
             
             onPageFinished(wv_services, controller.getLink(type));
        }else{
            try {
                controller.setCloudTypeInUse(type);
                controller.gotoData(stage);
            } catch (AddServiceFailException e) {
                e.printStackTrace();
            } catch (NoUserLoggedInException e) {
                e.printStackTrace();
            }
        }


         }
    public void onPageFinished(final WebView webView, final String url) {

    	
    }
    
    /**
     * shows the button for adding a new Service
     */
    @FXML
    private void getNewService(){
    	   		
    		btn_newService.setVisible(true);
    
    	
    }
    /**
     * hides the button for adding a new Service
     */
    
    @FXML
    private void hideNewService(){
    	btn_newService.setVisible(false);
    }
    /**
     * shows the pane for the Services
     */
    @FXML
    private void createNewService(){
    	pane_createSettings.setVisible(true);
    	pane_createSettings.setLayoutY(120);
    	
    }
    
    
    /**
     * shows all Services the user has
     * @throws NoUserLoggedInException
     */
    @FXML
    private void showServices(){
    	pane_createSettings.setVisible(false);
    	stackpane_pw.setVisible(false);
    	list_services.setVisible(true);
    	pane_List.setVisible(true);
    	btn_ServiceDelete.setVisible(true);
    	lbl_Service.setText(null);
    	errorlabel.setText(null);
    	try {
			if(controller.getLoggedInUser().getServices().length == 0){
				lbl_Service.setText("Keine Services vorhanden");
				list_services.setDisable(true);
			}
		} catch (NoUserLoggedInException e) {
			lbl_Service.setText("Kein User eingeloggt");
		}
        list_services.setOnEditStart(new EventHandler<ListView.EditEvent<CloudService>>()
		{
		  	@Override
			public void handle(EditEvent<CloudService> event) {
				editStart(event);
				
			}
		});
        list_services.setOnEditCommit(new EventHandler<ListView.EditEvent<CloudService>>()
        		{
        		    @Override
        		    public void handle(EditEvent<CloudService> event)
               		    {
        		    		list_services.getItems().set(event.getIndex(), event.getNewValue());
              		        editCommit(event);
              		        lbl_Service.setStyle("-fx-text-fill: green");
              		        lbl_Service.setText("Service wurde ge�ndert");
        	        		    }

				});


    	
    }
    
   
    @FXML
    private void selectServices(){
    	lbl_Service.setText(null);
    	btn_ServiceDelete.setVisible(true);
    	list_services.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    		
             

    }
    
    /**
     * 
     * on doubleclick lets you edit the service
     * @param e is the Service from CloudService
     */
    @FXML
    public void editStart(ListView.EditEvent<CloudService> e)
    {

    	list_services.setOnEditStart(new EventHandler<ListView.EditEvent<CloudService>>()
    			{
    			  	@Override
					public void handle(EditEvent<CloudService> event) {
						editStart(event);
						lbl_Service.setText(null);
					}
    			});

    }
    
    /**
     * 
     * after method editstart the changes will be commited(saved)
     * @param e is the Service from CloudService
     */
    @FXML
    public void editCommit(ListView.EditEvent<CloudService> e)
    {
    	CloudService serviceEdited = list_services.getItems().get(e.getIndex());
    	try {
			controller.updateCloudConnection(serviceEdited, e.getNewValue().getName());
		} catch (UpdateServiceErrorException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	list_services.getItems().set(e.getIndex(), e.getNewValue());
    	
    
    }

     


    /**
     * shows the pane to edit the password
     */
    @FXML
    private void changePw(){
    	stackpane_pw.setVisible(true);
    	list_services.setVisible(false);
    	pane_List.setVisible(false);
    	lbl_Service.setText(null);
    	pane_pwreset.setVisible(true);
    	txt_oldPw.setText(null);
    	txt_newPw.setText(null);
    	pane_createSettings.setVisible(false);
    }
    
    /**
     * closes the settings menu
     */
    @FXML
    private void goToHome(){
    	pane_properties.setVisible(false);
    	grid_mainPane.setEffect(null);
    	btn_settings.setVisible(true);
    	stackpane_pw.setVisible(false);
    	list_services.setVisible(false);
    	pane_List.setVisible(false);
    	lbl_Service.setText(null);
    	errorlabel.setText(null);
    }
    
    
    /**
     * updates the old password to new password
     * @throws UpdateUserPwErrorException
     * @throws NullPointerException
     * 
     */
    @FXML
    private void setPw(){

    	try{
    	  if (txt_newPw.getText().trim().isEmpty() || 
    		  txt_oldPw.getText().trim().isEmpty()){
              errorlabel.setText("Beide Felder ausf�llen");

    	  if (txt_newPw.getText().isEmpty() || txt_oldPw.getText().isEmpty()){
              errorlabel.setText("Beide Felder ausf�llen");

          }else{
             try {
				controller.setUserPw(txt_oldPw.getText(), txt_newPw.getText());
				pane_pwreset.setVisible(false);
				errorlabel.setText(null);
			} catch (UpdateUserPwErrorException e) {
				errorlabel.setText("Falsches Passwort");
			}
              }
    	}
    	  }catch(NullPointerException e){
    		errorlabel.setText("Beide Felder ausf�llen");
    	}
              
          }
    
    /**
     * if enter is pressed calls the method setPw()
     * @param ke gets the pressed key
     */
    @FXML
    private void onEnter(KeyEvent ke){
    	 if (ke.getCode().equals(KeyCode.ENTER))
         {
             setPw();
         }
    	
    }
 
    /**
     * shows the properties
     */
    @FXML 
    private void showProperties(){
    	
    	pane_properties.setVisible(true);
    	grid_mainPane.setEffect(new GaussianBlur());
    	btn_settings.setVisible(false);
    	pane_settings.setVisible(true);
    	
    	
    }


    /**
     * calls the method kill()
     */
    @FXML
    private void logout() {

        controller.kill();

    }

    /**
     * while logging in to dropbox lets you cancel the webview
     */
    @FXML
    private void setLoginCancel() {
        pane_login.setVisible(false);
        fadeIn.play();
        grid_mainPane.setEffect(null);
        btn_logout.setVisible(true);
        lbl_title.setVisible(true);
        lbl_time.setVisible(true);
        lbl_username.setVisible(true);

    }

    /**
     * changes the current background
     */
    @FXML
    private void changebackground() {
        customBackground.setResult(customBackground.getResult() + 1);
        grid_mainPane.setBackground(customBackground.getBackground());
    }

    /**
     * created the fadein used while opening the webview
     */
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

    /**
     * sets all necessary properties while calling the webview
     */
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
                        grid_mainPane.setEffect(new GaussianBlur());
                        progress.setVisible(false);
                        btn_logout.setVisible(false);
                        lbl_title.setVisible(false);
                        lbl_time.setVisible(false);
                        lbl_username.setVisible(false);
                    }
                });
    }

    /**
     * loads all the services a user has
     * @throws LoadSupportedServiceException
     * @throws NoServiceFoundException
     * 
     */
    private void loadservice(){
        try {

            for (ServiceType service : controller.getServices()) {
                System.out.print(service.getName());
                serviceButton = new ServiceButton(service.getName());
                serviceButton.setOnAction((event) -> setLoginVisible(service));
                pane_btnServices.getChildren().add(serviceButton);
//                pane_service.getChildren().add(serviceButton);
               }
        } catch (LoadSupportedServicesException e) {

            // TODO Nachrichten handlen
        } catch (NoServicesFoundException e) {
            pane_service.getChildren().add(new ServiceButton("plus"));
        }
    }

    public void setStage(Stage stage) {
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

}
