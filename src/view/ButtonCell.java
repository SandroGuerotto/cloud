package view;

import com.sun.prism.impl.Disposer;
import controller.Controller;

import handler.EventhandlerDataScreen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import model.Data;


/**
 * @author :   Sandro Guerotto
 * Created          :   23.09.2016
 * Project          :   cloud
 * Package          :   view
 * @version :   1.0
 * LastUpdated      :
 * Description      :   Custom Cell  for Download button
 */

public class ButtonCell extends TableCell<Disposer.Record, Boolean> {

    private Button cellButton = new Button();

    public ButtonCell(Controller controller, EventhandlerDataScreen eventhandlerDataScreen) {

        // set dimensions
        cellButton.setMaxWidth(45);
        cellButton.setMinWidth(45);
        cellButton.setPrefWidth(45);
        cellButton.setMaxHeight(45);
        cellButton.setMinHeight(45);
        cellButton.setPrefHeight(45);
        cellButton.setId("download-cell");
        // Action Handler
        this.cellButton.setOnAction(t -> {
            Data data = (Data) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
            //Handle download
            ObservableList<Data> downloadList = FXCollections.observableArrayList();
            downloadList.add(data);
            controller.download_data(downloadList, eventhandlerDataScreen);
            System.out.println("herunterladen " + data.getdata_name());
        });
    }

    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if (!empty) {
            int index = indexProperty().getValue();
            Data data = (Data) ButtonCell.this.getTableView().getItems().get(index);
            if (!data.getdata_type().equals("Folder")) {
                this.setGraphic(this.cellButton);
            }

        }
    }

    public Button getCellButton() {
        return this.cellButton;
    }

}
