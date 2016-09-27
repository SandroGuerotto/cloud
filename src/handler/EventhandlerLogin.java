package handler;

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
 * Last modify  : 27.09.2016 Sandro Testladen der FXML
 */
public class EventhandlerLogin {

    @FXML
    private Button btn_login;

    private Stage stage;

    @FXML
    private void initialize() {

    }

    @FXML
    void login(ActionEvent event) {


        stage.close();
        StarterHome starterHome = new StarterHome();
        starterHome.start(new Stage());
    }

    /**
     * Methode zum Setzen der Stage -> Popup. Aufgerufen von
     * @param stage wird für Popups genutzt
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}