package handler;

import com.jfoenix.controls.JFXProgressBar;
import com.sun.prism.impl.Disposer.Record;
import controller.Controller;
import exception.CloudException;
import exception.DeleteException;
import exception.ExceptionType;
import exception.NoFilesException;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import message.Message;
import model.Data;
import thread.IWorkThread;
import view.PTableColumn;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author      :   Sandro Guerotto
 * @version     :   1.0
 * @created     :   20.09.2016
 * @project     :   cloud
 * @package     :   handler
 * @lastupdate  :   04.11.2016 / by Sandro Guerotto
 * @description :   Contains all handler for DataScreen and init Screen
 */

public class EventhandlerDataScreen implements IWorkThread {

    @FXML
    private GridPane pane_data;

    @FXML
    private StackPane pane_msg;

    @FXML
    private TableView<Data> tv_data;

    @FXML
    private PTableColumn<Data, String> col_name;

    @FXML
    private PTableColumn<Data, String> col_type;

    @FXML
    private PTableColumn<Data, String> col_size;

    @FXML
    private PTableColumn<Data, String> col_create;

    @FXML
    private PTableColumn<Data, String> col_last;

    @FXML
    private TableColumn<Record, Boolean> col_download;

    @FXML
    private Label lbl_title, lbl_path, lbl_msg, lbl_status;

    @FXML
    private Button btn_upload, btn_delete, btn_logout, btn_download, btn_refresh, btn_hideprogress;

    @FXML
    private FlowPane pane_flowcontroll;
    @FXML
    private StackPane pane_controlls;

    @FXML
    private SplitMenuButton itm_upload;

    @FXML
    private MenuItem itm_download, itm_delete, itm_logout;

    @FXML
    private ProgressIndicator pb_loaddata;

    @FXML
    private ImageView iv_logo;

    @FXML
    private JFXProgressBar pb_downlad;

    @FXML
    private Hyperlink btn_openDir;

    @FXML
    private VBox pane_progress;


    private FileChooser mediaChooser;
    private Stage stage;
    private static final String DEFAULT_DIR = "../";
    private Controller controller;
    private Message message;
    private int downloadFinish = 0;

    /**
     * Creates gui
     */
    @FXML
    private void initialize() {

        mediaChooser = new FileChooser();
        message = new Message(lbl_msg);
        initView();

        hideButton();

        // Cell factory
        initCell();
        preloadData();

        tv_data.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tv_data.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!newValue.getdata_type().equals("Folder")) {
                    showButton();
                } else {
                    hideButton();
                }
            } else {
                hideButton();
            }
        });

        pane_data.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
            if ((100 * newSceneWidth.intValue() / 1920) <= 75) {
                System.out.println("Width: " + newSceneWidth.intValue());
                pane_flowcontroll.setVisible(false);
                pane_flowcontroll.setDisable(true);
                itm_upload.setDisable(false);
                itm_upload.setVisible(true);
                // Spalten anpassen
                col_type.setVisible(false);
                col_create.setVisible(false);
                col_size.setPercentageWidth(0.13);
                col_last.setPercentageWidth(0.14);
                col_name.setPercentageWidth(0.655);
                lbl_title.setText("");

            } else {
                pane_flowcontroll.setVisible(true);
                pane_flowcontroll.setDisable(false);
                itm_upload.setDisable(true);
                itm_upload.setVisible(false);
                // Spalten zurücksetzen
                col_type.setVisible(true);
                col_create.setVisible(true);
                col_type.setPercentageWidth(0.05);
                col_size.setPercentageWidth(0.05);
                col_last.setPercentageWidth(0.08);
                col_create.setPercentageWidth(0.08);
                col_name.setPercentageWidth(0.695);
                lbl_title.setText("Dropbox");
            }
        });

    }

    /**
     * initalize the screen for the first time
     */
    private void initView() {
        itm_upload.setDisable(true);
        itm_upload.setVisible(false);
        pb_downlad.setVisible(false);
        pb_downlad.setDisable(true);
        btn_openDir.setVisible(false);
        btn_openDir.setDisable(true);
        lbl_status.setVisible(false);
        lbl_status.setDisable(true);
        hideProgress();

        pb_loaddata.setStyle(" -fx-progress-color:  #38424b;");

    }

    /**
     * load data from dropbox to tableview
     */
    private synchronized void preloadData() {
        Platform.runLater(() -> {
            try {
                pb_loaddata.setDisable(false);
                pb_loaddata.setVisible(true);
                tv_data.setItems(controller.getAllData());
            } catch (CloudException e) {
                message.showMessage(e.getType(), e.getMsg());
            } finally {
                pb_loaddata.setDisable(true);
                pb_loaddata.setVisible(false);
            }

        });
    }

    /**
     * shows the drop down menu and hides standard buttons
     */
    private void hideButton() {
        itm_download.setDisable(true);
        itm_download.setVisible(false);
        itm_delete.setDisable(true);
        itm_delete.setVisible(false);
        btn_delete.setDisable(true);
        btn_delete.setVisible(false);
        btn_download.setDisable(true);
        btn_download.setVisible(false);
    }

    /**
     * shows the standard buttons and hides drop down menu
     */
    private void showButton() {
        btn_delete.setVisible(true);
        btn_delete.setDisable(false);
        btn_download.setVisible(true);
        btn_download.setDisable(false);
        itm_download.setDisable(false);
        itm_download.setVisible(true);
        itm_delete.setDisable(false);
        itm_delete.setVisible(true);
    }

    /**
     * cellfactory of tableview.
     * create a custom cell for download column
     */
    private void initCell() {

        col_name.setCellValueFactory(new PropertyValueFactory<Data, String>("data_name"));
        col_type.setCellValueFactory(new PropertyValueFactory<Data, String>("data_type"));
        col_size.setCellValueFactory(new PropertyValueFactory<Data, String>("data_size"));
        col_create.setCellValueFactory(new PropertyValueFactory<Data, String>("data_create"));
        col_last.setCellValueFactory(new PropertyValueFactory<Data, String>("data_last"));

        col_download.setSortable(false);

        col_download.setCellValueFactory(
                p -> new SimpleBooleanProperty(p.getValue() != null));
        col_download.setCellFactory(p -> new view.ButtonCell(controller, this));
    }

    /**
     * start upload sequence via controller (Thread)
     */
    @FXML
    private void upload() {
        mediaChooser.setTitle("Datei hochladen");
        mediaChooser.setInitialDirectory(new File(DEFAULT_DIR));
        List<File> upload_list = mediaChooser.showOpenMultipleDialog(stage);
        if (upload_list != null) {
            pb_downlad.setProgress(0.0);
            downloadFinish = 0;
            hideOpenDir();
            controller.upload_data(upload_list, this);
        }

    }

    /**
     * start delete sequence via controller
     */
    @FXML
    private void delete() {
        ObservableList<Data> delete_list = tv_data.getSelectionModel().getSelectedItems();
        try {
            controller.delete_data(delete_list);
            tv_data.refresh();
        }  catch (DeleteException e) {
            message.showMessage(e.getType(), e.getMsg());
        }
    }

    /**
     * logout from service and exit to homescreen
     */
    @FXML
    private void logout() {
        controller.logout();
        controller.gotoHome(stage);
    }

    /**
     * Loads content in tableview new from dropbox
     */
    @FXML
    private void refresh(){
        tv_data.getItems().clear();
        try {
            tv_data.setItems(controller.getAllData());
        } catch (CloudException e) {
           message.showMessage(e.getType(), e.getMsg());
        }
    }

    /**
     *start download sequence via controller (Thread)
     */
    @FXML
    private void download() {
        ObservableList<Data> download_list = tv_data.getSelectionModel().getSelectedItems();
        pb_downlad.setProgress(0.0);
        downloadFinish = 0;
        hideOpenDir();
        controller.download_data(download_list, this);
    }

    /**
     * after a successful download sequence the hyperlink
     * to open the download dir gets visible
     */
    private void showOpenDir() {
        btn_openDir.setDisable(false);
        btn_openDir.setVisible(true);
    }
    /**
     * after a successful download sequence the hyperlink
     * to open the download dir gets visible
     */
    private void hideOpenDir() {
        btn_openDir.setDisable(true);
        btn_openDir.setVisible(false);
    }

    /**
     * hides the current displayed message
     */
    @FXML
    private void deletemsg() {
        lbl_msg.setVisible(false);
        lbl_msg.setDisable(true);
    }

    /**
     * first focus set after first load
     */
    @FXML
    private void setFocus() {
        pane_data.requestFocus();
        tv_data.getSelectionModel().clearSelection();
    }

    /**
     * opens file explorer at the users Download folder
     */
    @FXML
    private void openDir( ) {
        try {
            Runtime.getRuntime().exec("explorer.exe /open," + System.getProperty("user.home")+"\\Downloads\\");
        } catch (IOException e) {
            message.showMessage(ExceptionType.ERROR, "Ihr System unterstützt diese Funktion nicht!");
        }
    }

    /**
     * This methode save the primary stage that will be used switching screens
     *
     * @param stage primary stage. Usuage for all other screens
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * sets controller to get on loading all data
     * @param controller controller to get data
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * shows the user what the progress of download/upload is
     * @param msg Message String
     * @param size queued files
     * @param current current file
     */
    @Override
    public void onWorkStart(String msg, int size, int current) {
        Platform.runLater(() -> {
            showWorkProgress();
            String text = "Datei " + current + " / " + size + " " + msg + " gestartet";
            setStatus(text);
        });
    }

    /**
     *
     * @param msg Message String
     * @param size queued files
     */
    @Override
    public void onWorkEnd(String msg, int size) {
        Platform.runLater(() -> {
            updateDownloadFinish();
            if (downloadFinish == size && msg.equals("download")) {
                showOpenDir();
                downloadFinish = 0;
                pb_downlad.setProgress(1.0);
                lbl_status.setDisable(true);
                lbl_status.setVisible(false);
                lbl_status.setManaged(false);
            } else {
                String text = "Datei " + downloadFinish + " / " + size + " " + msg + " beendet";
                setStatus(text);
                double progress = (double) 1 / size + pb_downlad.getProgress();
                pb_downlad.setProgress(progress);
            }
        });
    }

    @Override
    public void onWorkError(CloudException e) {
        Platform.runLater(() -> {
            String text = null;
            if (e != null) {
                text = " Ein Fehler ist aufgetreten: " + e.getMsg();
                e.getStackTrace();
            } else {
                text = "Ein Fehler ist aufgetreten";
            }
            setStatus(text);
        });
    }

    /**
     * Sets progressbar and pane visible for Download/Upload
     */
    private void showWorkProgress() {
        pane_progress.setVisible(true);
        pane_progress.setDisable(false);
        pb_downlad.setVisible(true);
        pb_downlad.setDisable(false);
    }

    /**
     * User can manually close progressbar
     */
    @FXML
    private void hideProgress( ){
        pane_progress.setVisible(false);
        pane_progress.setDisable(true);
    }

    /**
     * Displays a given message as feedback for the user
     * @param text String message
     */
    private void setStatus(String text) {
        lbl_status.setDisable(false);
        lbl_status.setVisible(true);
        lbl_status.setText(text);
        lbl_status.setManaged(true);
    }

    private void updateDownloadFinish() {
        this.downloadFinish++;
    }
}
