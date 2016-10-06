package message;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author          :   Sandro Guerotto
 * Created          :   20.09.2016
 * Project          :   cloud
 * Package          :   message
 * @version         :   1.0
 * LastUpdated      :
 * Description      :   This Class holds all messages for the user as feedback
 */

public class Message {

    private Label lbl_message;
    private ImageView iv_icon;
    private String type;
    private final Image img_info = new Image("File:icons/msg/info/info.png", 20, 20, false, false);

    public Message(Label lbl_message, ImageView iv_msgicon){
    	this.lbl_message = lbl_message;
    	this.iv_icon = iv_msgicon;
    }

    /**
     * Entscheidet welchen Typ und welche Nachricht angezeigt werden muss
     * @param msgid Bsp: 'e20'
     */
    public void showMessage(char msg_type, String msg){
        switch (msg_type) {
            case 's':
                type = "msg_success";
                break;
            case 'i':
                type = "msg_info";
                break;
            case 'w':
                type = "msg_warning";
                break;
            case 'e':
                type = "msg_error";
                break;
        }
        
        setProperties(type, msg);
        removeMsg();
    }

    private void setProperties(String cssClass, String msg) {
        lbl_message.getStyleClass().add(cssClass);
        lbl_message.setText(msg);
//        iv_icon.setImage(img_info);
        lbl_message.setGraphic(new ImageView(img_info));
        lbl_message.setVisible(true);
        lbl_message.setDisable(false);
    }

    private void deleteStyleMsg(String name) {
        ObservableList<String> list = lbl_message.getStyleClass();
        try {
            for (String cssclass : list) {
                if (cssclass.matches(name)) {
                    list.remove(cssclass);
                    lbl_message.setText("");
                    lbl_message.setVisible(false);
                    lbl_message.setDisable(true);
                    return;
                }
            }
        } catch (Exception e) {
            return;
        }

    }

    private void removeMsg() {
        // Label & Button nach bestimmter zeit not Visible
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.schedule(() -> {
            Platform.runLater(() -> {
                deleteStyleMsg("msg_.*");
            });

        }, 5, TimeUnit.SECONDS);

    }
}
