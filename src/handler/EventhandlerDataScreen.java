package handler;

import java.io.File;
import java.util.List;

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

import controller.Controller;
import exception.ConnectionErrorException;
import exception.DeleteException;
import exception.DownloadException;
import exception.NoFilesException;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import message.Message;
import model.Data;
import view.PTableColumn;

public class EventhandlerDataScreen {

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
	private Label lbl_title, lbl_path, lbl_msg;

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
	
	private FileChooser mediaChooser;
	private Stage stage;
	private static final String DEFAULT_DIR = "../";
	private Controller controller;
	private Message message;

	@FXML
	private void initialize() {

		mediaChooser = new FileChooser();
		message = new Message(lbl_msg);
		itm_upload.setDisable(true);
		itm_upload.setVisible(false);
		pb_loaddata.setStyle(" -fx-progress-color:  #38424b;");
//		pb_loaddata.setDisable(true);
//		pb_loaddata.setVisible(false);
		hideButton();

		// Cell factory
		initCell();
		preloadData();

		tv_data.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		tv_data.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {

				showButton();
			} else {
				hideButton();
			}
		});

		pane_data.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
					Number newSceneWidth) {
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
			}
		});
		pane_data.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
					Number newSceneHeight) {
				System.out.println("Height: " + newSceneHeight);
			}
		});

	}

	private void preloadData() {
//		tv_data.setItems(testload());
		Platform.runLater(() -> {
			try {
				
				pb_loaddata.setDisable(false);
				pb_loaddata.setVisible(true);
				tv_data.setItems(controller.getAllData());
			} catch (NoFilesException e) {
				message.showMessage(e.getType(), e.getMsg());
			}finally {
				pb_loaddata.setDisable(true);
				pb_loaddata.setVisible(false);
			}

		});

	}

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
	private void upload() {
		mediaChooser.setTitle("Datei hochladen");
		mediaChooser.setInitialDirectory(new File(DEFAULT_DIR));
		List<File> upload_list = mediaChooser.showOpenMultipleDialog(stage);
		if (upload_list != null) {
			// Ã¼bergabe nach controller
		}

	}

	@FXML
	private void delete() {
		ObservableList<Data> delete_list = tv_data.getSelectionModel().getSelectedItems();
		try {
			controller.delete_data(delete_list);
		} catch (DeleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void logout() {
		controller.gotoHome(stage);
	}

	@FXML
	private void download() {
		ObservableList<Data> download_list = tv_data.getSelectionModel().getSelectedItems();
		try {
			controller.download_data(download_list);
		} catch (DownloadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	private void deletemsg() {
		lbl_msg.setVisible(false);
		lbl_msg.setDisable(true);
	}

	@FXML
	private void setFocus() {
		pane_data.requestFocus();
		tv_data.getSelectionModel().clearSelection();
	}

	/**
	 * Methode zum Setzen der Stage -> Popup. Aufgerufen von wird für Popups
	 * genutzt
	 * 
	 * @param stage
	 * 
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setController(Controller controller) {
		System.out.println(controller);
		this.controller = controller;
	}
	
	
	
	private ObservableList<Data> testload(){
		ObservableList<Data> list = FXCollections.observableArrayList();
		Data dummy = new Data();
		dummy.setdata_name("kjsafsafas");
		dummy.setdata_type(".docx");
		dummy.setdata_size("30Gb");
		dummy.setdata_last("07.10.2016");
		dummy.setdatacreate("09.09.2016");
		
		list.add(dummy);
		dummy = new Data();
		dummy.setdata_name("asfsf");
		dummy.setdata_type("Folder");
		dummy.setdata_size("");
		dummy.setdata_last("");
		dummy.setdatacreate("");
		
		list.add(dummy);
		return list;
	}
}
