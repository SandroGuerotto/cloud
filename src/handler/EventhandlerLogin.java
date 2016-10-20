package handler;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.Controller;
import controller.StarterHome;
import exception.EmailExistException;
import exception.LoginFailedException;
import exception.UserExistException;
import exception.UsernameHasToBeFilledOutException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.rmi.RemoteException;

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
    private JFXTextField tf_username, tf_email;

    @FXML
    private JFXPasswordField tf_password;

    @FXML
    private Button btn_login, btn_signup, btn_back, btn_register;

    @FXML
    private Label lbl_login_error;

    @FXML
    private VBox pane_login;

    @FXML
    private VBox pane_agb;

    @FXML
    private JFXCheckBox btn_agb;

    private Stage stage;
    private Controller controller;

    @FXML
    private void initialize() {
        init(); //Knöpf verstecken und fokus setzen

    }

    @FXML
    private void login(ActionEvent event) {

        if (tf_username.getText().isEmpty() || tf_password.getText().isEmpty()){
            lbl_login_error.setText("Bitte alles ausfüllen!");
            lbl_login_error.setDisable(false);
            lbl_login_error.setVisible(true);
        }else{
            try {
                controller.login(tf_username.getText(), tf_password.getText());
            } catch (LoginFailedException e) {
                lbl_login_error.setText(e.getMsg());
                lbl_login_error.setDisable(false);
                lbl_login_error.setVisible(true);
                return;
            }
            controller.gotoHome(stage);
        }


    }

    @FXML
    private void signup(ActionEvent event){
        lbl_login_error.setDisable(true);
        lbl_login_error.setVisible(false);

        tf_email.setManaged(true);
        tf_email.setVisible(true);
        tf_email.setDisable(false);

        btn_back.setVisible(true);
        btn_back.setDisable(false);

        btn_register.setVisible(true);
        btn_register.setDisable(false);

        btn_login.setVisible(false);
        btn_login.setDisable(true);

        btn_signup.setVisible(false);
        btn_signup.setDisable(true);

        tf_password.setText("");
        tf_username.setText("");

        pane_agb.setVisible(true);
        pane_agb.setDisable(false);

    }



    @FXML
    private void register(ActionEvent event){

        if (tf_username.getText().isEmpty() || tf_password.getText().isEmpty() || tf_email.getText().isEmpty()){
            lbl_login_error.setText("Bitte alles ausfüllen!");
            lbl_login_error.setDisable(false);
            lbl_login_error.setVisible(true);
        }else{
            if(!btn_agb.isSelected()){
                lbl_login_error.setText("Bitte AGBs akzeptieren");
                lbl_login_error.setDisable(false);
                lbl_login_error.setVisible(true);
                return;
            }
            try {
                controller.register(tf_username.getText(), tf_email.getText(), tf_password.getText());
            } catch (RemoteException e) {
                lbl_login_error.setText("Ein Fehler ist aufgetreten");
                lbl_login_error.setDisable(false);
                lbl_login_error.setVisible(true);
                return;
            } catch (EmailExistException e) {
                lbl_login_error.setText(e.getMsg());
                lbl_login_error.setDisable(false);
                lbl_login_error.setVisible(true);
                return;
            } catch (UserExistException e) {
                lbl_login_error.setText(e.getMsg());
                lbl_login_error.setDisable(false);
                lbl_login_error.setVisible(true);
                return;
            }
            controller.gotoHome(stage);
        }
    }

    @FXML
    private void back(ActionEvent event){
        init();

    }


    private void init(){
        lbl_login_error.setDisable(true);
        lbl_login_error.setVisible(false);

        tf_email.setDisable(true);
        tf_email.setVisible(false);
        tf_email.setManaged(false);
        tf_email.setText("");

        tf_password.setText("");
        tf_username.setText("");

        btn_back.setVisible(false);
        btn_back.setDisable(true);

        btn_register.setVisible(false);
        btn_register.setDisable(true);

        btn_login.setVisible(true);
        btn_login.setDisable(false);

        btn_signup.setVisible(true);
        btn_signup.setDisable(false);

        pane_agb.setVisible(false);
        pane_agb.setDisable(true);

        Platform.runLater(() -> {
            pane_login.requestFocus();
        });
    }

    @FXML
    private void onEnter(KeyEvent ke){
        if (ke.getCode().equals(KeyCode.ENTER))
        {
            if (tf_email.isVisible()){
                register(new ActionEvent());
            }else{
                login(new ActionEvent());
            }
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