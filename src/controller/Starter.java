package controller;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author      :   Sandro Guerotto
 * @version     :   3.0
 * @created     :   27.09.2016
 * @Project     :   cloud
 * @Package     :   controller
 * @LastUpdated :   02.11.2016
 * @Description :   Starterclass for whole application
 */
public class Starter extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller controller = new Controller();
        controller.start(primaryStage);
    }
}
