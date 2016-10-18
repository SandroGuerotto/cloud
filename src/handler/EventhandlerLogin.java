package handler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.Controller;
import controller.StarterHome;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author          :   Sandro Guerotto
 * Created          :   20.09.2016
 * Project          :   cloud
 * Package          :   view
 * @version         :   1.0
 * LastUpdated      :
 * Description      :   Contains all handler for Login and init Screen
 */
public class EventhandlerLogin {


    @FXML
    private JFXTextField tf_username;

    @FXML
    private JFXPasswordField tf_password;

    @FXML
    private Button btn_login;

    @FXML
    private Label lbl_login_error;

    @FXML
    private VBox pane_login;

    private Stage stage;
    private Controller controller;

    @FXML
    private void initialize() {
        lbl_login_error.setDisable(true);
        lbl_login_error.setVisible(false);
        Platform.runLater(() -> {
            pane_login.requestFocus();
                });

    }

    @FXML
    void login(ActionEvent event) {

        if (tf_username.getText().isEmpty() || tf_password.getText().isEmpty()){
            lbl_login_error.setText("Bitte alles ausfüllen!");
            lbl_login_error.setDisable(false);
            lbl_login_error.setVisible(true);
        }else{
            //controller.login(tf_username.getText(), tf_password.getText());

            controller.gotoHome(stage);
        }


    }

    /**
     * Methode zum Setzen der Stage -> Popup. Aufgerufen von
     * @param stage wird für Popups genutzt
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public void setController(Controller controller){
    	this.controller = controller;
    }
}