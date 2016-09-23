package handler;

import exception.DownloadException;
import exception.ConnectionErrorException;
import exception.DeleteException;
import exception.UploadException;
import javafx.collections.ObservableList;
import model.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
 * Name			: I_EventhandlerDataScreen.java
 * Author		: Sandro Guerotto
 * Describtion	: Interface with all Methods that are necessary for controller and GUI relation
 * Create on 	: 23.09.2016
 * Last modify  : dd.mm.yyyy name reason
 */
public interface I_EventhandlerDataScreen {

    ObservableList<Data> getAllData();

    void delete_data(ObservableList<Data> deletelist) throws DeleteException, ConnectionErrorException;

    void upload_data(List<File> uploadlist) throws UploadException, ConnectionErrorException;

    void download_data(ArrayList<Data> downloadlist) throws DownloadException, ConnectionErrorException;

}
