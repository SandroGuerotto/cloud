package message;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author :   Sandro Guerotto
 *         Created          :   20.09.2016
 *         Project          :   cloud
 *         Package          :   message
 * @version :   1.0
 *          LastUpdated      :
 *          Description      :   This Class holds all messages for the user as feedback
 */

public class Message {

    private Label lbl_message;
    private String type;

    /**
     * To create an show a message correctly, it musst have a label to set the Text.
     * It automatically hides after 5 seconds
     *
     * @param lbl_message Javafx Label to set the message text
     */
    public Message(Label lbl_message) {
        this.lbl_message = lbl_message;
    }

    /**
     * Method to show all kind of Messages
     *
     * @param msg_type msg_type sets how the message will be displayed
     * @param msg      message to show
     */
    public void showMessage(char msg_type, String msg) {
        Platform.runLater(() -> {
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
        });

        removeMsg();
    }

    /**
     * Sets all Properties, show and Enable the label
     *
     * @param cssClass refernece to external css file
     * @param msg      actual message
     */
    private void setProperties(String cssClass, String msg) {
        lbl_message.getStyleClass().add(cssClass);
        lbl_message.setText(msg);

        lbl_message.setVisible(true);
        lbl_message.setDisable(false);
    }

    /**
     * Removes all Stylecasses for the label so it doesn't show up
     *
     * @param name classname which will be removed
     */
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
        } catch (Exception ignored) {
        }

    }

    /**
     * after 5 seconds the message will be removed.
     * runs in a new Thread/Service
     */
    private void removeMsg() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        service.schedule(() -> Platform.runLater(() -> deleteStyleMsg("msg_.*")), 5, TimeUnit.SECONDS);

    }
}
