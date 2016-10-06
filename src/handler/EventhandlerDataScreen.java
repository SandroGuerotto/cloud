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

import controller.Controller;
import exception.ConnectionErrorException;
import exception.DeleteException;
import exception.DownloadException;
import exception.NoFilesException;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
	private ImageView iv_msgicon;

	private FileChooser mediaChooser;
	private Stage stage;
	private static final String DEFAULT_DIR = "../";
	private Controller controller;
	private Message message;

	@FXML
	private void initialize() {

		mediaChooser = new FileChooser();
		message = new Message(lbl_msg, iv_msgicon);
		itm_upload.setDisable(true);
		itm_upload.setVisible(false);
		hideButton();

		// Cell factory
		initCell();

		Platform.runLater(() -> {
			
			try {
				tv_data.setItems(controller.getAllData());
			} catch (NoFilesException e) {
				message.showMessage(e.getType(), e.getMsg());
			}

		});

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
				} else {
					pane_flowcontroll.setVisible(true);
					pane_flowcontroll.setDisable(false);
					itm_upload.setDisable(true);
					itm_upload.setVisible(false);
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
	 * Methode zum Setzen der Stage -> Popup. Aufgerufen von wird für Popups genutzt
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
}
