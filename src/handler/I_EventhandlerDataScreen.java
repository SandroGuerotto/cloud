package handler;

import java.io.File;
import java.util.List;

import exception.DeleteException;
import exception.ErrorException;
import exception.NoFilesException;
import javafx.collections.ObservableList;
import model.Data;

/**
 * @author      : Sandro Guerotto
 * @version     : 2.0
 * @created     : 23.09.2016
 * @project     : cloud
 * @package     : handler
 * @lastUpdated : 04.11.2016 / by Sandro Guerotto
 * @description : Interface with all Methods that are necessary for controller and GUI relation
 */
public interface I_EventhandlerDataScreen {

    ObservableList<Data> getAllData() throws NoFilesException, ErrorException;

    void delete_data(ObservableList<Data> deletelist) throws DeleteException;

    void upload_data(List<File> uploadlist, EventhandlerDataScreen handler);

    void download_data(ObservableList<Data> downloadlist, EventhandlerDataScreen handler);

}
