package handler;

import controller.Controller;
import controller.StarterHome;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/*
 * Name			: EventhandlerLogin.java
 * Author		: Sandro Guerotto
 * Describtion	: Handler class for all Event from the Login Screen
 * Create on 	: 20.09.2016
 * Last modify  : 06.10.2016 Sandro Setter f¸r Controller
 */
public class EventhandlerLogin {

    @FXML
    private Button btn_login;

    private Stage stage;
    private Controller controller;

    @FXML
    private void initialize() {

    }

    @FXML
    void login(ActionEvent event) {


//        stage.close();
        // login methoden
        controller.gotoHome(stage);
    }

    /**
     * Methode zum Setzen der Stage -> Popup. Aufgerufen von
     * @param stage wird f√ºr Popups genutzt
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public void setController(Controller controller){
    	this.controller = controller;
    }
}