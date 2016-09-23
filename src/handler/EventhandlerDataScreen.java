package handler;

/*
 * Name			: EventhandlerDataScreen.java
 * Author		: Sandro Guerotto
 * Describtion	: Handler class for all Event from the Data Screen
 * Create on 	: 20.09.2016
 * Last modify  : dd.mm.yyyy name reason
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
import javafx.util.Callback;
import model.Data;

public class EventhandlerDataScreen {

    @FXML
    private GridPane pane_data;

    @FXML
    private TableView<Data> tv_data;

    @FXML
    private TableColumn<Data, String> col_name;

    @FXML
    private TableColumn<Data, String> col_type;

    @FXML
    private TableColumn<Data, String> col_create;

    @FXML
    private TableColumn<Data, String> col_last;

    @FXML
    private TableColumn col_download;

    @FXML
    private Label lbl_title, lbl_path;

    @FXML
    private Button btn_upload, btn_delete, btn_logout;


    @FXML
    private void initialize() {

        ObservableList<Data> user_data = getdata();
        tv_data.setItems(user_data);
        tv_data.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        col_name.setCellValueFactory(new PropertyValueFactory("data_name"));
        col_type.setCellValueFactory(new PropertyValueFactory("data_type"));
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
//        tv_data.getColumns().add(col_action);
    }

    public ObservableList<Data> getdata() {
        ObservableList<Data> list = FXCollections.observableArrayList();

        Data dummy = new Data();
        dummy.setdata_type("docx");
        dummy.setDataCreate("23.09.2016");
        dummy.setFirstName("test");
        list.add(dummy);

        dummy = new Data();
        dummy.setdata_type("txt");
        dummy.setDataCreate("20.09.2016");
        dummy.setFirstName("file");
        list.add(dummy);

        return list;

    }

}
