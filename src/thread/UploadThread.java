package thread;

import com.dropbox.core.DbxException;
import controller.Controller;
import controller.Dropbox;
import exception.*;
import handler.EventhandlerDataScreen;

import java.io.File;
import java.io.IOException;


/**
 * @author      :   Sandro Guerotto
 * @version     :   1.0
 * @created     :   01.11.2016
 * @project     :   cloud
 * @package     :   thread
 * @lastupdate  :
 * @description :   Handles for every file to upload. updates gui with progress
 */
public class UploadThread extends Thread {

    private EventhandlerDataScreen eventhandlerDataScreen;
    private Controller controller;
    private Dropbox dropbox;
    private File file;
    private int size, current;


    public UploadThread(EventhandlerDataScreen eventhandlerDataScreen, Controller controller, File file, int size, int current){
        this.eventhandlerDataScreen = eventhandlerDataScreen;
        this.controller = controller;
        this.dropbox = controller.getDropbox();
        this.size = size;
        this.current = current;
        this.file = file;

    }

    @Override
    public void run() {
        eventhandlerDataScreen.onWorkStart("upload" , size, current);
        try {
            controller.getEncryption().encryptFile(file, controller.getServconnection().getUser().getEncryptionKey());
            dropbox.uploadFile(file.getAbsolutePath() + ".aes");
            new File(file.getAbsolutePath() + ".aes").delete();
            dropbox.addFiletoList(file);
            eventhandlerDataScreen.onWorkEnd("upload", size);
        } catch ( EncryptionInvalidKeyException | EncryptionFileNotFoundException | StreamCopyException e) {
            eventhandlerDataScreen.onWorkError(e);
        } catch (DbxException | IOException e){
            eventhandlerDataScreen.onWorkError(new ErrorException(ExceptionType.ERROR));
        }finally {
            //delete .aes file
        }
    }
}
