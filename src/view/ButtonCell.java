package view;

import com.sun.prism.impl.Disposer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    private final Button cellButton = new Button("Download");

    public ButtonCell() {
        this.cellButton.setOnAction(t -> {
            Data data = (Data)ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
            //Handle download
//                System.out.println("herunterladen " + person.getdata_name() + "." + person.getdata_type());
        });
    }

    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if(!empty) {
            this.setGraphic(this.cellButton);
        }

    }
}
