package handler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import com.jfoenix.controls.JFXProgressBar;
import com.sun.prism.impl.Disposer.Record;

import controller.Controller;
import exception.ConnectionErrorException;
import exception.DeleteException;
import exception.NoFilesException;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import message.Message;
import model.Data;
import thread.IWorkThread;
import view.PTableColumn;

/**
 * @author :   Sandro Guerotto
 * @version :   1.0
 * @created :   20.09.2016
 * @project :   cloud
 * @package :   handler
 * @lastupdate :   01.11.2016
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
    private Button btn_upload, btn_delete, btn_logout, btn_download;

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


    private FileChooser mediaChooser;
    private Stage stage;
    private static final String DEFAULT_DIR = "../";
    private Controller controller;
    private Message message;
    private int downloadFinish = 0;

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

    private void initView() {
        itm_upload.setDisable(true);
        itm_upload.setVisible(false);
        pb_downlad.setVisible(false);
        pb_downlad.setDisable(true);
        btn_openDir.setVisible(false);
        btn_openDir.setDisable(true);
        lbl_status.setVisible(false);
        lbl_status.setDisable(true);

        pb_loaddata.setStyle(" -fx-progress-color:  #38424b;");

    }

    private synchronized void preloadData() {
        Platform.runLater(() -> {
            try {
                pb_loaddata.setDisable(false);
                pb_loaddata.setVisible(true);
                tv_data.setItems(controller.getAllData());
            } catch (NoFilesException e) {
                message.showMessage(e.getType(), e.getMsg());
            } finally {
                pb_loaddata.setDisable(true);
                pb_loaddata.setVisible(false);
            }

        });
    }

    /**
     * Zeigt das Dropdownmenü an und blendet das Standardmenü aus
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
     * Zeigt das Standardmenü wieder an und blendet das Dropdown aus
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
     * Methode um die Tabellenzellen zu initalisieren. Erstellt auch den
     * Downloadbutton
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

    @FXML
    private void upload(ActionEvent event) {
        mediaChooser.setTitle("Datei hochladen");
        mediaChooser.setInitialDirectory(new File(DEFAULT_DIR));
        List<File> upload_list = mediaChooser.showOpenMultipleDialog(stage);
        if (upload_list != null) {
            controller.upload_data(upload_list, this);
        }

    }

    @FXML
    private void delete(ActionEvent event) {
        ObservableList<Data> delete_list = tv_data.getSelectionModel().getSelectedItems();
        try {
            controller.delete_data(delete_list);
        } catch (DeleteException e) {
            message.showMessage(e.getType(), e.getMsg());
        } catch (ConnectionErrorException e) {
            message.showMessage(e.getType(), e.getMsg());
        }
    }

    @FXML
    private void logout(ActionEvent event) {
        controller.gotoHome(stage);
    }

    @FXML
    private void download(ActionEvent event) {
        ObservableList<Data> download_list = tv_data.getSelectionModel().getSelectedItems();
        pb_downlad.setProgress(0.0);
        controller.download_data(download_list, this);
    }

    private void showOpenDir() {
        btn_openDir.setDisable(false);
        btn_openDir.setVisible(true);
    }

    @FXML
    private void deletemsg(ActionEvent event) {
        lbl_msg.setVisible(false);
        lbl_msg.setDisable(true);
    }

    @FXML
    private void setFocus() {
        pane_data.requestFocus();
        tv_data.getSelectionModel().clearSelection();
    }

    @FXML
    private void openDir(ActionEvent event) {
        try {
            Runtime.getRuntime().exec("explorer.exe /select," + Paths.get("").toAbsolutePath().toString());
        } catch (IOException e) {
            message.showMessage('e', "Ihr System unterstützt diese Funktion nicht!");
        }
    }

    /**
     * Methode zum Setzen der Stage -> Popup. Aufgerufen von wird für Popups
     * genutzt
     *
     * @param stage akutelle Stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setController(Controller controller) {
        System.out.println(controller);
        this.controller = controller;
    }

    @Override
    public void onWorkStart(String msg, int size, int current) {
        Platform.runLater(() -> {
            showWorkProgress();
            String text = "Datei " + current + " / " + size + " " + msg + " gestartet";
            setStatus(text);
        });
    }


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
    public void onWorkError(Exception e) {
        Platform.runLater(() -> {
            String text = null;
            if (e != null) {
                text = " Ein Fehler ist aufgetreten: " + e.getMessage();
                e.getStackTrace();
            } else {
                text = "Ein Fehler ist aufgetreten";
            }
            setStatus(text);
        });
    }

    private void showWorkProgress() {
        pb_downlad.setVisible(true);
        pb_downlad.setDisable(false);
    }

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
