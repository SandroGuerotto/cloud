package handler;

import com.dropbox.core.DbxException;
import com.jfoenix.controls.JFXProgressBar;
import exception.DownloadException;
import exception.NoFilesException;
import exception.ConnectionErrorException;
import exception.DeleteException;
import exception.UploadException;
import javafx.collections.ObservableList;
import message.Message;
import model.Data;

import java.io.File;
import java.io.IOException;
import java.util.List;

/*
 * Name			: I_EventhandlerDataScreen.java
 * Author		: Sandro Guerotto
 * Describtion	: Interface with all Methods that are necessary for controller and GUI relation
 * Create on 	: 23.09.2016
 * Last modify  : dd.mm.yyyy name reason
 */
public interface I_EventhandlerDataScreen {

    ObservableList<Data> getAllData() throws NoFilesException;

    void delete_data(ObservableList<Data> deletelist) throws DeleteException, ConnectionErrorException;

    void upload_data(List<File> uploadlist) throws UploadException, ConnectionErrorException, IOException, DbxException;

    void download_data(ObservableList<Data> downloadlist);

    void setMessageClass(Message messageClass);

    void setProgressbar(JFXProgressBar progressbar);

    void onWorkStart(int value, int max, String msg);

    void onWorkEnd(int value, int max, String msg);

    void onWorkError(int value, int max);




}
