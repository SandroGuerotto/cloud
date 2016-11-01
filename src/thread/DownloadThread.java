package thread;

import com.dropbox.core.DbxException;
import controller.Controller;
import controller.Dropbox;
import exception.EncryptionFileNotFoundException;
import exception.EncryptionInvalidKeyException;
import exception.StreamCopyException;
import handler.EventhandlerDataScreen;
import model.Data;

import java.io.File;
import java.io.IOException;

/**
 * @author      :   Sandro Guerotto
 * @version     :   1.0
 * @created     :   01.11.2016
 * @project     :   cloud
 * @package     :   thread
 * @lastupdate  :
 * @description :   Handles for every file to download. updates gui with progress
 */
public class DownloadThread extends Thread {

    private EventhandlerDataScreen eventhandlerDataScreen;
    private Controller controller;
    private Dropbox dropbox;
    private Data data;
    private int size, current;


    public DownloadThread(EventhandlerDataScreen eventhandlerDataScreen, Controller controller, Data data, int size, int current){
        this.eventhandlerDataScreen = eventhandlerDataScreen;
        this.controller = controller;
        this.dropbox = controller.getDropbox();
        this.size = size;
        this.current = current;
        this.data = data;

    }

    @Override
    public void run() {
        eventhandlerDataScreen.onWorkStart("download" , size, current);
        try {
            dropbox.downloadFile(data.getdata_name());
            controller.getEncryption().decryptFile(new File(data.getdata_name()), controller.getServconnection().getUser().getEncryptionKey(), "C:/Cloud");
        } catch ( EncryptionInvalidKeyException | EncryptionFileNotFoundException | StreamCopyException | DbxException | IOException e) {
            eventhandlerDataScreen.onWorkError(e);
        } finally {
            eventhandlerDataScreen.onWorkEnd("download", size);
        }
    }
}
