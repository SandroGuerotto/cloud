package view;

import com.sun.prism.impl.Disposer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Data;

/*
 * Name			: ButtonCell.java
 * Author		: Sandro Guerotto
 * Describtion	: Custom Cell Factory for Download button
 * Create on 	: 23.09.2016
 * Last modify  : dd.mm.yyyy name reason
 */
public class ButtonCell extends TableCell<Disposer.Record, Boolean> {

    private Image img_download;
    private Button cellButton = new Button();
    private ImageView iv_download;

    public ButtonCell() {
        img_download = new Image("File:@../icons/white/PNG/upload.png");
        iv_download = new ImageView(img_download);
        iv_download.setFitHeight(30);
        cellButton.setGraphic(iv_download);

        // set dimensions
        cellButton.setMaxWidth(45);
        cellButton.setMinWidth(45);
        cellButton.setPrefWidth(45);
        cellButton.setMaxHeight(45);
        cellButton.setMinHeight(45);
        cellButton.setPrefHeight(45);

        // Action Handler
        this.cellButton.setOnAction(t -> {
            Data data = (Data) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
            //Handle download
//                System.out.println("herunterladen " + person.getdata_name() + "." + person.getdata_type());
        });
    }

    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if (!empty) {
            this.setGraphic(this.cellButton);
        }

    }
}
