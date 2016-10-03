package handler;

/**
 * @author          :   Sandro Guerotto
 * Created          :   20.09.2016
 * Project          :   cloud
 * Package          :   exception
 * @version         :   1.0
 * LastUpdated      :
 * Description      :   Contains all handler for DataScreen and init Screen
 */


import com.sun.prism.impl.Disposer.Record;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import message.Message;
import model.Data;

import java.io.File;
import java.util.List;

public class EventhandlerDataScreen {

    @FXML
    private GridPane pane_data;

    @FXML
    private StackPane pane_msg;

    @FXML
    private TableView<Data> tv_data;

    @FXML
    private TableColumn<Data, String> col_name;

    @FXML
    private TableColumn<Data, String> col_type;

    @FXML
    private TableColumn<Data, String> col_size;

    @FXML
    private TableColumn<Data, String> col_create;

    @FXML
    private TableColumn<Data, String> col_last;

    @FXML
    private TableColumn col_download;

    @FXML
    private Label lbl_title, lbl_path, lbl_msg;

    @FXML
    private Button btn_upload, btn_delete, btn_logout, btn_download;


    private FileChooser mediaChooser;
    private Stage stage;
    private static final String DEFAULT_DIR = "../";

    @FXML
    private void initialize() {

        mediaChooser = new FileChooser();

        btn_delete.setDisable(true);
        btn_delete.setVisible(false);
        btn_download.setDisable(true);
        btn_download.setVisible(false);

        //Cell factory
        initCell();

        // Tabelle einrichten
        ObservableList<Data> user_data = getdata();
        tv_data.setItems(user_data);
        tv_data.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tv_data.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                btn_delete.setVisible(true);
                btn_delete.setDisable(false);
                btn_download.setVisible(true);
                btn_download.setDisable(false);
            } else {
                btn_delete.setVisible(false);
                btn_delete.setDisable(true);
                btn_download.setVisible(false);
                btn_download.setDisable(true);
            }
        });
        tv_data.getSelectionModel().getSelectedItems().clear();

        Message message = new Message();

    }

    /**
     *  Temporäre Methode zum füllen der Tabelle
     * @return ObservableList<Data>
     */
    public ObservableList<Data> getdata() {
        ObservableList<Data> list = FXCollections.observableArrayList();

        Data dummy = new Data();
        dummy.setdata_type("docx");
        dummy.setdatacreate("23.09.2016 02:50");
        dummy.setdata_last("26.09.2016 03:00");
        dummy.setdata_name("test");
        dummy.setdata_size("5 Gb");
        list.add(dummy);

        dummy = new Data();
        dummy.setdata_type("txt");
        dummy.setdatacreate("20.09.2016 12:15");
        dummy.setdata_last("30.09.2016 08:00");
        dummy.setdata_name("file");
        dummy.setdata_size("1.2 Mb");
        list.add(dummy);

        return list;

    }

    /**
     * Methode um die Tabellenzellen zu initalisieren. Erstellt auch den Downloadbutton
     */
    private void initCell(){

        col_name.setCellValueFactory(new PropertyValueFactory("data_name"));
        col_type.setCellValueFactory(new PropertyValueFactory("data_type"));
        col_size.setCellValueFactory(new PropertyValueFactory("data_size"));
        col_create.setCellValueFactory(new PropertyValueFactory("data_create"));
        col_last.setCellValueFactory(new PropertyValueFactory("data_last"));

        col_download.setSortable(false);

        col_download.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Record, Boolean>, ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Record, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

        col_download.setCellFactory(new Callback<TableColumn<Record, Boolean>, TableCell<Record, Boolean>>() {

            @Override
            public TableCell<Record, Boolean> call(TableColumn<Record, Boolean> p) {
                return new view.ButtonCell();
            }

        });

    }

    @FXML
    private void upload(){
        mediaChooser.setTitle("Datei hochladen");
        mediaChooser.setInitialDirectory(new File(DEFAULT_DIR));
        List<File> upload_list = mediaChooser.showOpenMultipleDialog(stage);
        if (upload_list != null){
            //übergabe nach controller
        }

    }
    @FXML
    private void delete(){
        ObservableList<Data> delete_list = tv_data.getSelectionModel().getSelectedItems();
        //überagabe nach controller
    }
    @FXML
    private void logout(){
        //zurück zu home screen
    }

    @FXML
    private void download(){
        ObservableList<Data> download_list = tv_data.getSelectionModel().getSelectedItems();
        //übergabe nach controller!
    }

    @FXML
    private void deletemsg(){
        lbl_msg.setVisible(false);
        lbl_msg.setDisable(true);
    }
    @FXML
    private void setFocus(){
        pane_data.requestFocus();
        tv_data.getSelectionModel().clearSelection();
    }

    /**
     * Methode zum Setzen der Stage -> Popup. Aufgerufen von
     * @param stage wird für Popups genutzt
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
