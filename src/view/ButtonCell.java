package view;

import com.sun.prism.impl.Disposer;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import model.Data;

/*
 * Name			: ButtonCell.java
 * Author		: Sandro Guerotto
 * Describtion	: Custom Cell Factory for Download button
 * Create on 	: 23.09.2016
 * Last modify  : dd.mm.yyyy name reason
 */
public class ButtonCell extends TableCell<Disposer.Record, Boolean> {

    private Button cellButton = new Button();

    public ButtonCell() {

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
                System.out.println("herunterladen " + data.getdata_name() + "." + data.getdata_type());
        });
    }

    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if (!empty) {
            this.setGraphic(this.cellButton);
        }

    }
}
