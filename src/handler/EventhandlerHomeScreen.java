package handler;

import com.jfoenix.controls.JFXButton;
import controller.Controller;
import exception.LoadSupportedServicesException;
import exception.NoServicesFoundException;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.datacontract.schemas._2004._07.PrettySecureCloud_Model.ServiceType;
import view.BackgroundWallpaper;
import view.ServiceButton;
import view.Time;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/*
 * @Name			: EventhandlerHomeScreen.java
 * @Author		    : Sandro Guerotto & Toshiki Hennig
 * @Describtion	    : Handler class for all Event from the Home Screen
 * @Create on 	    : 20.09.2016
 * @Last modify     : 06.10.2016 Sandro Zeit angepasst und controllmethoden eingefügt
 */

public class EventhandlerHomeScreen {

    private static final double BLUR_AMOUNT = 10;

    private static final Effect frostEffect = new BoxBlur(BLUR_AMOUNT, BLUR_AMOUNT, 3);

    @FXML
    private AnchorPane pane_mainPane;

    @FXML
    private Label lbl_title, lbl_username, lbl_time;

    @FXML
    private ProgressIndicator progress;


    @FXML
    private Hyperlink btn_logout;

    @FXML
    private WebView wv_services;

    @FXML
    private StackPane pane_login, pane_homeScreen;

    @FXML
    private Hyperlink btn_cancel;

    @FXML
    private JFXButton btn_background;

    @FXML
    private FlowPane pane_service;

    private FadeTransition fadeIn;
    private Stage stage;
    private Controller controller;
    private BackgroundWallpaper customBackground;

    private WebEngine webEngine;

    Timer timer = new Timer();
    Time clock = new Time();


    public void initialize() {

        customBackground = new BackgroundWallpaper();

        pane_homeScreen.setBackground(customBackground.getBackground());

        progress.setVisible(false);

        Platform.runLater(() -> loadservice());


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

        //Name setzen
        Platform.runLater(() -> {
            lbl_username.setText(controller.getUsername());
            pane_mainPane.requestFocus(); // Fokus holen, damit am anfangen nichts selektiert ist
        });
    }

    @FXML
    private void setLoginVisible(String type) {

        if (controller.getUsername().equals("Sandro")) {
            controller.gotoData(stage);
        } else {

            webEngine = wv_services.getEngine();
            progress.setVisible(true);
            progress.setStyle(" -fx-progress-color: white;");

//            webEngine.load("https://www.dropbox.com/1/oauth2/authorize?locale=de_DE&client_id=4ib2r751sawik1x&response_type=code");

            webEngine.load(controller.getLink(type));


            setWVProps();

        }

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
                serviceButton.setOnAction((event) -> setLoginVisible(service.getName()));
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
