package handler;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import controller.Controller;
import exception.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import thread.IConnectorThread;

import java.rmi.RemoteException;

/**
 * @author : Sandro Guerotto
 * @version : 2.1
 * @created : 20.09.2016
 * @project : cloud
 * @package : handler
 * @lastUpdated : 03.14.2016
 * @description : Contains all handler for Login and init Screen
 */
public class EventhandlerLogin implements IConnectorThread {

	@FXML
	private JFXTextField tf_username, tf_email;

	@FXML
	private JFXPasswordField tf_password;

	@FXML
	private Button btn_login, btn_signup, btn_back, btn_register;

	@FXML
	private Label lbl_login_error;

	@FXML
	private VBox pane_login, pane_progress;

	@FXML
	private VBox pane_agb;

	@FXML
	private JFXCheckBox btn_agb;

	@FXML
	private ProgressIndicator pb_loading;

	private Stage stage;
	private Controller controller;

	@FXML
	private void initialize() {
		init();

		new Thread(() -> {
			try {
				onWorkStart();
				controller.startServerConnecter();
				onWorkEnd();
			} catch (FailLoadingServicesException | ConnectionErrorException e) {
				onWorkError(e);
				e.printStackTrace();
			}
		}).start();

	}

	/**
	 * calls login method for controller checks previously if all inputs are
	 * filled out
	 */
	@FXML
	private void login() {

		if (tf_username.getText().isEmpty() || tf_password.getText().isEmpty()) {
			lbl_login_error.setText("Bitte alles ausfüllen!");
			lbl_login_error.setDisable(false);
			lbl_login_error.setVisible(true);
		} else {
			controller.login(tf_username.getText(), tf_password.getText(), this);
		}

	}

	/**
	 * calls the signup screen
	 *
	 * @param event
	 *            ActionEvent from button
	 */
	@FXML
	private void signup(ActionEvent event) {
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

	/**
	 * first checks if all inputs are provided and after all information are
	 * sent to server if an error accoured, a message will show up
	 *
	 * @param event
	 *            ActionEvent from button
	 */
	@FXML
	private void register(ActionEvent event) {

		if (tf_username.getText().isEmpty() || tf_password.getText().isEmpty() || tf_email.getText().isEmpty()) {
			lbl_login_error.setText("Bitte alles ausfüllen!");
			lbl_login_error.setDisable(false);
			lbl_login_error.setVisible(true);
		} else {
			if (!btn_agb.isSelected()) {
				lbl_login_error.setText("Bitte AGBs akzeptieren");
				lbl_login_error.setDisable(false);
				lbl_login_error.setVisible(true);
			} else {
				try {
					lockInput();
					controller.register(tf_username.getText(), tf_email.getText(), tf_password.getText(), this);
					controller.gotoHome(stage);
				} catch (RemoteException e) {
					lbl_login_error.setText("Verbindung mit dem Server nicht möglich");
					lbl_login_error.setDisable(false);
					lbl_login_error.setVisible(true);
				} catch (EmailExistException e) {
					lbl_login_error.setText(e.getMsg());
					lbl_login_error.setDisable(false);
					lbl_login_error.setVisible(true);
				} catch (UserExistException e) {
					lbl_login_error.setText(e.getMsg());
					lbl_login_error.setDisable(false);
					lbl_login_error.setVisible(true);
				}
				unlockInput();
			}

		}
	}

	/**
	 * locks login/register screen till server responds
	 */
	private void lockInput() {
		Platform.runLater(() -> {
			pb_loading.setStyle(" -fx-progress-color:  #38424b;");

			pane_progress.setDisable(false);
			pane_progress.setVisible(true);

			pane_login.setDisable(true);
		});
	}

	/**
	 * after sending information to the server and an error accoured screen will
	 * be unlocked to correct inputs
	 */
	private void unlockInput() {
		Platform.runLater(() -> {
			pane_progress.setDisable(true);
			pane_progress.setVisible(false);

			pane_login.setDisable(false);
		});
	}

	/**
	 * setups login screen
	 *
	 * @param event
	 *            ActionEvent from button
	 */
	@FXML
	private void back(ActionEvent event) {
		init();

	}

	/**
	 * initalizes screen when first called
	 */
	private void init() {
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

		pane_progress.setDisable(true);
		pane_progress.setVisible(false);

		Platform.runLater(() -> pane_login.requestFocus());
	}

	/**
	 * automatically calls register or login depends on which option is selected
	 *
	 * @param ke
	 *            Keyevent too listen on Enter
	 */
	@FXML
	private void onEnter(KeyEvent ke) {
		if (ke.getCode().equals(KeyCode.ENTER)) {
			if (tf_email.isVisible()) {
				register(new ActionEvent());
			} else {
				login();
			}
		}
	}

	/**
	 * This methode save the primary stage that will be used switching screens
	 *
	 * @param stage
	 *            primary stage. Usuage for all other screens
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Setting the controller for method calls
	 *
	 * @param controller
	 *            actual controller. Created in Starter class
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void onWorkStart() {
		lockInput();
	}

	@Override
	public void onWorkEnd() {
		unlockInput();
		Platform.runLater(() -> controller.gotoHome(this.stage));
	}

	@Override
	public void onWorkError(CloudException e) {
		Platform.runLater(() -> {
			lbl_login_error.setText(e.getMsg());
			lbl_login_error.setDisable(false);
			lbl_login_error.setVisible(true);
		});
		unlockInput();
	}
}